package de.fb.arduino_sandbox.controller;

import java.util.List;
import javax.swing.JPanel;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import de.fb.arduino_sandbox.service.HardwareUplink;
import de.fb.arduino_sandbox.view.*;

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
    private HardwareUplink hardwareLink;

    public MainWindowController() {

    }

    public void showActivityControlPanel(final JPanel controlPanel) {
        final ControlWindow controlWindow = appContext.getBean(ControlWindow.class);
        controlWindow.setControlPanel(controlPanel);
    }

    public List<String> getAvailablePorts() {
        return hardwareLink.getAvailablePorts();
    }

    public void connect(final SerialPortParams params) {
        hardwareLink.connect(params);
    }

    public Boolean requestAppExit() {

        /*
         * EXTEND IF NECESSARY: check for pending and ongoing operations and deny exit (perhaps call back to view)
         * as long as there any active tasks in the queue.
         */
        return Boolean.TRUE;
    }

    public void exitApp() {
        hardwareLink.disconnect();
        // EXTEND IF NECESSARY add any necessary application context and resources cleanup code
        System.exit(0);
    }
}
