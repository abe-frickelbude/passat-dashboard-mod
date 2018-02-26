package de.fb.adc_monitor.controller;

import java.util.List;
import javax.swing.JPanel;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import de.fb.adc_monitor.service.ArduinoLinkService;
import de.fb.adc_monitor.view.*;

/**
 * Controller and glue logic for the MainWindow view class.
 * 
 * @author ibragim
 * 
 */
@SuppressWarnings("unused")
@Component("mainWindowController")
public class MainWindowController {

    private static final Logger log = LoggerFactory.getLogger(MainWindowController.class);

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private ArduinoLinkService arduinoLinkService;

    public MainWindowController() {

    }

    public void showActivityControlPanel(final JPanel controlPanel) {
        final ControlWindow controlWindow = appContext.getBean(ControlWindow.class);
        controlWindow.setControlPanel(controlPanel);
    }

    public List<String> getAvailablePorts() {
        return arduinoLinkService.getAvailablePorts();
    }

    public void connect(final SerialPortParams params) {
        arduinoLinkService.connect(params);
    }

    public Boolean requestAppExit() {

        /*
         * EXTEND IF NECESSARY: check for pending and ongoing operations and deny exit (perhaps call back to view)
         * as long as there any active tasks in the queue.
         */
        return Boolean.TRUE;
    }

    public void exitApp() {
        arduinoLinkService.disconnect();
        // EXTEND IF NECESSARY add any necessary application context and resources cleanup code
        System.exit(0);
    }
}
