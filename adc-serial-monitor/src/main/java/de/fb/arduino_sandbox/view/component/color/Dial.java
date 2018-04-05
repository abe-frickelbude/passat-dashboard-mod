package de.fb.arduino_sandbox.view.component.color;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import javax.swing.UIManager;

public class Dial extends JComponent {

    private static final Dimension DEFAULT_SIZE = new Dimension(32, 32);

    private static final float ARC_WIDTH = 3.0f;

    private static final float ARC_RADIUS_RATIO = 0.80f;
    private static final float KNOB_RADIUS_RATIO = 0.65f;
    private static final float FOCUS_RING_RADIUS_RATIO = 0.99f;

    private static final Color DEFAULT_ARC_COLOR = new Color(0, 204, 255);
    private static final Color DEFAULT_FOCUS_RING_COLOR = new Color(200, 200, 200);

    // graphical state
    private Color arcColor;
    private Color focusRingColor;

    private float arcRadius;
    private float knobRadius;
    private float focusRingRadius;

    private Arc2D indicatorArc;
    private Ellipse2D knob;
    private Ellipse2D focusRing;

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

        if (mouseInBounds && isEnabled()) {
            if (mousePressed) {
                // ctx.setColor(clickBorderColor);
            } else {
                // ctx.setStroke(new BasicStroke(0.25f));
                ctx.setColor(focusRingColor);
                ctx.draw(focusRing);
            }
        } else {
            // ctx.setColor(borderColor);
        }

        // ctx.setStroke(new BasicStroke(1.0f));

        ctx.fill(knob);

        // ctx.setColor(mouseInBounds ? hoverBorderColor : borderColor);
        // ctx.fillRoundRect(0, 0, buttonGeometry[2], buttonGeometry[3], buttonGeometry[6], buttonGeometry[6]);
        //
        // // draw background
        // final Paint prevPaint = ctx.getPaint();
        // ctx.setPaint(new GradientPaint(0, 0, topColor, 0, getHeight(), bottomColor));
        // ctx.fillRoundRect(buttonGeometry[0], buttonGeometry[1],
        // buttonGeometry[4], buttonGeometry[5],
        // buttonGeometry[6], buttonGeometry[6]);
        // ctx.setPaint(prevPaint);
        //
        // // draw icon
        // if (icon != null) {
        // ctx.translate(iconPosition.getX(), iconPosition.getY());
        // ctx.drawImage(iconImage, 0, 0, null);
        // }
    }

    private void initColors() {

        Color color = UIManager.getColor("Button.darcula.selectedButtonForeground");
        setForeground(color != null ? color : Color.WHITE);

        color = UIManager.getColor("Button.darcula.color1");
        // setTopColor(color != null ? color : Color.GRAY);

        color = UIManager.getColor("Button.darcula.color2");
        // setBottomColor(color != null ? color : Color.GRAY);

        this.arcColor = DEFAULT_ARC_COLOR;
        this.focusRingColor = DEFAULT_FOCUS_RING_COLOR;
        // this.borderColor = DEFAULT_ARC_COLOR;
        // this.hoverBorderColor = DEFAULT_HOVER_BORDER_COLOR;
        // this.clickBorderColor = DEFAULT_CLICK_BORDER_COLOR;
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

        boundingRectangle = new Rectangle(0, 0, width, height);

        focusRingRadius = radius * FOCUS_RING_RADIUS_RATIO;
        knobRadius = radius * KNOB_RADIUS_RATIO;
        arcRadius = radius * ARC_RADIUS_RATIO;

        knob = new Ellipse2D.Float(
            halfWidth - knobRadius, halfHeight - knobRadius,
            2.0f * knobRadius, 2.0f * knobRadius);

        focusRing = new Ellipse2D.Float(
            halfWidth - focusRingRadius, halfHeight - focusRingRadius,
            2.0f * focusRingRadius, 2.0f * focusRingRadius);
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
                if (isEnabled() && boundingRectangle.contains(event.getPoint())) {
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
