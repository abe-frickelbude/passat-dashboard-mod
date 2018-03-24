package de.fb.arduino_sandbox.view.component.color;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.UIManager;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.IkonHandler;
import org.kordamp.ikonli.swing.FontIcon;
import org.kordamp.ikonli.swing.IkonResolver;

/**
 * 
 *
 */
public class TinyButton extends JComponent {

    private static final Dimension DEFAULT_SIZE = new Dimension(20, 20);
    private static final int BORDER_WIDTH = 1;
    private static final int CORNER_RADIUS = 2;
    private static final float DEFAULT_ICON_SIZE_RATIO = 0.7f;

    private static final Color DEFAULT_BORDER_COLOR = new Color(100, 100, 100);
    private static final Color DEFAULT_HOVER_BORDER_COLOR = new Color(150, 150, 150);
    private static final Color DEFAULT_CLICK_BORDER_COLOR = new Color(200, 200, 200);
    private static final Color DEFAULT_INACTIVE_COLOR = new Color(100, 100, 100);

    // graphical state
    private Color topColor;
    private Color bottomColor;
    private Color borderColor;
    private Color hoverBorderColor;
    private Color clickBorderColor;

    private FontIcon icon;
    private Image iconImage;
    private float iconSizeRatio;
    private Point2D iconPosition;

    // x,y, full width, full height, width, height, corner diameter
    private final int[] buttonGeometry;
    private final Rectangle boundingRectangle;

    private boolean mouseInBounds;
    private boolean mousePressed;

    private List<ActionListener> actionListeners;
    private List<Runnable> actionCallbacks;

    public TinyButton() {
        super();
        buttonGeometry = new int[7];
        boundingRectangle = new Rectangle();
        iconSizeRatio = DEFAULT_ICON_SIZE_RATIO;

        actionCallbacks = Collections.emptyList();
        actionListeners = Collections.emptyList();

        initColors();
        setMinimumSize(DEFAULT_SIZE);
        setMaximumSize(DEFAULT_SIZE);
        setPreferredSize(DEFAULT_SIZE);
        initGeometry();
        registerEvents();
    }

    public Color getTopColor() {
        return topColor;
    }

    public void setTopColor(final Color topColor) {
        this.topColor = topColor;
    }

    public Color getBottomColor() {
        return bottomColor;
    }

    public void setBottomColor(final Color bottomColor) {
        this.bottomColor = bottomColor;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(final Color borderColor) {
        this.borderColor = borderColor;
    }

    public Color getHoverBorderColor() {
        return hoverBorderColor;
    }

    public void setHoverBorderColor(final Color hoverBorderColor) {
        this.hoverBorderColor = hoverBorderColor;
    }

    public Color getClickBorderColor() {
        return clickBorderColor;
    }

    public void setClickBorderColor(final Color clickBorderColor) {
        this.clickBorderColor = clickBorderColor;
    }

    public FontIcon getFontIcon() {
        return icon;
    }

    public void setFontIcon(final FontIcon fontIcon) {
        this.icon = fontIcon;
        if (icon != null) {
            preRenderIcon();
            initGeometry();
        }
    }

    public float getIconSizeRatio() {
        return iconSizeRatio;
    }

    public void setIconSizeRatio(final float iconSizeRatio) {
        this.iconSizeRatio = iconSizeRatio;
        if (iconSizeRatio != 0) {
            initGeometry();
            preRenderIcon();
        }
    }

    public void addActionListener(final ActionListener listener) {
        if (actionListeners.isEmpty()) {
            actionListeners = new ArrayList<>();
        }
        actionListeners.add(listener);
    }

    public void addActionCallback(final Runnable callback) {
        if (actionCallbacks.isEmpty()) {
            actionCallbacks = new ArrayList<>();
        }
        actionCallbacks.add(callback);
    }

    @Override
    public void revalidate() {
        super.revalidate();
        initGeometry();
        preRenderIcon();
    }

    @Override
    public void paint(final Graphics g) {

        Graphics2D ctx = Graphics2D.class.cast(g);
        ctx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // draw border (it easier to simply fill a rectangle in the border color and then
        // fill a smaller inner rectangle with the foreground color)
        if (mouseInBounds && isEnabled()) {
            if (mousePressed) {
                ctx.setColor(clickBorderColor);
            } else {
                ctx.setColor(hoverBorderColor);
            }
        } else {
            ctx.setColor(borderColor);
        }

        // ctx.setColor(mouseInBounds ? hoverBorderColor : borderColor);
        ctx.fillRoundRect(0, 0, buttonGeometry[2], buttonGeometry[3], buttonGeometry[6], buttonGeometry[6]);

        // draw background
        final Paint prevPaint = ctx.getPaint();
        ctx.setPaint(new GradientPaint(0, 0, topColor, 0, getHeight(), bottomColor));
        ctx.fillRoundRect(buttonGeometry[0], buttonGeometry[1],
            buttonGeometry[4], buttonGeometry[5],
            buttonGeometry[6], buttonGeometry[6]);
        ctx.setPaint(prevPaint);

        // draw icon
        if (icon != null) {
            ctx.translate(iconPosition.getX(), iconPosition.getY());
            ctx.drawImage(iconImage, 0, 0, null);
        }
    }

    private void initColors() {

        Color color = UIManager.getColor("Button.darcula.selectedButtonForeground");
        setForeground(color != null ? color : Color.WHITE);

        color = UIManager.getColor("Button.darcula.color1");
        setTopColor(color != null ? color : Color.GRAY);

        color = UIManager.getColor("Button.darcula.color2");
        setBottomColor(color != null ? color : Color.GRAY);

        this.borderColor = DEFAULT_BORDER_COLOR;
        this.hoverBorderColor = DEFAULT_HOVER_BORDER_COLOR;
        this.clickBorderColor = DEFAULT_CLICK_BORDER_COLOR;
    }

    private void preRenderIcon() {

        /*
         * Note: using individual font metrics to calculate the appropriate size for the icon image is complicated and
         * also inaccurate (because the said metrics only return the "general dimensions" of a font character and not the
         * actual dimensions of a particular character), so instead we temporarily convert the icon into an actual
         * GlyphVector and use the resulting Shape's bounding box to create an appropriately sized icon image, and also
         * to render the icon.
         */
        final int width = getPreferredSize().width;
        final int height = getPreferredSize().height;
        final int iconSize = Math.round(height * iconSizeRatio);

        final IkonHandler ikonHandler = IkonResolver.getInstance().resolve(icon.getIkon().getDescription());
        final Font font = ((Font) ikonHandler.getFont()).deriveFont(Font.PLAIN, iconSize);

        // this one is temporary, just to get a Graphics2D instance
        final BufferedImage tmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D ctx = Graphics2D.class.cast(tmp.getGraphics());

        // convert font character into a geometry and retrieve the bounding box
        final GlyphVector glyphVector = font.createGlyphVector(ctx.getFontMetrics(font).getFontRenderContext(),
            new char[] {
                icon.getIkon().getCode()
        });

        final Shape shape = glyphVector.getOutline();
        ctx.dispose();
        // log.info("Glyph bounds for {}: {}", icon.getIkon(), shape.getBounds2D());

        // initialize cached icon image
        iconImage = new BufferedImage(shape.getBounds().width, shape.getBounds().height, BufferedImage.TYPE_INT_ARGB);
        ctx = Graphics2D.class.cast(iconImage.getGraphics());
        ctx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // glyph Y0 is always negative, so the whole coordinate space must be translated by -Y0!
        final AffineTransform xform = new AffineTransform();
        xform.setToTranslation(0.0, -shape.getBounds2D().getY());

        ctx.setColor(getForeground());
        ctx.setTransform(xform);
        ctx.fill(shape);
        ctx.dispose();

        iconPosition = new Point2D.Double(
            0.5 * (width - shape.getBounds2D().getWidth()),
            0.5 * (height - shape.getBounds().getHeight()));
    }

    private void initGeometry() {

        int width = getPreferredSize().width;
        int height = getPreferredSize().height;

        buttonGeometry[0] = BORDER_WIDTH; // x
        buttonGeometry[1] = BORDER_WIDTH; // y
        buttonGeometry[2] = width; // full width
        buttonGeometry[3] = height; // full height
        buttonGeometry[4] = width - 2 * BORDER_WIDTH; // adjusted width
        buttonGeometry[5] = height - 2 * BORDER_WIDTH; // adjusted height
        buttonGeometry[6] = 2 * CORNER_RADIUS; // corner arc diameter

        boundingRectangle.setBounds(0, 0, buttonGeometry[2], buttonGeometry[3]);
    }

    private void fireActionEvent() {

        final ActionEvent event = new ActionEvent(this, 0, StringUtils.EMPTY);
        for (ActionListener listener : actionListeners) {
            listener.actionPerformed(event);
        }
        for (Runnable callback : actionCallbacks) {
            callback.run();
        }
    }

    @SuppressWarnings("synthetic-access")
    private void registerEvents() {

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
