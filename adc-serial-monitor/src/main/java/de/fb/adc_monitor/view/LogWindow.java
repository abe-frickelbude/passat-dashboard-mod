package de.fb.adc_monitor.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import de.fb.adc_monitor.annotations.SwingView;
import de.fb.adc_monitor.view.ansi.JConsoleLogPane;

@SwingView
public class LogWindow extends JFrame {

    private final JPanel contentPane;
    private final JConsoleLogPane consoleLogPane;

    /**
     * Create the frame.
     */
    public LogWindow() {
        setTitle("Log console");

        // prevent this window from closing
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 692, 765);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        consoleLogPane = new JConsoleLogPane();

        // testing
        consoleLogPane.setColorScheme("/ansi_color_schemes/monokai");
        consoleLogPane.setUseDefaultLafColors(true);
        consoleLogPane.setCaptureStandardStreams(true);

        consoleLogPane.setFont(new Font("Verdana", Font.PLAIN, 11));
        consoleLogPane.setMargin(new Insets(5, 5, 5, 5));
        consoleLogPane.setVerifyInputWhenFocusTarget(false);
        consoleLogPane.setEditable(false);

        final JScrollPane scrollPane = new JScrollPane(consoleLogPane);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }
}
