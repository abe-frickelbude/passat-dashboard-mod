package de.fb.adc_monitor.sandbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bulenkov.darcula.DarculaLaf;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import de.fb.adc_monitor.math.*;
import de.fb.adc_monitor.view.DarculaUiColors;
import de.fb.adc_monitor.view.component.JHeapMonitor;
import de.fb.adc_monitor.view.component.ZoomableChartView;
import de.fb.adc_monitor.view.filter.DoubleExponentialControlBox;
import de.fb.adc_monitor.view.filter.GuiUtils;
import de.fb.adc_monitor.view.filter.KalmanControlBox;
import de.fb.adc_monitor.view.filter.SimpleExponentialControlBox;
import info.monitorenter.gui.chart.ITrace2D;

public class FilterSandbox {

    private static final Logger log = LoggerFactory.getLogger(FilterSandbox.class);

    // sample generator period
    private static final int SAMPLE_PERIOD = 50;

    // number of points in the input signal dataset, used by the chart traces
    private static final int NUM_CHART_DATA_POINTS = 200;

    // ditto, for stats accumulator - the value is lower so that the stats don't "lag" excessively behind the main
    // trace
    private static final int NUM_STATS_DATA_POINTS = 25;

    // initial conditions
    private static final double MIN_VOLTAGE = 1.0;
    private static final double MAX_VOLTAGE = 5.0;

    private static final double MIN_JITTER = 0.001;
    private static final double MAX_JITTER = 0.500;

    // i.e. ~4.8 mV per bit of resolution when using default Vref of 5V
    private static final double ADC_ACCURACY = 0.048;

    private JFrame mainWindow;

    // controls
    private JTextField voltageField;
    private JTextField jitterField;

    private JSlider voltageSlider;
    private JSlider jitterSlider;

    private JComboBox<FilterType> filterSelectionBox;
    private JButton pauseButton;
    private JCheckBox showMinMaxBox;
    private JCheckBox useGaussianNoiseBox;

    private JPanel filterControlPanel;

    private ZoomableChartView voltageChart;

    private ITrace2D baseVoltageTrace;
    private ITrace2D voltageTrace;
    private ITrace2D filteredVoltageTrace;
    private ITrace2D filteredVoltageMinTrace;
    private ITrace2D filteredVoltageMaxTrace;

    // data
    private double currentVoltage = MIN_VOLTAGE;
    private double currentJitterMagnitude = MIN_JITTER;

    private boolean signalPaused = false;
    private boolean useGaussianNoise = false;
    private boolean showMinMax = false;

    private AtomicReference<SignalFilter> currentFilter;
    private SampleAndThresholdFilter thresholdFilter;

    private final DescriptiveStatistics signalStatistics;
    private final Random randomGenerator;

    // maps filter type -> filter and its associated parameter control box
    private final Map<FilterType, Pair<SignalFilter, JPanel>> signalFilterMap;

    public static void main(final String[] args) {
        FilterSandbox testApp = new FilterSandbox();
        testApp.runApp();
    }

    public FilterSandbox() {

        signalFilterMap = new HashMap<>();
        signalStatistics = new DescriptiveStatistics(NUM_STATS_DATA_POINTS);

        signalPaused = false;
        randomGenerator = new Random();
        randomGenerator.setSeed(System.nanoTime());
    }

    public void runApp() {

        initUI();
        connectControls();
        createProducer();

        mainWindow.setVisible(true);
        mainWindow.setBounds(100, 100, 1024, 768);
    }

    private void createProducer() {

        final Thread messager = new Thread(() -> {

            final long startTime = System.currentTimeMillis();
            while (true) {

                if (!signalPaused && currentFilter.get() != null) {

                    double timeValue = 0.001 * (System.currentTimeMillis() - startTime);

                    baseVoltageTrace.addPoint(timeValue, currentVoltage);

                    double voltageValue = addJitter(currentVoltage, currentJitterMagnitude);
                    voltageTrace.addPoint(timeValue, voltageValue);

                    double filteredVoltageValue = currentFilter.get().addValue(voltageValue);

                    // additionally apply the threshold filter
                    // filteredVoltageValue = thresholdFilter.addValue(filteredVoltageValue);

                    signalStatistics.addValue(filteredVoltageValue);
                    filteredVoltageTrace.addPoint(timeValue, filteredVoltageValue);

                    filteredVoltageMinTrace.addPoint(timeValue, signalStatistics.getMin());
                    filteredVoltageMaxTrace.addPoint(timeValue, signalStatistics.getMax());
                }

                try {
                    Thread.sleep(SAMPLE_PERIOD);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        messager.start();
    }

    private double addJitter(final double voltage, final double jitterMagnitude) {

        double result;
        if (useGaussianNoise) {
            result = voltage + jitterMagnitude * randomGenerator.nextGaussian();
        } else {
            result = voltage + jitterMagnitude * (2 * randomGenerator.nextDouble() - 1.0);
        }

        return result;
    }

    private void initUI() {

        try {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (Exception ex) {}

        mainWindow = new JFrame();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainWindow.setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JHeapMonitor heapMonitor = new JHeapMonitor();
        contentPane.add(heapMonitor, BorderLayout.SOUTH);

        createFilters();
        createControls(contentPane);
        createChart(contentPane);

        heapMonitor.setUpdateInterval(500);
        heapMonitor.setEnabled(true);
    }

    private void createFilters() {

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

        thresholdFilter = new SampleAndThresholdFilter();
        thresholdFilter.setThreshold(ADC_ACCURACY);
    }

    private void createControls(final JPanel contentPane) {

        JPanel controlPanel = new JPanel();
        controlPanel.setMinimumSize(new Dimension(250, 10));
        contentPane.add(controlPanel, BorderLayout.EAST);
        controlPanel.setLayout(new FormLayout(new ColumnSpec[] {
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("default:grow"),
            FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
            ColumnSpec.decode("300px:grow"),
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("default:grow"),
            FormSpecs.RELATED_GAP_COLSPEC,
        },
            new RowSpec[] {
                FormSpecs.LINE_GAP_ROWSPEC,
                RowSpec.decode("32px"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("default:grow"),
        }));

        voltageSlider = GuiUtils.makeSlider();
        controlPanel.add(voltageSlider, "4, 4");

        JLabel voltageLabel = new JLabel("Voltage");
        controlPanel.add(voltageLabel, "2, 4, left, default");

        voltageField = new JTextField();
        controlPanel.add(voltageField, "6, 4, fill, default");
        voltageField.setColumns(10);

        jitterSlider = GuiUtils.makeSlider();
        controlPanel.add(jitterSlider, "4, 6");

        JLabel jitterLabel = new JLabel("Jitter");
        controlPanel.add(jitterLabel, "2, 6, left, default");

        jitterField = new JTextField();
        controlPanel.add(jitterField, "6, 6, fill, default");
        jitterField.setColumns(10);

        JLabel filterLabel = new JLabel("Filter");
        controlPanel.add(filterLabel, "2, 10, left, default");

        filterSelectionBox = new JComboBox<>();
        filterSelectionBox.setModel(new DefaultComboBoxModel<>(FilterType.values()));
        filterSelectionBox.setEditable(true);
        controlPanel.add(filterSelectionBox, "4, 10, fill, default");

        showMinMaxBox = new JCheckBox("Show min/max traces");
        controlPanel.add(showMinMaxBox, "4, 14");

        useGaussianNoiseBox = new JCheckBox("Use gaussian noise");
        controlPanel.add(useGaussianNoiseBox, "4, 16");

        pauseButton = new JButton("Pause / Resume");
        controlPanel.add(pauseButton, "4, 20");

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.LIGHT_GRAY);
        controlPanel.add(separator, "2, 25, 5, 1");

        JLabel filterParamLabel = new JLabel("Filter parameters");
        controlPanel.add(filterParamLabel, "4, 27, center, default");

        filterControlPanel = new JPanel();
        controlPanel.add(filterControlPanel, "2, 29, 5, 1, fill, fill");

        filterControlPanel.add(signalFilterMap.get(FilterType.SIMPLE_EXPONENTIAL).getRight(), BorderLayout.CENTER);
    }

    private void createChart(final JPanel contentPane) {

        voltageChart = new ZoomableChartView();

        voltageChart.setBackground(DarculaUiColors.PRIMARY1);
        voltageChart.setForeground(DarculaUiColors.MEDIUM_GRAY);
        voltageChart.setGridColor(DarculaUiColors.DARKEST_GRAY);

        voltageChart.setXaxisTitle("TIME, s", Color.YELLOW);
        voltageChart.setYaxisTitle("VOLTAGE, V", Color.YELLOW);

        baseVoltageTrace = voltageChart.addLtdTrace("Base voltage", Color.WHITE, NUM_CHART_DATA_POINTS);
        voltageTrace = voltageChart.addLtdTrace("Input signal", Color.CYAN, NUM_CHART_DATA_POINTS);
        filteredVoltageTrace = voltageChart.addLtdTrace("Filtered signal", Color.YELLOW, NUM_CHART_DATA_POINTS);
        filteredVoltageMinTrace = voltageChart.addLtdTrace("Filtered min", Color.GREEN, NUM_CHART_DATA_POINTS);
        filteredVoltageMaxTrace = voltageChart.addLtdTrace("Filtered max", Color.GREEN, NUM_CHART_DATA_POINTS);

        contentPane.add(voltageChart, BorderLayout.CENTER);
    }

    private void connectControls() {

        pauseButton.addActionListener(event -> {
            signalPaused = !signalPaused;
            log.info(signalPaused ? "Paused" : "Running");
        });

        useGaussianNoiseBox.addActionListener(event -> {
            useGaussianNoise = useGaussianNoiseBox.isSelected();
        });

        showMinMaxBox.addActionListener(event -> {
            showMinMax = showMinMaxBox.isSelected();
            filteredVoltageMinTrace.setVisible(showMinMax);
            filteredVoltageMaxTrace.setVisible(showMinMax);
        });

        // filter type switch
        filterSelectionBox.addActionListener(event -> {

            FilterType filterType = (FilterType) filterSelectionBox.getSelectedItem();
            if (signalFilterMap.containsKey(filterType)) {

                final JPanel controlBox = signalFilterMap.get(filterType).getRight();

                // show associated filter control box
                filterControlPanel.removeAll();
                filterControlPanel.add(controlBox, BorderLayout.CENTER);

                currentFilter.set(signalFilterMap.get(filterType).getLeft());
            }
        });

        voltageSlider.setValue(GuiUtils.paramToSliderPosition(MIN_VOLTAGE, MIN_VOLTAGE, MAX_VOLTAGE));
        jitterSlider.setValue(GuiUtils.paramToSliderPosition(MIN_JITTER, MIN_JITTER, MAX_JITTER));

        voltageField.setText(GuiUtils.formatDouble(MIN_VOLTAGE));
        jitterField.setText(GuiUtils.formatDouble(MIN_JITTER));

        voltageSlider.addChangeListener(event -> {
            double value = GuiUtils.interpolateFilterParam(voltageSlider.getValue(), MIN_VOLTAGE, MAX_VOLTAGE);
            voltageField.setText(GuiUtils.formatDouble(value));
            currentVoltage = value;
        });

        jitterSlider.addChangeListener(event -> {
            double value = GuiUtils.interpolateFilterParam(jitterSlider.getValue(), MIN_JITTER, MAX_JITTER);
            jitterField.setText(GuiUtils.formatDouble(value));
            currentJitterMagnitude = value;
        });

        voltageField.addActionListener(event -> {
            double value = Double.parseDouble(voltageField.getText());
            voltageSlider.setValue(GuiUtils.paramToSliderPosition(value, MIN_VOLTAGE, MAX_VOLTAGE));
        });

        jitterField.addActionListener(event -> {
            double value = Double.parseDouble(jitterField.getText());
            jitterSlider.setValue(GuiUtils.paramToSliderPosition(value, MIN_JITTER, MAX_JITTER));
        });

    }
}
