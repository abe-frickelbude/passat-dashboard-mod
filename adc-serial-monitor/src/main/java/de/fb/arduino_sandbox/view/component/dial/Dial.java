package de.fb.arduino_sandbox.view.component.dial;

import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import javax.swing.UIManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.fb.arduino_sandbox.view.component.MouseEventHook;
import de.fb.arduino_sandbox.view.component.MouseMotionEventHook;

public class Dial extends JComponent {

    private static final Logger log = LoggerFactory.getLogger(Dial.class);

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
    private Color indicatorArcColor;
    private Color innerKnobColor;
    private Color outerKnobColor;
    private Color focusRingColor;

    private RadialGradientPaint knobGradient;
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
    private boolean mousePressed;
    private Point prevMouseCoords;
    private Long prevTimestamp;

    private IntegerRangeModel model;
    // private List<ActionListener> actionListeners;
    // private List<Runnable> actionCallbacks;

    public Dial() {

        super();
        // actionCallbacks = Collections.emptyList();
        // actionListeners = Collections.emptyList();
        model = new IntegerRangeModel(0, 100, 10, 1, 0);

        initColors();
        setMinimumSize(DEFAULT_SIZE);
        setMaximumSize(DEFAULT_SIZE);
        setPreferredSize(DEFAULT_SIZE);

        initGeometry();
        registerEvents();
    }

    public void setMin(final int min) {
        model.setMin(min);
    }

    public void setMax(final int max) {
        model.setMax(max);
    }

    public void setValue(final int value) {
        model.setValue(value);
    }

    public void setCoarseStep(final int coarseStep) {
        model.setCoarseStep(coarseStep);
    }

    public void setFineStep(final int fineStep) {
        model.setCoarseStep(fineStep);
    }

    // public void addActionListener(final ActionListener listener) {
    // if (actionListeners.isEmpty()) {
    // actionListeners = new ArrayList<>();
    // }
    // actionListeners.add(listener);
    // }
    //
    // public void addActionCallback(final Runnable callback) {
    // if (actionCallbacks.isEmpty()) {
    // actionCallbacks = new ArrayList<>();
    // }
    // actionCallbacks.add(callback);
    // }

    @Override
    public void revalidate() {
        super.revalidate();
        initGeometry();
    }

    @Override
    public void paint(final Graphics g) {

        Graphics2D ctx = Graphics2D.class.cast(g);
        ctx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ctx.transform(viewportTransform);

        final AffineTransform prevTransform = ctx.getTransform();
        ctx.rotate(knobRotationAngle);

        ctx.setColor(innerKnobColor);
        ctx.fill(knob);

        ctx.setColor(indicatorArcColor);
        ctx.fill(knobDot);

        ctx.setTransform(prevTransform);

        ctx.setColor(indicatorArcColor);

        Stroke stroke = ctx.getStroke();
        ctx.setStroke(indicatorArcStroke);
        ctx.draw(indicatorArc);
        ctx.setStroke(stroke);

        // final Paint prevPaint = ctx.getPaint();
        // ctx.setPaint(knobGradient);
        // ctx.fill(knob);
        // ctx.setPaint(prevPaint);

        if (mouseInBounds && isEnabled()) {
            // if (mousePressed) {
            // } else {
            // }
            ctx.setColor(focusRingColor);
            stroke = ctx.getStroke();
            ctx.setStroke(focusRingStroke);
            ctx.draw(focusRing);
            ctx.setStroke(stroke);
        }
    }

    private void initColors() {

        Color color = UIManager.getColor("Button.darcula.color1");
        this.innerKnobColor = color != null ? color : DEFAULT_KNOB_COLOR;

        color = UIManager.getColor("Button.darcula.color2");
        this.outerKnobColor = color != null ? color : DEFAULT_KNOB_COLOR;

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

        //@formatter:off
        knobGradient = new RadialGradientPaint(halfWidth, halfHeight, knobRadius,
            halfWidth  + 2, halfWidth + 2,
            new float[] { 0.0f, 1.0f }, 
            new Color[] { outerKnobColor, innerKnobColor },
            CycleMethod.NO_CYCLE);
        //@formatter:on

        indicatorArcStroke = new BasicStroke(ARC_STROKE_WIDTH);

        indicatorArc = new Arc2D.Float(Arc2D.OPEN);
        indicatorArc.setFrame(-arcRadius, -arcRadius, 2.0f * arcRadius, 2.0f * arcRadius);
        indicatorArc.setAngleStart(-225.0);
        indicatorArc.setAngleExtent(0.0);

        focusRing = new Ellipse2D.Float(-focusRingRadius, -focusRingRadius, 2.0f * focusRingRadius, 2.0f * focusRingRadius);
        focusRingStroke = new BasicStroke(FOCUS_RING_STROKE_WIDTH);
    }

    private void updateAngles() {

        final float normValue = 1.0f * (model.getValue() - model.getMin()) / (model.getMax() - model.getMin());
        final double angle = normValue * ROTATION_ARC_EXTENT;

        indicatorArc.setAngleExtent(angle);
        knobRotationAngle = -Math.toRadians(angle);
    }

    private void fireActionEvent() {
        // final ActionEvent event = new ActionEvent(this, 0, StringUtils.EMPTY);
        // for (ActionListener listener : actionListeners) {
        // listener.actionPerformed(event);
        // }
        // for (Runnable callback : actionCallbacks) {
        // callback.run();
        // }
    }

    @SuppressWarnings("unused")
    private void mouseClicked(final MouseEvent event) {
        // NO-OP
    }

    private void mousePressed(final MouseEvent event) {
        if (isEnabled() && boundingRectangle.contains(event.getPoint())) {
            mousePressed = true;
            prevMouseCoords = event.getPoint();
            prevTimestamp = System.currentTimeMillis();
            repaint();
        }
    }

    private void mouseReleased(final MouseEvent event) {
        if (isEnabled()) {
            mousePressed = false;
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
            final long timeStamp = System.currentTimeMillis();

            final int deltaX = mouseCoords.x - prevMouseCoords.x;
            // final float x_accel = 1.0f * deltaX / (timeStamp - prevTimestamp);
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

            // log.info("delta x: {}, coarse: {}, value: {}", deltaX, coarse, model.getValue());
            // update previous mouse coordinates
            prevMouseCoords = mouseCoords;
            prevTimestamp = timeStamp;
            updateAngles();
            repaint();
        }
    }

    private void mouseWheelMoved(final MouseWheelEvent event) {

        if (isEnabled()) {
            if (event.getWheelRotation() > 0) {
                model.fineDecrement();
            } else if (event.getWheelRotation() < 0) {
                model.fineIncrement();
            }
            updateAngles();
            repaint();
        }
    }

    private void registerEvents() {

        this.addComponentListener(new ComponentAdapter() {

            @Override
            @SuppressWarnings("synthetic-access")
            public void componentResized(final ComponentEvent event) {
                super.componentResized(event);
                initGeometry();
                repaint(); // important to account for changed swatch geometry!
            }
        });

        this.addMouseListener(new MouseEventHook(
            this::mouseClicked, this::mousePressed, this::mouseReleased,
            this::mouseEntered, this::mouseExited));

        this.addMouseMotionListener(new MouseMotionEventHook(this::mouseMoved, this::mouseDragged));
        this.addMouseWheelListener(event -> mouseWheelMoved(event));
    }
}
