package de.fb.adc_monitor.controller;

import java.awt.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import de.fb.adc_monitor.service.ArduinoLinkService;

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
    private ArduinoLinkService serialPortService;

    public MainWindowController() {

    }

    public Boolean handleAppRequestExitEvent(final ActionEvent event) {

        /*
         * TODO: check for pending and ongoing operations and deny exit (perhaps call back to view)
         * as long as there any active tasks in the queue.
         */
        return Boolean.TRUE;
    }

    public void handleAppExitEvent(final ActionEvent event) {

        // TODO: add any necessary appication context and resources cleanup code
        System.exit(0);
    }
}
