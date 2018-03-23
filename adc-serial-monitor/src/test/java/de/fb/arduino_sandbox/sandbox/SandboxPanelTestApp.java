package de.fb.arduino_sandbox.sandbox;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bulenkov.darcula.DarculaLaf;

public class SandboxPanelTestApp {

    private static final Logger log = LoggerFactory.getLogger(SandboxPanelTestApp.class);

    public static void main(final String[] args) {

        try {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (Exception ex) {}

        final JFrame mainWindow = new JFrame();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainWindow.setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        final SandboxPanel sandboxPanel = new SandboxPanel();

        final JScrollPane scrollPane = new JScrollPane(sandboxPanel);

        // contentPane.add(ledGroupSwitch, BorderLayout.CENTER);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        mainWindow.setBounds(100, 100, 1024, 768);
        mainWindow.setVisible(true);
    }
}
