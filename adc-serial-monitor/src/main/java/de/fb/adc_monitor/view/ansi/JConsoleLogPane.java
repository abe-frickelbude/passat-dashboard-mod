package de.fb.adc_monitor.view.ansi;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;
import java.io.PrintStream;
import javax.swing.JPanel;
import org.apache.commons.io.output.TeeOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JConsoleLogPane extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(JConsoleLogPane.class);

    private static final int DEFAULT_CAPTURE_BUFFER_SIZE = 1024; // 1KB

    private final JAnsiTextPane ansiTextPane;

    private int captureBufferSize;
    private boolean captureStandardStreams;

    private PrintStream stdOutStream;
    private PrintStream stdErrStream;

    // TODO: expose some more useful properties i.e font and stuff
    public JConsoleLogPane() {

        super();
        captureBufferSize = DEFAULT_CAPTURE_BUFFER_SIZE;

        ansiTextPane = new JAnsiTextPane();
        ansiTextPane.setFont(new Font("Verdana", Font.PLAIN, 11));
        ansiTextPane.setMargin(new Insets(5, 5, 5, 5));
        ansiTextPane.setVerifyInputWhenFocusTarget(false);
        ansiTextPane.setEditable(false);
        add(ansiTextPane, BorderLayout.CENTER);
    }

    public void setColorScheme(final String schemeName) {
        ansiTextPane.setColorScheme(schemeName);
    }

    public void setColorsEnabled(final boolean colorsEnabled) {
        ansiTextPane.setColorsEnabled(colorsEnabled);
    }

    public void setUseDefaultLafColors(final boolean useDefaultColors) {
        ansiTextPane.setUseDefaultLafColors(useDefaultColors);
    }

    public void setCaptureStandardStreams(final boolean captureStandardStreams) {
        this.captureStandardStreams = captureStandardStreams;
        captureStandardStreams(captureStandardStreams);
    }

    public void setCaptureBufferSize(final int captureBufferSize) {
        if(captureBufferSize > 0) {
            this.captureBufferSize = captureBufferSize;
        } else {
            this.captureBufferSize = DEFAULT_CAPTURE_BUFFER_SIZE;
        }
    }

    public void clear() {
        ansiTextPane.clear();
    }

    public void appendMessage(final String text) {

        // WIP
        ansiTextPane.append(text);
    }

    @SuppressWarnings("resource")
    private void captureStandardStreams(final boolean capture) {

        if (capture) {

            // split System.out and System.err and direct the cloned streams to the logging area
            PrintStream sysout = System.out;
            stdOutStream = System.out;

            TeeOutputStream sysoutSplitter = new TeeOutputStream(sysout,
                new MessageOutputStream(captureBufferSize, this::appendMessage));

            PrintStream sysoutTeeWrapper = new PrintStream(sysoutSplitter, true);
            System.setOut(sysoutTeeWrapper);

            PrintStream syserr = System.err;
            stdErrStream = System.err;

            TeeOutputStream syserrSplitter = new TeeOutputStream(syserr,
                new MessageOutputStream(captureBufferSize, this::appendMessage));

            PrintStream syserrTeeWrapper = new PrintStream(syserrSplitter, true);
            System.setErr(syserrTeeWrapper);

            log.info("System.out and System.err mirrored!");

        } else {
            // restore original system streams
            if (stdOutStream != null) {
                System.setOut(stdOutStream);
            }
            if (stdErrStream != null) {
                System.setErr(stdErrStream);
            }
            log.info("System.out and System.err restored!");
        }
    }
}
