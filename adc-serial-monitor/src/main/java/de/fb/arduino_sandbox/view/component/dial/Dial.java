package de.fb.arduino_sandbox.view.component.dial;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import de.fb.arduino_sandbox.view.component.MouseEventHook;
import de.fb.arduino_sandbox.view.component.MouseMotionEventHook;

public class Dial extends JComponent {

    private static final String TOOLTIP_TEMPLATE = "<html><h4>Value: {0}<br/>Min: {1}<br/>Max: {2}</h4></html>";

    private static final Dimension DEFAULT_SIZE = new Dimension(32, 32);

    private static final float ARC_STROKE_WIDTH = 3.0f;
    private static final float FOCUS_RING_STROKE_WIDTH = 1.5f;

    private static final float MIN_KNOB_DOT_RADIUS = 2.0f;

    private static final float ARC_RADIUS_RATIO = 0.80f;
    private static final float KNOB_RADIUS_RATIO = 0.65f;
    private static final float KNOB_DOT_RADIUS_RATIO = 0.15f;
    private static final float FOCUS_RING_RADIUS_RATIO = 0.9995f;

    private static final float ROTATION_ARC_EXTENT = 270.0f;

    private static final Color DEFAULT_ARC_COLOR = new Color(0, 204, 255);
    private static final Color DEFAULT_KNOB_COLOR = new Color(180, 180, 180);
    private static final Color DEFAULT_FOCUS_RING_COLOR = new Color(200, 200, 200);

    private static final int COARSE_CHANGE_THRESHOLD = 10;

    // graphical state
    private Color knobColor;
    private Color focusRingColor;
    private Color indicatorArcColor;

    private BasicStroke focusRingStroke;
    private BasicStroke indicatorArcStroke;

    private Ellipse2D knob;
    private Shape knobDot;
    private Arc2D indicatorArc;
    private Ellipse2D focusRing;

    private AffineTransform viewportTransform;
    private Rectangle boundingRectangle;
    private double knobRotationAngle;

    private boolean mouseInBounds;
    private Point prevMouseCoords;

    private IntegerRangeModel model;

    private List<ChangeListener> changeListeners;
    private List<Consumer<Integer>> changeCallbacks;

    public Dial() {

        super();
        changeListeners = Collections.emptyList();
        changeCallbacks = Collections.emptyList();
        model = new IntegerRangeModel(0, 100, 10, 1, 0);

        initColors();
        setMinimumSize(DEFAULT_SIZE);
        setMaximumSize(DEFAULT_SIZE);
        setPreferredSize(DEFAULT_SIZE);

        initGeometry();
        registerEvents();
        updateState();
    }

    public int getMin() {
        return model.getMin();
    }

    public void setMin(final int min) {
        model.setMin(min);
        updateState();
    }

    public int getMax() {
        return model.getMax();
    }

    public void setMax(final int max) {
        model.setMax(max);
        updateState();
    }

    public int getValue() {
        return model.getValue();
    }

    public void setValue(final int value) {
        model.setValue(value);
        updateState();
    }

    public int getCoarseStep() {
        return model.getCoarseStep();
    }

    public void setCoarseStep(final int coarseStep) {
        model.setCoarseStep(coarseStep);
        updateState();
    }

    public int getFineStep() {
        return model.getFineStep();
    }

    public void setFineStep(final int fineStep) {
        model.setFineStep(fineStep);
        updateState();
    }

    public void addChangeListener(final ChangeListener listener) {
        if (changeListeners.isEmpty()) {
            changeListeners = new ArrayList<>();
        }
        changeListeners.add(listener);
    }

    public void addChangeCallback(final Consumer<Integer> callback) {
        if (changeCallbacks.isEmpty()) {
            changeCallbacks = new ArrayList<>();
        }
        changeCallbacks.add(callback);
    }

    @Override
    public void revalidate() {
        super.revalidate();
        initGeometry();
        updateState();
    }

    @Override
    public void paint(final Graphics g) {

        Graphics2D ctx = Graphics2D.class.cast(g);
        ctx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ctx.transform(viewportTransform);

        final AffineTransform prevTransform = ctx.getTransform();

        ctx.rotate(knobRotationAngle);
        ctx.setColor(knobColor);
        ctx.fill(knob);

        ctx.setColor(indicatorArcColor);
        ctx.fill(knobDot);

        ctx.setTransform(prevTransform);

        Stroke stroke = ctx.getStroke();
        ctx.setStroke(indicatorArcStroke);
        ctx.setColor(indicatorArcColor);
        ctx.draw(indicatorArc);
        ctx.setStroke(stroke);

        if (mouseInBounds && isEnabled()) {
            ctx.setColor(focusRingColor);
            stroke = ctx.getStroke();
            ctx.setStroke(focusRingStroke);
            ctx.draw(focusRing);
            ctx.setStroke(stroke);
        }
    }

    private void initColors() {

        Color color = UIManager.getColor("Button.darcula.color1");
        this.knobColor = color != null ? color : DEFAULT_KNOB_COLOR;

        this.indicatorArcColor = DEFAULT_ARC_COLOR;
        this.focusRingColor = DEFAULT_FOCUS_RING_COLOR;
    }

    private void initGeometry() {

        int width = getPreferredSize().width;
        int height = getPreferredSize().height;
        float halfWidth = 0.5f * width;
        float halfHeight = 0.5f * height;
        float radius = halfWidth < halfHeight ? halfWidth : halfHeight;

        // "global transform" that establishes a canonical Cartesian coordinate system with the center
        // @(half_width, half_height) with positive Y-axis pointing up
        viewportTransform = new AffineTransform(1.0f, 0.0f, 0.0f, -1.0f, halfWidth, halfHeight);
        boundingRectangle = new Rectangle(0, 0, width, height);

        final float focusRingRadius = radius * FOCUS_RING_RADIUS_RATIO;
        final float knobRadius = radius * KNOB_RADIUS_RATIO;
        final float arcRadius = radius * ARC_RADIUS_RATIO;

        knob = new Ellipse2D.Float(-knobRadius, -knobRadius, 2.0f * knobRadius, 2.0f * knobRadius);
        knobRotationAngle = 0.0;

        float knobDotRadius = radius * KNOB_DOT_RADIUS_RATIO;
        if (knobDotRadius < MIN_KNOB_DOT_RADIUS) {
            knobDotRadius = MIN_KNOB_DOT_RADIUS;
        }

        // pre-transform the knob dot shape
        final Ellipse2D dot = new Ellipse2D.Float(-knobDotRadius, -knobDotRadius, 2.0f * knobDotRadius, 2.0f * knobDotRadius);

        AffineTransform xform = new AffineTransform();
        xform.rotate(Math.PI / 4.0);
        xform.translate(-knobRadius + 2 + dot.getWidth() / 2, 0.0);
        knobDot = xform.createTransformedShape(dot);

        indicatorArcStroke = new BasicStroke(ARC_STROKE_WIDTH);

        indicatorArc = new Arc2D.Float(Arc2D.OPEN);
        indicatorArc.setFrame(-arcRadius, -arcRadius, 2.0f * arcRadius, 2.0f * arcRadius);
        indicatorArc.setAngleStart(-225.0);
        indicatorArc.setAngleExtent(0.0);

        focusRing = new Ellipse2D.Float(-focusRingRadius, -focusRingRadius, 2.0f * focusRingRadius, 2.0f * focusRingRadius);
        focusRingStroke = new BasicStroke(FOCUS_RING_STROKE_WIDTH);
    }

    private void updateState() {
        updateAngles();
        setTooltip();
        repaint();
    }

    private void updateAngles() {

        final float normValue = 1.0f * (model.getValue() - model.getMin()) / (model.getMax() - model.getMin());
        final double angle = normValue * ROTATION_ARC_EXTENT;

        indicatorArc.setAngleExtent(angle);
        knobRotationAngle = -Math.toRadians(angle);
    }

    private void setTooltip() {
        final String text = MessageFormat.format(TOOLTIP_TEMPLATE, model.getValue(), model.getMin(), model.getMax());
        setToolTipText(text);
    }

    private void fireChangeEvent() {

        final ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : changeListeners) {
            listener.stateChanged(event);
        }
        for (Consumer<Integer> callback : changeCallbacks) {
            callback.accept(this.getValue());
        }
    }

    @SuppressWarnings("unused")
    private void mouseClicked(final MouseEvent event) {
        // NO-OP
    }

    private void mousePressed(final MouseEvent event) {
        if (isEnabled() && boundingRectangle.contains(event.getPoint())) {
            prevMouseCoords = event.getPoint();
            repaint();
        }
    }

    private void mouseReleased(final MouseEvent event) {
        if (isEnabled()) {
            prevMouseCoords = event.getPoint();
            repaint();
        }
    }

    @SuppressWarnings("unused")
    private void mouseEntered(final MouseEvent event) {
        if (isEnabled()) {
            mouseInBounds = true;
            repaint();
        }
    }

    @SuppressWarnings("unused")
    private void mouseExited(final MouseEvent event) {
        if (isEnabled()) {
            mouseInBounds = false;
            repaint();
        }
    }

    @SuppressWarnings("unused")
    private void mouseMoved(final MouseEvent event) {
        // NO-OP
    }

    private void mouseDragged(final MouseEvent event) {

        if (isEnabled()) {
            final Point mouseCoords = event.getPoint();
            final int deltaX = mouseCoords.x - prevMouseCoords.x;

            // update previous mouse coordinates
            prevMouseCoords = mouseCoords;

            final boolean coarse = Math.abs(deltaX) >= COARSE_CHANGE_THRESHOLD;

            if (deltaX > 0) {
                if (coarse) {
                    model.coarseIncrement();
                } else {
                    model.fineIncrement();
                }

            } else if (deltaX < 0) {
                if (coarse) {
                    model.coarseDecrement();
                } else {
                    model.fineDecrement();
                }
            }

            fireChangeEvent();
            updateState();
        }
    }

    private void mouseWheelMoved(final MouseWheelEvent event) {

        if (isEnabled()) {
            if (event.getWheelRotation() > 0) {
                model.fineDecrement();
            } else if (event.getWheelRotation() < 0) {
                model.fineIncrement();
            }

            fireChangeEvent();
            updateState();
        }
    }

    private void registerEvents() {

        this.addComponentListener(new ComponentAdapter() {

            @Override
            @SuppressWarnings("synthetic-access")
            public void componentResized(final ComponentEvent event) {
                super.componentResized(event);
                initGeometry();
                updateState(); // necessary to account for changed geometry!
            }
        });

        this.addMouseListener(new MouseEventHook(
            this::mouseClicked, this::mousePressed, this::mouseReleased,
            this::mouseEntered, this::mouseExited));

        this.addMouseMotionListener(new MouseMotionEventHook(this::mouseMoved, this::mouseDragged));
        this.addMouseWheelListener(event -> mouseWheelMoved(event));
    }
}
