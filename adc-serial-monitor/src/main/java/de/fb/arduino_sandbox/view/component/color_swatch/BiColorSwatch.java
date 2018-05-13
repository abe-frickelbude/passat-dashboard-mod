package de.fb.arduino_sandbox.view.component.color_swatch;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.commons.collections4.CollectionUtils;

@Deprecated
public class BiColorSwatch extends JComponent {

    private static final String TOOLTIP_TEMPLATE = "<html><h4>RGBA: ({0}, {1}, {2}, {3})<br/>HEX(ARGB): {4}</h4></html>";

    public static final Color DEFAULT_PRIMARY_COLOR = Color.GRAY;
    public static final Color DEFAULT_SECONDARY_COLOR = Color.GRAY.darker();
    public static final Color DEFAULT_BORDER_COLOR = Color.WHITE;

    // all dimensions are in pixels!
    public static final Dimension DEFAULT_SWATCH_SIZE = new Dimension(32, 32);
    public static final int DEFAULT_BORDER_WIDTH = 1;
    public static final int DEFAULT_CORNER_RADIUS = 8;
    public static final int DEFAULT_CURSOR_SIZE = 8;

    private Color primaryColor;
    private Color secondaryColor;

    private boolean borderEnabled;
    private int borderWidth;
    private int cornerRadius;
    private Color borderColor;

    private int cursorSize;
    private boolean customCursorEnabled;

    private List<ChangeListener> changeListeners;

    public BiColorSwatch() {

        super();
        this.borderColor = DEFAULT_BORDER_COLOR;
        this.borderWidth = DEFAULT_BORDER_WIDTH;
        this.cornerRadius = DEFAULT_CORNER_RADIUS;
        this.cursorSize = DEFAULT_CURSOR_SIZE;
        this.customCursorEnabled = false;
        this.borderEnabled = false;

        setPrimaryColor(DEFAULT_PRIMARY_COLOR);
        setSecondaryColor(DEFAULT_SECONDARY_COLOR);

        setMinimumSize(DEFAULT_SWATCH_SIZE);
        setMaximumSize(DEFAULT_SWATCH_SIZE);
        setPreferredSize(DEFAULT_SWATCH_SIZE);

        changeListeners = Collections.emptyList();
        registerEvents();
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(final Color color) {
        this.primaryColor = color;
        setTooltip();
        repaint();
    }

    public Color getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(final Color secondaryColor) {
        this.secondaryColor = secondaryColor;
        repaint();
    }

    public boolean isCustomCursorEnabled() {
        return customCursorEnabled;
    }

    public void setCustomCursorEnabled(final boolean enabled) {
        this.customCursorEnabled = enabled;
        if (enabled) {
            setCustomCursor();
        } else {
            setCursor(Cursor.getDefaultCursor());
        }
    }

    public int getCursorSize() {
        return cursorSize;
    }

    public void setCursorSize(final int cursorSize) {
        this.cursorSize = cursorSize;
    }

    public boolean isBorderEnabled() {
        return borderEnabled;
    }

    public void setBorderEnabled(final boolean borderEnabled) {
        this.borderEnabled = borderEnabled;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(final int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(final Color borderColor) {
        this.borderColor = borderColor;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(final int cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void addChangeListener(final ChangeListener listener) {
        if (CollectionUtils.isEmpty(changeListeners)) {
            changeListeners = new ArrayList<>();
        }
        changeListeners.add(listener);
    }

    public void removeChangeListener(final ChangeListener listener) {

        if (CollectionUtils.isNotEmpty(changeListeners)) {
            changeListeners.remove(listener);
        }
        if (CollectionUtils.isEmpty(changeListeners)) {
            changeListeners = Collections.emptyList();
        }
    }

    @Override
    protected void paintComponent(final Graphics g) {

        Graphics2D ctx = Graphics2D.class.cast(g);
        ctx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ctx.setBackground(getBackground());
        ctx.clearRect(0, 0, getWidth(), getHeight());

        final int cornerDiameter = 2 * cornerRadius;

        if (borderEnabled) {
            // draw border (it easier to simply fill a rectangle in the border color and then
            // fill a smaller inner rectangle with the foreground color)
            ctx.setColor(borderColor);
            ctx.fillRoundRect(0, 0, getWidth(), getHeight(), cornerDiameter, cornerDiameter);
        }

        // draw swatch
        final int x = borderEnabled ? borderWidth : 0;
        final int y = borderEnabled ? borderWidth : 0;
        final int width = borderEnabled ? (getWidth() - 2 * borderWidth) : getWidth();
        final int height = borderEnabled ? (getHeight() - 2 * borderWidth) : getHeight();

        final int halfWidth = Math.round(width / 2.0f);
        // final int halfHeight = Math.round(height / 2.0f);

        ctx.setColor(primaryColor);
        ctx.fillRoundRect(x, y, halfWidth, height, cornerDiameter, cornerDiameter);

        ctx.fillRect(x + halfWidth - cornerRadius, y, cornerRadius, height);

        // overdraw the right round corners with a normal rectangle
        ctx.fillRect(x + halfWidth - cornerRadius, y, cornerRadius, height);

        ctx.setColor(secondaryColor);
        ctx.fillRoundRect(x + halfWidth, y, halfWidth, height, cornerDiameter, cornerDiameter);

        // overdraw the left round corners with a normal rectangle
        ctx.fillRect(x + halfWidth, y, cornerRadius, height);
    }

    // set component tooltip based on current color
    private void setTooltip() {
        final String text = MessageFormat.format(TOOLTIP_TEMPLATE,
            primaryColor.getRed(), primaryColor.getGreen(), primaryColor.getBlue(), primaryColor.getAlpha(),
            Integer.toHexString(primaryColor.getRGB()).toUpperCase());

        setToolTipText(text);
    }

    private void setCustomCursor() {

        Dimension dim = getToolkit().getBestCursorSize(DEFAULT_CURSOR_SIZE, DEFAULT_CURSOR_SIZE);

        // see javadoc for getBestCursorSize() on why the image has these dimensions and not [cursorSize x cursorSize]
        BufferedImage cursorImage = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D ctx = Graphics2D.class.cast(cursorImage.getGraphics());

        // be aware that there's no "partial" transparency support (at least on Windows), alpha has to be 0 to
        // have the desired effect!
        Color transparentBackground = new Color(0.0f, 0.0f, 0.0f, 0.0f);
        ctx.setBackground(transparentBackground);
        ctx.clearRect(0, 0, dim.width, dim.height);

        ctx.setColor(Color.BLACK);
        ctx.drawOval(0, 0, cursorSize - 1, cursorSize - 1);
        ctx.dispose();

        final Cursor cursor = getToolkit().createCustomCursor(cursorImage,
            new Point(cursorSize / 2, cursorSize / 2),
            "swatch cursor");
        setCursor(cursor);
    }

    private void registerEvents() {

        addMouseListener(new MouseAdapter() {

            @Override
            @SuppressWarnings("synthetic-access")
            public void mouseClicked(final MouseEvent event) {
                handleClicks(event);
            }
        });
    }

    private void handleClicks(final MouseEvent event) {
        if (SwingUtilities.isLeftMouseButton(event)) {
            pickColor();
        } else if (SwingUtilities.isRightMouseButton(event)) {
            showInfoDialog();
        }
    }

    private void pickColor() {
        final Color newColor = JColorChooser.showDialog(null, "Pick color", primaryColor);
        if (newColor != null) {
            setPrimaryColor(newColor);
            fireChangeEvent();
        }
    }

    private void showInfoDialog() {
        final ColorSwatchInfoDialog dialog = new ColorSwatchInfoDialog(getParentWindow(), primaryColor);
        dialog.setVisible(true);
    }

    private JFrame getParentWindow() {
        // walk up the hierarchy until we find the top-most JFrame owner of this component, if any
        Container parent = getParent();
        while (parent.getParent() != null) {
            parent = parent.getParent();
        }
        return parent instanceof JFrame ? JFrame.class.cast(parent) : null;
    }

    private void fireChangeEvent() {
        if (CollectionUtils.isNotEmpty(changeListeners)) {
            final ChangeEvent event = new ChangeEvent(primaryColor);
            for (ChangeListener listener : changeListeners) {
                listener.stateChanged(event);
            }
        }
    }
}
