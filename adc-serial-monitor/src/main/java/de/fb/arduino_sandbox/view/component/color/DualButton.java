package de.fb.arduino_sandbox.view.component.color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;

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

        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 2));

        primaryButton.setHorizontalTextPosition(SwingConstants.LEFT);
        primaryButton.setHorizontalAlignment(SwingConstants.LEFT);
        primaryButton.setFont(new Font("Dialog", Font.BOLD, 12));
        primaryButton.setText("+");
        primaryButton.setMinimumSize(new Dimension(30, 20));
        primaryButton.setMaximumSize(new Dimension(30, 20));
        primaryButton.setPreferredSize(new Dimension(40, 24));

        secondaryButton.setHorizontalTextPosition(SwingConstants.LEFT);
        secondaryButton.setHorizontalAlignment(SwingConstants.LEFT);
        secondaryButton.setFont(new Font("Dialog", Font.BOLD, 12));
        secondaryButton.setText("-");
        secondaryButton.setPreferredSize(new Dimension(40, 24));

        this.add(primaryButton);
        this.add(secondaryButton);

    }
}
