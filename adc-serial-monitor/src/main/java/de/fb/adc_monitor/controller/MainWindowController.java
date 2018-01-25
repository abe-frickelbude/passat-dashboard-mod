package de.fb.adc_monitor.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import de.fb.adc_monitor.service.ArduinoLinkService;
import de.fb.adc_monitor.view.SerialPortParams;

/**
 * Controller logic for the MainWindow view class.
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

    public List<String> getAvailablePorts() {
        return arduinoLinkService.getAvailablePorts();
    }

    public void handleConnectEvent(final SerialPortParams params) {
        arduinoLinkService.connect(params);
    }

    public Boolean handleAppRequestExitEvent() {

        /*
         * EXTEND IF NECESSARY: check for pending and ongoing operations and deny exit (perhaps call back to view)
         * as long as there any active tasks in the queue.
         */
        return Boolean.TRUE;
    }

    public void handleAppExitEvent() {

        arduinoLinkService.disconnect();

        // EXTEND IF NECESSARY add any necessary application context and resources cleanup code
        System.exit(0);
    }
}
