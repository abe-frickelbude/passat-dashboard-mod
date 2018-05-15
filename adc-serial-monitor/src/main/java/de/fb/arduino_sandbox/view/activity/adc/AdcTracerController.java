package de.fb.arduino_sandbox.view.activity.adc;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.function.Consumer;
import javax.annotation.*;
import javax.swing.JPanel;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.commons.lang3.tuple.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import de.fb.arduino_sandbox.math.*;
import de.fb.arduino_sandbox.service.ArduinoLinkService;
import de.fb.arduino_sandbox.util.*;
import de.fb.arduino_sandbox.view.TraceData;
import de.fb.arduino_sandbox.view.activity.adc.filter.*;
import info.monitorenter.gui.chart.TracePoint2D;

/**
 * 
 * Note on ADC sample buffer strategy: the sample buffer is a circular FIFO buffer, and once the ArduinoLinkService starts
 * listening on analog pins, will generally fill up a lot quicker than the chart update routine can consume them, so
 * depending on graph update frequency, some samples may be lost.
 */
@Component
public class AdcTracerController {

    private static final Logger log = LoggerFactory.getLogger(AdcTracerController.class);

    private static final int SAMPLE_BUFFER_SIZE = Constants.SAMPLE_BUFFER_SIZE;
    private static final int INPUT_PIN = Constants.PIN_A0;

    private final ApplicationContext appContext;
    private final ArduinoLinkService arduinoLinkService;

    private final AtomicInteger updateFrequency;
    private final AtomicBoolean updateActive;
    private final AtomicLong startTime;

    private final ConcurrentCircularFifoQueue<Integer> adcSampleBuffer;
    private final DescriptiveStatistics signalStatistics;

    private AtomicBoolean usePreFilterThreshold;
    private AtomicBoolean usePostFilterThreshold;

    private Thread chartUpdateThread;
    private AtomicBoolean viewDisposed;

    private Consumer<JPanel> selectFilterCallback;
    private Consumer<TraceData> updateChartCallback;
    private Runnable clearChartCallback;

    // maps filter type -> filter and its associated parameter control box
    private Map<FilterType, Pair<SignalFilter, JPanel>> signalFilterMap;
    private AtomicReference<SignalFilter> currentFilter;

    private SampleAndThresholdFilter preThresholdFilter;
    private SampleAndThresholdFilter postThresholdFilter;

    @Autowired
    public AdcTracerController(final ApplicationContext appContext,
        final ArduinoLinkService arduinoLinkService) {

        this.appContext = appContext;
        this.arduinoLinkService = arduinoLinkService;

        createFilters();

        final CircularFifoQueue<Integer> sampleQueue = new CircularFifoQueue<>(SAMPLE_BUFFER_SIZE);
        adcSampleBuffer = new ConcurrentCircularFifoQueue<>(sampleQueue);

        signalStatistics = new DescriptiveStatistics(SAMPLE_BUFFER_SIZE);
        usePreFilterThreshold = new AtomicBoolean(false);
        usePostFilterThreshold = new AtomicBoolean(false);

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
            signalStatistics.clear();
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

    public void setUsePreFilterThreshold(final boolean enabled) {
        usePreFilterThreshold.set(enabled);
    }

    public void setUsePostFilterThreshold(final boolean enabled) {
        usePostFilterThreshold.set(enabled);
    }

    public void setThresholdValue(final double value) {
        preThresholdFilter.setThreshold(value);
        postThresholdFilter.setThreshold(value);
    }

    public void selectFilter(final FilterType filterType) {

        final Pair<SignalFilter, JPanel> filterBox = signalFilterMap.get(filterType);
        currentFilter.set(filterBox.getLeft());

        // set parameter control box in the view
        selectFilterCallback.accept(filterBox.getRight());
        log.info("Selected filter type: {}", filterType);
    }

    public void setUpdateChartCallback(final Consumer<TraceData> callback) {
        this.updateChartCallback = callback;
    }

    public void setClearChartCallback(final Runnable callback) {
        this.clearChartCallback = callback;
    }

    public void setSelectFilterCallback(final Consumer<JPanel> callback) {
        this.selectFilterCallback = callback;
    }

    private void createFilters() {

        signalFilterMap = new HashMap<>();

        SimpleExponentialFilter filter1 = new SimpleExponentialFilter();
        filter1.setSmoothingFactor(SimpleExponentialControlBox.MIN_SMOOTHING_FACTOR);

        SimpleExponentialControlBox box1 = new SimpleExponentialControlBox(filter1);
        signalFilterMap.put(FilterType.SIMPLE_EXPONENTIAL, new ImmutablePair<SignalFilter, JPanel>(filter1, box1));

        DoubleExponentialFilter filter2 = new DoubleExponentialFilter();
        DoubleExponentialControlBox box2 = new DoubleExponentialControlBox(filter2);
        signalFilterMap.put(FilterType.DOUBLE_EXPONENTIAL, new ImmutablePair<SignalFilter, JPanel>(filter2, box2));

        KalmanFilter filter3 = new KalmanFilter();
        KalmanControlBox box3 = new KalmanControlBox(filter3);
        signalFilterMap.put(FilterType.KALMAN, new ImmutablePair<SignalFilter, JPanel>(filter3, box3));

        currentFilter = new AtomicReference<>(signalFilterMap.get(FilterType.SIMPLE_EXPONENTIAL).getLeft());

        preThresholdFilter = new SampleAndThresholdFilter();
        preThresholdFilter.setThreshold(2 * Constants.ADC_RESOLUTION);

        postThresholdFilter = new SampleAndThresholdFilter();
        postThresholdFilter.setThreshold(2 * Constants.ADC_RESOLUTION);
    }

    private void updateChart() {

        // viewDisposed is used for graceful thread shutdown
        while (viewDisposed.get() == false) {

            final long localStart = System.nanoTime();
            if (updateActive.get()) {

                double currentTime = TimeUtils.nanosToSeconds(System.nanoTime() - startTime.get());
                Integer nextSample = adcSampleBuffer.poll();

                if (nextSample != null) {

                    double voltageSample = AdcUtils.mapAdcSampleToVoltage(nextSample);

                    if (usePreFilterThreshold.get() == true) {
                        voltageSample = preThresholdFilter.addValue(voltageSample);
                    }

                    TracePoint2D inputPoint = new TracePoint2D(currentTime, voltageSample);

                    double filteredSample = currentFilter.get().addValue(voltageSample);

                    if (usePostFilterThreshold.get() == true) {
                        filteredSample = postThresholdFilter.addValue(filteredSample);
                    }

                    TracePoint2D filteredPoint = new TracePoint2D(currentTime, filteredSample);

                    signalStatistics.addValue(filteredSample);

                    TracePoint2D minPoint = new TracePoint2D(currentTime, signalStatistics.getMin());
                    TracePoint2D maxPoint = new TracePoint2D(currentTime, signalStatistics.getMax());
                    TracePoint2D rmsPoint = new TracePoint2D(currentTime, signalStatistics.getQuadraticMean());

                    final TraceData traceData = new TraceData();

                    traceData.setInputPoint(inputPoint);
                    traceData.setFilteredPoint(filteredPoint);

                    traceData.setMinPoint(minPoint);
                    traceData.setMaxPoint(maxPoint);
                    traceData.setRmsPoint(rmsPoint);

                    updateChartCallback.accept(traceData);
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
