package de.fb.adc_monitor.controller;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;
import javax.annotation.*;
import javax.swing.JPanel;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.lang3.tuple.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import de.fb.adc_monitor.math.*;
import de.fb.adc_monitor.service.ArduinoLinkService;
import de.fb.adc_monitor.util.*;
import de.fb.adc_monitor.view.SerialPortParams;
import de.fb.adc_monitor.view.filter.*;
import info.monitorenter.gui.chart.*;

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

    private final AtomicInteger updateFrequency;
    private final AtomicBoolean updateActive;
    private final AtomicLong startTime;

    private final ConcurrentCircularFifoQueue<Integer> adcSampleBuffer;

    private Thread chartUpdateThread;
    private AtomicBoolean viewDisposed;

    private BiConsumer<ITracePoint2D, ITracePoint2D> updateChartCallback;
    private Runnable clearChartCallback;

    // maps filter type -> filter and its associated parameter control box
    private Map<FilterType, Pair<SignalFilter, JPanel>> signalFilterMap;
    private AtomicReference<SignalFilter> currentFilter;
    private Consumer<JPanel> selectFilterCallback;

    public MainWindowController() {

        createFilters();

        final CircularFifoQueue<Integer> sampleQueue = new CircularFifoQueue<>(SAMPLE_BUFFER_SIZE);
        adcSampleBuffer = new ConcurrentCircularFifoQueue<>(sampleQueue);

        updateFrequency = new AtomicInteger(Constants.DEFAULT_DISPLAY_UPDATE_FREQUENCY);
        updateActive = new AtomicBoolean(false);
        startTime = new AtomicLong(0);

        viewDisposed = new AtomicBoolean(false);

        chartUpdateThread = new Thread(this::updateChart, "chart update thread");
        chartUpdateThread.start();
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

    public void setUpdateFrequency(final int frequency) {
        updateFrequency.set(frequency);
    }

    public void setUpdateChartCallback(final BiConsumer<ITracePoint2D, ITracePoint2D> callback) {
        this.updateChartCallback = callback;
    }

    public void setClearChartCallback(final Runnable callback) {
        this.clearChartCallback = callback;
    }

    public void setSelectFilterCallback(final Consumer<JPanel> callback) {
        this.selectFilterCallback = callback;
    }

    public List<String> getAvailablePorts() {
        return arduinoLinkService.getAvailablePorts();
    }

    public void connect(final SerialPortParams params) {
        arduinoLinkService.connect(params);
    }

    public void selectFilter(final FilterType filterType) {

        final Pair<SignalFilter, JPanel> filterBox = signalFilterMap.get(filterType);
        currentFilter.set(filterBox.getLeft());

        // set parameter control box in the view
        selectFilterCallback.accept(filterBox.getRight());
        log.info("Selected filter type: {}", filterType);
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

    private void createFilters() {

        signalFilterMap = new HashMap<>();

        SimpleExponentialFilter filter1 = new SimpleExponentialFilter();
        SimpleExponentialControlBox box1 = new SimpleExponentialControlBox(filter1);
        signalFilterMap.put(FilterType.SIMPLE_EXPONENTIAL, new ImmutablePair<SignalFilter, JPanel>(filter1, box1));

        DoubleExponentialFilter filter2 = new DoubleExponentialFilter();
        DoubleExponentialControlBox box2 = new DoubleExponentialControlBox(filter2);
        signalFilterMap.put(FilterType.DOUBLE_EXPONENTIAL, new ImmutablePair<SignalFilter, JPanel>(filter2, box2));

        KalmanFilter filter3 = new KalmanFilter();
        KalmanControlBox box3 = new KalmanControlBox(filter3);
        signalFilterMap.put(FilterType.KALMAN, new ImmutablePair<SignalFilter, JPanel>(filter3, box3));

        currentFilter = new AtomicReference<>(signalFilterMap.get(FilterType.SIMPLE_EXPONENTIAL).getLeft());
    }

    private void updateChart() {

        // viewDisposed is used for thread shutdown
        while (viewDisposed.get() == false) {

            final long localStart = System.nanoTime();

            if (updateActive.get()) {
                double currentTime = TimeUtils.nanosToSeconds(System.nanoTime() - startTime.get());
                Integer nextSample = adcSampleBuffer.poll();

                if (nextSample != null) {

                    final double voltageSample = AdcUtils.mapAdcSampleToVoltage(nextSample);
                    final TracePoint2D point1 = new TracePoint2D(currentTime, voltageSample);

                    final double filteredSample = currentFilter.get().addValue(voltageSample);
                    final TracePoint2D point2 = new TracePoint2D(currentTime, filteredSample);

                    updateChartCallback.accept(point1, point2);
                }
            }

            final long execTime = System.nanoTime() - localStart;
            nanoSleep(calculateSleepPeriod(updateFrequency.get(), execTime));
        }
    }

    /*
     * General formula here is: sleep period = Math.round(nano(((1.0) / update frequency)) - execTime,
     * which gives the number of nanoseconds to sleep in order to hit the target refresh frequency.
     * 
     * The value is approximate, of course, given that profiling the execution time of the block being executed is itself
     * approximate.
     */
    private long calculateSleepPeriod(final int frequency, final long execTime) {
        long idealPeriod = Math.round(Constants.SECONDS_TO_NANOS * (1.0 / frequency));
        return idealPeriod - execTime;
    }

    // uses a wait loop as Thread.sleep() is not accurate enough for this!
    private void nanoSleep(final long sleepTime) {
        long elapsed = 0;
        final long start = System.nanoTime();
        while (elapsed < sleepTime) {
            elapsed = System.nanoTime() - start;
        }
    }

    // only stuff here that has to be executed post-DI
    @PostConstruct
    private void init() {
        arduinoLinkService.setAdcSampleConsumer(INPUT_PIN, this::updateSampleBuffer);
    }

    @PreDestroy
    private void cleanup() {
        viewDisposed.set(true);
    }

    private void updateSampleBuffer(final Integer sample) {
        adcSampleBuffer.offer(sample);
    }
}
