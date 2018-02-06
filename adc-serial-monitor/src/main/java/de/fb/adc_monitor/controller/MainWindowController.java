package de.fb.adc_monitor.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import de.fb.adc_monitor.service.ArduinoLinkService;
import de.fb.adc_monitor.util.TimeUtils;
import de.fb.adc_monitor.view.SerialPortParams;
import info.monitorenter.gui.chart.ITracePoint2D;
import info.monitorenter.gui.chart.TracePoint2D;

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

    private static final int INPUT_PIN = 0; // ?

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private ArduinoLinkService arduinoLinkService;

    private final AtomicBoolean updateActive;
    private final AtomicLong startTime;

    private BiConsumer<ITracePoint2D, ITracePoint2D> updateChartCallback;
    private Runnable clearChartCallback;

    public MainWindowController() {
        updateActive = new AtomicBoolean(false);
        startTime = new AtomicLong(0);
    }

    public void start() {
        if (!updateActive.get()) {
            startTime.set(System.nanoTime());
            clearChartCallback.run();
            updateActive.set(true);
        }
    }

    public void stop() {
        if (updateActive.get()) {
            updateActive.set(false);
        }
    }

    public void setUpdateChartCallback(final BiConsumer<ITracePoint2D, ITracePoint2D> updateChartCallback) {
        this.updateChartCallback = updateChartCallback;
    }

    public void setClearChartCallback(final Runnable clearChartCallback) {
        this.clearChartCallback = clearChartCallback;
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

    @Scheduled(fixedRate = 10)
    public void updateChart() {

        if (updateActive.get()) {

            double currentTime = TimeUtils.nanosToSeconds(System.nanoTime() - startTime.get());

            ////////////////////// TEST //////////////////////////////////

            TracePoint2D point1 = new TracePoint2D(currentTime, Math.sin(2 * Math.PI * currentTime));
            TracePoint2D point2 = new TracePoint2D(currentTime, Math.cos(2 * Math.PI * currentTime));

            updateChartCallback.accept(point1, point2);
        }
    }
}
