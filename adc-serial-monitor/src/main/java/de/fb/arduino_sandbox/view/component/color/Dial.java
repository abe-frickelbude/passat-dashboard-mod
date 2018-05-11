package de.fb.arduino_sandbox.view.component.color;

import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import javax.swing.UIManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Color DEFAULT_ARC_COLOR = new Color(0, 204, 255);
    private static final Color DEFAULT_KNOB_COLOR = new Color(180, 180, 180);
    private static final Color DEFAULT_FOCUS_RING_COLOR = new Color(200, 200, 200);

    // graphical state
    private Color indicatorArcColor;
    private Color innerKnobColor;
    private Color outerKnobColor;
    private Color focusRingColor;

    private RadialGradientPaint knobGradient;
    private BasicStroke focusRingStroke;
    private BasicStroke indicatorArcStroke;

    // private float arcRadius;
    // private float knobRadius;
    // private float focusRingRadius;

    private Ellipse2D knob;
    private Shape knobDot;
    private Arc2D indicatorArc;
    private Ellipse2D focusRing;

    // private Point2D.Float center;
    private AffineTransform viewportTransform;
    private Rectangle boundingRectangle;

    private boolean mouseInBounds;
    private boolean mousePressed;

    // private List<ActionListener> actionListeners;
    // private List<Runnable> actionCallbacks;

    public Dial() {

        super();
        // actionCallbacks = Collections.emptyList();
        // actionListeners = Collections.emptyList();
        initColors();
        setMinimumSize(DEFAULT_SIZE);
        setMaximumSize(DEFAULT_SIZE);
        setPreferredSize(DEFAULT_SIZE);

        initGeometry();
        registerEvents();
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

        ctx.setColor(innerKnobColor);
        ctx.fill(knob);

        ctx.setColor(indicatorArcColor);
        ctx.fill(knobDot);

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

        ////// Debug bbox ///////////

        // ctx.setColor(Color.RED);
        // Rectangle2D bbox = new Rectangle2D.Float(
        // -getPreferredSize().width / 2,
        // -getPreferredSize().height / 2 + 1,
        // getPreferredSize().width, getPreferredSize().height - 1);
        // ctx.draw(bbox);

        /////////////////////////////

    }

    private void initColors() {

        Color color = UIManager.getColor("Button.darcula.color1");
        this.innerKnobColor = color != null ? color : DEFAULT_KNOB_COLOR;

        color = UIManager.getColor("Button.darcula.color2");
        this.outerKnobColor = color != null ? color : DEFAULT_KNOB_COLOR;

        this.indicatorArcColor = DEFAULT_ARC_COLOR;
        this.focusRingColor = DEFAULT_FOCUS_RING_COLOR;
    }

    private void preRenderIcon() {

        /*
         * Note: using individual font metrics to calculate the appropriate size for the icon image is complicated and
         * also inaccurate (because the said metrics only return the "general dimensions" of a font character and not the
         * actual dimensions of a particular character), so instead we temporarily convert the icon into an actual
         * GlyphVector and use the resulting Shape's bounding box to create an appropriately sized icon image, and also
         * to render the icon.
         */
        // final int width = getPreferredSize().width;
        // final int height = getPreferredSize().height;
        // final int iconSize = Math.round(height * iconSizeRatio);
        //
        // final IkonHandler ikonHandler = IkonResolver.getInstance().resolve(icon.getIkon().getDescription());
        // final Font font = ((Font) ikonHandler.getFont()).deriveFont(Font.PLAIN, iconSize);
        //
        // // this one is temporary, just to get a Graphics2D instance
        // final BufferedImage tmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        // Graphics2D ctx = Graphics2D.class.cast(tmp.getGraphics());
        //
        // // convert font character into a geometry and retrieve the bounding box
        // final GlyphVector glyphVector = font.createGlyphVector(ctx.getFontMetrics(font).getFontRenderContext(),
        // new char[] {
        // icon.getIkon().getCode()
        // });
        //
        // final Shape shape = glyphVector.getOutline();
        // ctx.dispose();
        // // log.info("Glyph bounds for {}: {}", icon.getIkon(), shape.getBounds2D());
        //
        // // initialize cached icon image
        // iconImage = new BufferedImage(shape.getBounds().width, shape.getBounds().height, BufferedImage.TYPE_INT_ARGB);
        // ctx = Graphics2D.class.cast(iconImage.getGraphics());
        // ctx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //
        // // glyph Y0 is always negative, so the whole coordinate space must be translated by -Y0!
        // final AffineTransform xform = new AffineTransform();
        // xform.setToTranslation(0.0, -shape.getBounds2D().getY());
        //
        // ctx.setColor(getForeground());
        // ctx.setTransform(xform);
        // ctx.fill(shape);
        // ctx.dispose();
        //
        // iconPosition = new Point2D.Double(
        // 0.5 * (width - shape.getBounds2D().getWidth()),
        // 0.5 * (height - shape.getBounds().getHeight()));
    }

    private void initGeometry() {

        int width = getPreferredSize().width;
        int height = getPreferredSize().height;
        float halfWidth = 0.5f * width;
        float halfHeight = 0.5f * height;
        float radius = halfWidth < halfHeight ? halfWidth : halfHeight;
        // center = new Point2D.Float(halfWidth, halfHeight);

        // "global transform" that establishes a canonical Cartesian coordinate system with the center
        // @(half_width, half_height) with positive Y-axis pointing up
        viewportTransform = new AffineTransform(1.0f, 0.0f, 0.0f, -1.0f, halfWidth, halfHeight);
        boundingRectangle = new Rectangle(0, 0, width, height);

        final float focusRingRadius = radius * FOCUS_RING_RADIUS_RATIO;
        final float knobRadius = radius * KNOB_RADIUS_RATIO;
        final float arcRadius = radius * ARC_RADIUS_RATIO;

        knob = new Ellipse2D.Float(-knobRadius, -knobRadius, 2.0f * knobRadius, 2.0f * knobRadius);

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

        //////////////////////////////////////
        indicatorArc.setAngleStart(-225.0);
        indicatorArc.setAngleExtent(1.0);

        //////////////////////////////////////

        focusRing = new Ellipse2D.Float(-focusRingRadius, -focusRingRadius, 2.0f * focusRingRadius, 2.0f * focusRingRadius);
        focusRingStroke = new BasicStroke(FOCUS_RING_STROKE_WIDTH);
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

    @SuppressWarnings("synthetic-access")
    private void registerEvents() {

        this.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(final ComponentEvent event) {
                super.componentResized(event);
                initGeometry();
                repaint(); // important to account for changed swatch geometry!
            }
        });

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(final MouseEvent event) {
                if (isEnabled())
                    fireActionEvent();
            }

            @Override
            public void mousePressed(final MouseEvent event) {
                if (isEnabled() && boundingRectangle.contains(event.getPoint())) {
                    mousePressed = true;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(final MouseEvent event) {
                if (isEnabled()) {
                    mousePressed = false;
                    repaint();
                }
            }

            @Override
            public void mouseEntered(final MouseEvent event) {
                if (isEnabled()) {
                    mouseInBounds = true;
                    repaint();
                }
            }

            @Override
            public void mouseExited(final MouseEvent event) {
                if (isEnabled()) {
                    mouseInBounds = false;
                    repaint();
                }
            }
        });
    }
}
