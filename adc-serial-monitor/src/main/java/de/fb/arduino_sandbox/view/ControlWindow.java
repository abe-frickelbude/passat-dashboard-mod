package de.fb.arduino_sandbox.view;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.springframework.stereotype.Component;

@Component
public class ControlWindow extends JFrame {

    private final JPanel contentPane;

    public ControlWindow() {

        setTitle("Controls");

        // prevent this window from closing
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 692, 765);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
    }

    public void setControlPanel(final JPanel controlPanel) {
        if (contentPane != null) {
            contentPane.removeAll();
            contentPane.add(controlPanel, BorderLayout.CENTER);
            contentPane.revalidate();
            this.repaint();
        }
    }
}
