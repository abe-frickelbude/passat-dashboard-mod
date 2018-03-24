package de.fb.arduino_sandbox.view.component.color;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import org.kordamp.ikonli.swing.FontIcon;

public class TinyButtonGroup extends JComponent {

    private static final int DEFAULT_HGAP = 3;
    private static final int DEFAULT_VGAP = 3;

    private int hGap;
    private int vGap;

    private final List<TinyButton> buttons;

    public TinyButtonGroup() {
        buttons = new ArrayList<>();
        hGap = DEFAULT_HGAP;
        vGap = DEFAULT_VGAP;
        init();
    }

    public int getHorizontalGap() {
        return hGap;
    }

    public void setHorizontalGap(final int hGap) {
        this.hGap = hGap;
        init();
    }

    public int getVerticalGap() {
        return vGap;
    }

    public void setVerticalGap(final int vGap) {
        this.vGap = vGap;
        init();
    }

    public void addButton(final FontIcon icon) {
        final TinyButton button = new TinyButton();
        button.setFontIcon(icon);
        buttons.add(button);
        this.add(button);
    }

    public void addButton(final FontIcon icon, final Runnable callback) {
        final TinyButton button = new TinyButton();
        button.setFontIcon(icon);
        button.addActionCallback(callback);
        buttons.add(button);
        this.add(button);
    }

    public TinyButton getButton(final int index) {
        if (buttons.isEmpty()) {
            throw new IllegalArgumentException("Button list is empty!");
        }
        return buttons.get(index);
    }

    private void init() {
        removeAll();
        setLayout(new FlowLayout(FlowLayout.CENTER, hGap, vGap));
        for (final TinyButton button : buttons) {
            this.add(button);
        }
    }
}
