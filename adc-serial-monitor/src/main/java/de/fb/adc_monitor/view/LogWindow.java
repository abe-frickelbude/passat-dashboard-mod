package de.fb.adc_monitor.view;

import java.awt.BorderLayout;
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

    // private final JSystemLogArea logMessageArea;
    // private final JSystemLogArea2 logMessageArea;

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
        consoleLogPane.setColorScheme("/ansi_color_schemes/monokai");
        consoleLogPane.setUseDefaultLafColors(true);
        consoleLogPane.setCaptureStandardStreams(true);

        // logMessageArea = new JSystemLogArea();
        // logMessageArea.setRows(40);
        // logMessageArea.setColumns(30);
        // logMessageArea.setMaxLines(25000);
        // logMessageArea.setWrapStyleWord(true);
        // logMessageArea.setMargin(new Insets(5, 5, 5, 5));
        // logMessageArea.setVerifyInputWhenFocusTarget(false);
        // logMessageArea.setTabSize(4);
        // logMessageArea.setLineWrap(true);
        // logMessageArea.setFont(new Font("Verdana", Font.PLAIN, 11));
        // logMessageArea.setEditable(false);
        // logMessageArea = new JSystemLogArea2();
        //
        // logMessageArea.setMargin(new Insets(5, 5, 5, 5));
        // logMessageArea.setVerifyInputWhenFocusTarget(false);
        // logMessageArea.setFont(new Font("Verdana", Font.PLAIN, 11));
        // logMessageArea.setEditable(false);
        //
        final JScrollPane scrollPane = new JScrollPane(consoleLogPane);
        contentPane.add(scrollPane, BorderLayout.CENTER);
    }
    //
    // public final OutputStream getLogSysOutStream() {
    // return logMessageArea.getStdOutStream();
    // }
    //
    // public final OutputStream getLogSysErrStream() {
    // return logMessageArea.getStdErrStream();
    // }
}
