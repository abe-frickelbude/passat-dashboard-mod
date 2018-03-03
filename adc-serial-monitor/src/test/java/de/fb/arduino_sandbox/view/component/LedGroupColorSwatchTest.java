package de.fb.arduino_sandbox.view.component;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bulenkov.darcula.DarculaLaf;
import de.fb.arduino_sandbox.view.component.color.LedGroupColorSwatch;

public class LedGroupColorSwatchTest {

    private static final Logger log = LoggerFactory.getLogger(LedGroupColorSwatchTest.class);

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

        final LedGroupColorSwatch ledGroupSwitch = new LedGroupColorSwatch();

        final JScrollPane scrollPane = new JScrollPane(ledGroupSwitch);

        // contentPane.add(ledGroupSwitch, BorderLayout.CENTER);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        mainWindow.setBounds(100, 100, 1024, 768);
        mainWindow.setVisible(true);
    }
}
