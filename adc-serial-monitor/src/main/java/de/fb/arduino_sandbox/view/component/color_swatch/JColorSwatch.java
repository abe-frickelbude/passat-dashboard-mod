package de.fb.arduino_sandbox.view.component.color_swatch;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.MessageFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import org.apache.commons.collections4.CollectionUtils;

public class JColorSwatch extends JComponent {

    private static final String TOOLTIP_TEMPLATE = "<html><h4>RGBA: ({0}, {1}, {2}, {3})<br/>HEX(ARGB): {4}</h4></html>";

    public static final Color DEFAULT_COLOR = Color.GRAY;
    public static final Color DEFAULT_BORDER_COLOR = Color.WHITE;

    // all dimensions are in pixels
    public static final int DEFAULT_SWATCH_SIZE = 16;
    public static final int DEFAULT_BORDER_WIDTH = 1;
    public static final int DEFAULT_CORNER_RADIUS = 8;
    public static final int DEFAULT_CURSOR_SIZE = 8;

    // immutables
    private final Color borderColor;
    private final int swatchSize;

    private final int cursorSize;

    private final int borderWidth;
    private final int cornerRadius;

    // state
    private Color color;
    private List<ChangeListener> changeListeners;

    public JColorSwatch() {
        this(Color.GRAY, Color.WHITE,
            DEFAULT_SWATCH_SIZE, DEFAULT_BORDER_WIDTH,
            DEFAULT_CORNER_RADIUS, false, DEFAULT_CURSOR_SIZE);
    }

    private JColorSwatch(final Color color, final Color borderColor, final int swatchSize,
        final int borderWidth, final int cornerRadius,
        final boolean useCustomCursor, final int cursorSize) {

        super();
        setColor(color);

        this.borderColor = borderColor;
        this.swatchSize = swatchSize;
        this.borderWidth = borderWidth;
        this.cornerRadius = cornerRadius;
        this.cursorSize = cursorSize;
        changeListeners = Collections.emptyList();

        if (useCustomCursor) {
            setCustomCursor();
        }

        registerEvents();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(final Color color) {
        this.color = color;
        setTooltip();
        repaint();
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
    public Dimension getMinimumSize() {
        return calculateComponentDimensions();
    }

    @Override
    public Dimension getMaximumSize() {
        return calculateComponentDimensions();
    }

    @Override
    public Dimension getPreferredSize() {
        return calculateComponentDimensions();
    }

    @Override
    protected void paintComponent(final Graphics g) {

        Graphics2D ctx = Graphics2D.class.cast(g);
        ctx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int fullWidth = swatchSize + 2 * borderWidth;

        ctx.setBackground(getBackground());
        ctx.clearRect(0, 0, fullWidth, fullWidth);

        // draw border
        ctx.setColor(borderColor);
        ctx.fillRoundRect(0, 0, fullWidth, fullWidth, cornerRadius, cornerRadius);

        // draw swatch
        ctx.setColor(color);
        ctx.fillRoundRect(borderWidth, borderWidth, swatchSize, swatchSize, cornerRadius, cornerRadius);
    }

    // set component tooltip based on current color
    private void setTooltip() {
        final String text = MessageFormat.format(TOOLTIP_TEMPLATE,
            color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(),
            Integer.toHexString(color.getRGB()).toUpperCase());

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

    private Dimension calculateComponentDimensions() {
        return new Dimension(swatchSize + 2 * borderWidth + 1, swatchSize + 2 * borderWidth + 1);
    }

    private void registerEvents() {

        addMouseListener(new MouseAdapter() {

            @Override
            @SuppressWarnings("synthetic-access")
            public void mouseClicked(final MouseEvent event) {
                handleClicks(event);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            @SuppressWarnings("synthetic-access")
            public void mouseDragged(final MouseEvent event) {
                handleDrags(event);
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

    @SuppressWarnings("unused")
    private void handleDrags(final MouseEvent event) {
        // if (getBounds().contains(event.getPoint())) {
        // System.out.println(event.getPoint().toString());
        // }
    }

    private void pickColor() {
        final Color newColor = JColorChooser.showDialog(null, "Pick color", color);
        if (newColor != null) {
            setColor(newColor);
            fireChangeEvent();
        }
    }

    private void showInfoDialog() {
        final ColorSwatchInfoDialog dialog = new ColorSwatchInfoDialog(getParentWindow(), color);
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
            final ChangeEvent event = new ChangeEvent(color);
            for (ChangeListener listener : changeListeners) {
                listener.stateChanged(event);
            }
        }
    }

    @SuppressWarnings("hiding")
    public static class Builder {

        private Color color = DEFAULT_COLOR;
        private Color borderColor = DEFAULT_BORDER_COLOR;

        private int swatchSize = DEFAULT_SWATCH_SIZE;
        private int borderWidth = DEFAULT_BORDER_WIDTH;
        private int cornerRadius = DEFAULT_CORNER_RADIUS;

        private boolean useCustomCursor = false;
        private int cursorSize = DEFAULT_CURSOR_SIZE;

        public Builder() {}

        public static Builder create() {
            return new Builder();
        }

        public Builder color(final Color color) {
            this.color = color;
            return this;
        }

        public Builder borderColor(final Color color) {
            this.borderColor = color;
            return this;
        }

        public Builder swatchSize(final int size) {
            this.swatchSize = size;
            return this;
        }

        public Builder borderWidth(final int width) {
            this.borderWidth = width;
            return this;
        }

        public Builder cornerRadius(final int radius) {
            this.cornerRadius = radius;
            return this;
        }

        public Builder useCustomCursor(final boolean useCursor) {
            this.useCustomCursor = useCursor;
            return this;
        }

        public Builder cursorSize(final int size) {
            this.cursorSize = size;
            return this;
        }

        @SuppressWarnings("synthetic-access")
        public JColorSwatch build() {
            return new JColorSwatch(color, borderColor, swatchSize, borderWidth, cornerRadius, useCustomCursor, cursorSize);
        }
    }
}
