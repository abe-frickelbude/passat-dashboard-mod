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

    // singleton application context instance
    private static ConfigurableApplicationContext appContext;

    public static void main(final String[] args) {
        configureLogging();
        configureSwingLookAndFeel();
        initializeApp(args);
    }

    private static void initializeApp(final String[] args) {
        /*
         * The section below might now be completely self-explanatory, so:
         * Basically the >run< sequence actually consists of two steps - the first one has to initialize the Spring
         * app context along with all SpringBoot magic, and the second one then initializes and displays the Swing UI.
         * For this to work, we need to retrieve the app context from the initialized SpringApplication instance, hence
         * the line below.
         */
        try {
            appContext = SpringApplication.run(AppContextConfiguration.class, args);
            appContext.registerShutdownHook();

            EventQueue.invokeLater(() -> {
                try {
                    // show the UI windows
                    MainWindow mainWindow = appContext.getBean(MainWindow.class);
                    LogWindow logWindow = appContext.getBean(LogWindow.class);
                    UILayouter uiLayouter = appContext.getBean(UILayouter.class);

                    uiLayouter.layoutWindows(false, 0.7f);
                    mainWindow.setVisible(true);
                    logWindow.setVisible(true);

                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                    System.exit(-1);
                }
            });
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            System.exit(-1);
        }
    }

    private static void configureLogging() {
        // route java.util.logging through SLF4J
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }

    /*
     * Swing "platform look and feel" has to be set PRIOR to any component initialization, otherwise it will have no
     * effect!
     */
    private static void configureSwingLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (Exception ex) {
            // Note: this is not an unrecoverable exception - the application will just use the default look and feel!
            log.error(ex.getMessage(), ex);
        }
    }
}
