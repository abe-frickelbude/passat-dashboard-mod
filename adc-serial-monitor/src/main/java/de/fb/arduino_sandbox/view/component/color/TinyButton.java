package de.fb.arduino_sandbox.view.component.color;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;

/**
 * 
 *
 */
public class TinyButton extends JComponent {

    private static final Dimension DEFAULT_SIZE = new Dimension(16, 16);

    private char label;

    public TinyButton() {
        super();
        setMinimumSize(DEFAULT_SIZE);
        setMaximumSize(DEFAULT_SIZE);
        setPreferredSize(DEFAULT_SIZE);

        label = 'M';
    }

    public char getLabel() {
        return label;
    }

    public void setLabel(final char label) {
        this.label = label;
    }

    @Override
    protected void paintComponent(final Graphics g) {

        Graphics2D ctx = Graphics2D.class.cast(g);
        ctx.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        ctx.setBackground(getBackground());
        ctx.clearRect(0, 0, getWidth(), getHeight());

        ctx.setFont(getFont());
        final FontMetrics fontMetrics = ctx.getFontMetrics();

        int charWidth = fontMetrics.charWidth(label);

        int center_x = getWidth() / 2;
        int center_y = getHeight() / 2;

        ctx.drawChars(new char[] {
            label
        }, 0, 1, center_x - charWidth / 2, center_y + 4);

    }

    @Override
    protected void paintBorder(final Graphics g) {
        // TODO Auto-generated method stub
        super.paintBorder(g);
    }
}
