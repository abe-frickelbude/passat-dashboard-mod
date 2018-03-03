package de.fb.arduino_sandbox.view.component.color;

import javax.swing.JButton;
import javax.swing.JComponent;

public class DualButton extends JComponent {

    private final JButton primaryButton;
    private final JButton secondaryButton;

    public DualButton() {
        primaryButton = new JButton();
        secondaryButton = new JButton();
        init();
    }

    public String getPrimaryText() {
        return primaryButton.getText();
    }

    public void setPrimaryText(final String text) {
        primaryButton.setText(text);
    }

    public String getSecondaryText() {
        return primaryButton.getText();
    }

    public void setSecondaryText(final String text) {
        secondaryButton.setText(text);
    }

    private void init() {

    }
}
