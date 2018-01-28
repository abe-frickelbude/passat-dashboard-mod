package de.fb.adc_monitor;

import java.awt.EventQueue;
import javax.swing.UIManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.bulenkov.darcula.DarculaLaf;
import de.fb.adc_monitor.config.AppContextConfiguration;
import de.fb.adc_monitor.view.LogWindow;
import de.fb.adc_monitor.view.MainWindow;
import de.fb.adc_monitor.view.UILayouter;

/**
 * This class essentially ties together Spring application context initialization and any auxiliary initialization of
 * Swing etc, and is ultimately responsible for setting up the Swing GUI.
 * 
 * @author Ibragim Kuliev
 *
 */
public class AdcSerialMonitorApp {

    private static final Logger log = LoggerFactory.getLogger(AdcSerialMonitorApp.class);

    public static void main(final String[] args) {

        configureLogging();
        configureSwingLookAndFeel();

        /*
         * The section below might now be completely self-explanatory, so:
         * Basically the >run< sequence actually consists of two steps - the first one has to initialize the Spring
         * app context along with all SpringBoot magic, and the second one then initializes and displays the Swing UI.
         * For this to work, we need to retrieve the app context from the initialized SpringApplication instance, hence
         * the line below.
         */
        try (final ConfigurableApplicationContext appContext = SpringApplication.run(AppContextConfiguration.class, args)) {

            appContext.registerShutdownHook();

            EventQueue.invokeLater(() -> {
                try {
                    // show the UI windows
                    MainWindow mainWindow = appContext.getBean(MainWindow.class);
                    LogWindow logWindow = appContext.getBean(LogWindow.class);
                    UILayouter uiLayouter = appContext.getBean(UILayouter.class);

                    // captureSystemMessageStreams(logWindow);
                    uiLayouter.layoutWindows(false, 0.7f);

                    mainWindow.setVisible(true);
                    logWindow.setVisible(true);

                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                    System.exit(-1);
                }
            });
        }
    }

    private static void configureLogging() {

        // route java.util.logging through SLF4J
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    private static void configureSwingLookAndFeel() {

        // Swing "platform look and feel" has to be set PRIOR to any component initialization,
        // otherwise it will have no effect!
        try {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (Exception ex) {

            // Note: this is not an unrecoverable exception - the application will just use the default look and feel!
            log.error(ex.getMessage(), ex);
        }
    }

    // @SuppressWarnings("resource")
    // private static void captureSystemMessageStreams(final LogWindow logWindow) {
    //
    // // split System.out and System.err and direct the cloned streams to the logging area
    // PrintStream sysout = System.out;
    // TeeOutputStream sysoutSplitter = new TeeOutputStream(sysout, new
    // BufferedOutputStream(logWindow.getLogSysOutStream()));
    // PrintStream sysoutTeeWrapper = new PrintStream(sysoutSplitter, true);
    // System.setOut(sysoutTeeWrapper);
    //
    // PrintStream syserr = System.err;
    // TeeOutputStream syserrSplitter = new TeeOutputStream(syserr, new
    // BufferedOutputStream(logWindow.getLogSysErrStream()));
    // PrintStream syserrTeeWrapper = new PrintStream(syserrSplitter, true);
    // System.setErr(syserrTeeWrapper);
    //
    // log.info("Log init completed!");
    //
    // //// test
    // for (int i = 0; i < 100; i++) {
    // log.info("Test message {}", i);
    // log.warn("Test warning {}", i);
    // log.error("Test error {}", i);
    // }
    //
    // }
}
