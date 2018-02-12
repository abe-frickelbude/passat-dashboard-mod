package de.fb.adc_monitor.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import javax.annotation.PostConstruct;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import de.fb.adc_monitor.service.ArduinoLinkService;
import de.fb.adc_monitor.util.ConcurrentCircularFifoQueue;
import de.fb.adc_monitor.util.Constants;
import de.fb.adc_monitor.util.TimeUtils;
import de.fb.adc_monitor.view.SerialPortParams;
import info.monitorenter.gui.chart.ITracePoint2D;
import info.monitorenter.gui.chart.TracePoint2D;

/**
 * Controller and glue logic for the MainWindow view class.
 * 
 * Note on ADC sample buffer strategy: the sample buffer is a circular FIFO buffer, and once the ArduinoLinkService starts
 * listening on analog pins, will generally fill up a lot quicker than the chart update routine can consume them, so
 * depending on graph update frequency, some samples may be lost.
 * 
 * @author ibragim
 * 
 */
@SuppressWarnings("unused")
@Component("mainWindowController")
public class MainWindowController {

    private static final Logger log = LoggerFactory.getLogger(MainWindowController.class);

    private static final int SAMPLE_BUFFER_SIZE = Constants.SAMPLE_BUFFER_SIZE;
    private static final int INPUT_PIN = Constants.PIN_A0;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private ArduinoLinkService arduinoLinkService;

    private final AtomicBoolean updateActive;
    private final AtomicLong startTime;

    private final ConcurrentCircularFifoQueue<Integer> adcSampleBuffer;

    private BiConsumer<ITracePoint2D, ITracePoint2D> updateChartCallback;
    private Runnable clearChartCallback;

    public MainWindowController() {

        final CircularFifoQueue<Integer> sampleQueue = new CircularFifoQueue<>(SAMPLE_BUFFER_SIZE);
        adcSampleBuffer = new ConcurrentCircularFifoQueue<>(sampleQueue);

        updateActive = new AtomicBoolean(false);
        startTime = new AtomicLong(0);
    }

    public void start() {
        if (!updateActive.get()) {
            startTime.set(System.nanoTime());
            clearChartCallback.run();
            updateActive.set(true);
            arduinoLinkService.startListening(INPUT_PIN);
        }
    }

    public void stop() {
        if (updateActive.get()) {
            updateActive.set(false);
            arduinoLinkService.stopListening(INPUT_PIN);
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

    @Scheduled(fixedRate = Constants.GRAPH_UPDATE_PERIOD)
    public void updateChart() {

        if (updateActive.get()) {

            double currentTime = TimeUtils.nanosToSeconds(System.nanoTime() - startTime.get());
            Integer nextSample = adcSampleBuffer.poll();

            if (nextSample != null) {
                final TracePoint2D point1 = new TracePoint2D(currentTime, 1.0 * adcSampleBuffer.poll());
                final TracePoint2D point2 = new TracePoint2D(currentTime, 0.0);
                updateChartCallback.accept(point1, point2);
            }
        }
    }

    @PostConstruct
    private void init() {
        arduinoLinkService.setAdcSampleConsumer(INPUT_PIN, this::updateSampleBuffer);
    }

    private void updateSampleBuffer(final Integer sample) {
        adcSampleBuffer.offer(sample);
    }

}
