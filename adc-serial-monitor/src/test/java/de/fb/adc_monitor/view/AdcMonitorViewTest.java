package de.fb.adc_monitor.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bulenkov.darcula.DarculaLaf;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import info.monitorenter.gui.chart.IAxis;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.ZoomableChart;
import info.monitorenter.gui.chart.axistitlepainters.AxisTitlePainterDefault;
import info.monitorenter.gui.chart.traces.Trace2DLtd;

public class AdcMonitorViewTest {

    private static final Logger log = LoggerFactory.getLogger(AdcMonitorViewTest.class);

    // sample generator period
    private static final int SAMPLE_PERIOD = 50;

    // number of points in the input signal dataset, used by the chart traces and stats accumulator
    private static final int NUM_DATA_POINTS = 200;

    private static final int SLIDER_TICKS = 1000;

    // kalman filter parameters
    private static final double INITIAL_Q = 0.25;
    private static final double MIN_Q = 0.1;
    private static final double MAX_Q = 1.0;

    private static final double INITIAL_R = 100.0;
    private static final double MIN_R = 10.0;
    private static final double MAX_R = 5000.0;

    private static final double INITIAL_P = 100.0;
    private static final double MIN_P = 10.0;
    private static final double MAX_P = 1000.0;

    // initial conditions
    private static final double MIN_VOLTAGE = 1.0;
    private static final double MAX_VOLTAGE = 5.0;

    private static final double MIN_JITTER = 0.001;
    private static final double MAX_JITTER = 0.500;

    private JFrame mainWindow;

    // controls
    private JTextField qField;
    private JTextField rField;
    private JTextField pField;
    private JTextField voltageField;
    private JTextField jitterField;

    private JSlider qSlider;
    private JSlider rSlider;
    private JSlider pSlider;
    private JSlider voltageSlider;
    private JSlider jitterSlider;

    private JButton resetZoomButton;
    private JButton pauseButton;
    private JCheckBox showStdDeviationBox;
    private JCheckBox useGaussianNoiseBox;

    private ZoomableChart voltageChart;
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

    private final KalmanFilter kalmanFilter;
    private final DescriptiveStatistics signalStatistics;
    private final Random randomGenerator;

    public static void main(final String[] args) {
        AdcMonitorViewTest testApp = new AdcMonitorViewTest();
        testApp.runApp();
    }

    public AdcMonitorViewTest() {

        signalStatistics = new DescriptiveStatistics(NUM_DATA_POINTS);
        kalmanFilter = new KalmanFilter(INITIAL_Q, INITIAL_R, INITIAL_P, MIN_VOLTAGE);

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

                if (!signalPaused) {

                    double timeValue = 0.001 * (System.currentTimeMillis() - startTime);

                    baseVoltageTrace.addPoint(timeValue, currentVoltage);

                    double voltageValue = addJitter(currentVoltage, currentJitterMagnitude);
                    voltageTrace.addPoint(timeValue, voltageValue);

                    double filteredVoltageValue = kalmanFilter.addSample(voltageValue);

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
            result = voltage + currentJitterMagnitude * randomGenerator.nextGaussian();
        } else {
            result = voltage + currentJitterMagnitude * (2 * randomGenerator.nextDouble() - 1.0);
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

        createControls(contentPane);
        createChart(contentPane);

        heapMonitor.setUpdateInterval(500);
        heapMonitor.setEnabled(true);
    }

    private JSlider makeSlider() {
        final JSlider slider = new JSlider();
        slider.setValue(0);
        slider.setMinimum(0);
        slider.setMaximum(SLIDER_TICKS);
        return slider;
    }

    private void createControls(final JPanel contentPane) {

        JPanel controlPanel = new JPanel();
        controlPanel.setMinimumSize(new Dimension(250, 10));
        contentPane.add(controlPanel, BorderLayout.EAST);
        controlPanel.setLayout(new FormLayout(new ColumnSpec[] {
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
            ColumnSpec.decode("300px:grow"),
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("default:grow"),
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
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
                RowSpec.decode("default:grow"),
        }));

        final JLabel qLabel = new JLabel("Filter Q");
        controlPanel.add(qLabel, "2, 2");

        qSlider = makeSlider();
        controlPanel.add(qSlider, "4, 2");

        qField = new JTextField();
        controlPanel.add(qField, "6, 2, fill, default");
        qField.setColumns(10);

        final JLabel rLabel = new JLabel("Filter R");
        controlPanel.add(rLabel, "2, 4");

        rSlider = makeSlider();
        controlPanel.add(rSlider, "4, 4");

        rField = new JTextField();
        controlPanel.add(rField, "6, 4, fill, default");
        rField.setColumns(10);

        final JLabel pLabel = new JLabel("Filter P");
        controlPanel.add(pLabel, "2, 6");

        pSlider = makeSlider();
        controlPanel.add(pSlider, "4, 6");

        pField = new JTextField();
        controlPanel.add(pField, "6, 6, fill, default");
        pField.setColumns(10);

        JLabel voltageLabel = new JLabel("Voltage");
        controlPanel.add(voltageLabel, "2, 8");

        voltageSlider = makeSlider();
        controlPanel.add(voltageSlider, "4, 8");

        voltageField = new JTextField();
        controlPanel.add(voltageField, "6, 8, fill, default");
        voltageField.setColumns(10);

        JLabel jitterLabel = new JLabel("Jitter");
        controlPanel.add(jitterLabel, "2, 10");

        jitterSlider = makeSlider();
        controlPanel.add(jitterSlider, "4, 10");

        jitterField = new JTextField();
        controlPanel.add(jitterField, "6, 10, fill, default");
        jitterField.setColumns(10);

        showStdDeviationBox = new JCheckBox("Show min/max traces");
        controlPanel.add(showStdDeviationBox, "4, 12");

        useGaussianNoiseBox = new JCheckBox("Use gaussian noise");
        controlPanel.add(useGaussianNoiseBox, "4, 14");

        resetZoomButton = new JButton("Reset zoom");
        controlPanel.add(resetZoomButton, "4, 16");

        pauseButton = new JButton("Pause / Resume");
        controlPanel.add(pauseButton, "4, 18");
    }

    private void createChart(final JPanel contentPane) {

        voltageChart = new ZoomableChart();

        voltageChart.setBackground(DarculaUiColors.PRIMARY1);
        voltageChart.setForeground(DarculaUiColors.MEDIUM_GRAY);
        voltageChart.setGridColor(DarculaUiColors.DARKEST_GRAY);

        voltageChart.setUseAntialiasing(true);
        voltageChart.setPaintLabels(true);

        IAxis<?> xAxis = voltageChart.getAxisX();
        xAxis.getAxisTitle().setTitle("TIME, s");
        xAxis.getAxisTitle().setTitlePainter(new AxisTitlePainterDefault());
        xAxis.getAxisTitle().setTitleColor(Color.YELLOW);

        // voltageChart.getAxisX().setPaintScale(true);
        // voltageChart.getAxisX().setPaintGrid(true);

        IAxis<?> yAxis = voltageChart.getAxisY();
        yAxis.getAxisTitle().setTitle("VOLTAGE, V");
        yAxis.getAxisTitle().setTitlePainter(new AxisTitlePainterDefault());
        yAxis.getAxisTitle().setTitleColor(Color.YELLOW);
        yAxis.setPaintScale(true);
        yAxis.setPaintGrid(true);

        baseVoltageTrace = new Trace2DLtd(NUM_DATA_POINTS);
        baseVoltageTrace.setColor(Color.WHITE);
        baseVoltageTrace.setName("Base voltage");

        voltageChart.addTrace(baseVoltageTrace);

        voltageTrace = new Trace2DLtd(NUM_DATA_POINTS);

        voltageTrace.setColor(Color.CYAN);
        voltageTrace.setName("Input signal");
        // voltageTrace.setPhysicalUnits("time", "voltage");

        voltageChart.addTrace(voltageTrace);

        filteredVoltageTrace = new Trace2DLtd(NUM_DATA_POINTS);

        filteredVoltageTrace.setColor(Color.YELLOW);
        filteredVoltageTrace.setName("Filtered signal");
        // filteredVoltageTrace.setPhysicalUnits("time", "voltage");

        voltageChart.addTrace(filteredVoltageTrace);

        filteredVoltageMinTrace = new Trace2DLtd(NUM_DATA_POINTS);
        filteredVoltageMinTrace.setColor(Color.GREEN);
        filteredVoltageMinTrace.setName("filtered min");

        voltageChart.addTrace(filteredVoltageMinTrace);
        // filteredVoltageMinTrace.setPointHighlighter(new PointPainterDisc(2));

        filteredVoltageMaxTrace = new Trace2DLtd(NUM_DATA_POINTS);
        filteredVoltageMaxTrace.setColor(Color.GREEN);
        filteredVoltageMaxTrace.setName("filtered max");

        voltageChart.addTrace(filteredVoltageMaxTrace);

        contentPane.add(voltageChart, BorderLayout.CENTER);
    }

    private void connectControls() {

        resetZoomButton.addActionListener(event -> {
            voltageChart.zoomAll();
        });

        pauseButton.addActionListener(event -> {
            signalPaused = !signalPaused;
            log.info(signalPaused ? "Paused" : "Running");
        });

        useGaussianNoiseBox.addActionListener(event -> {
            useGaussianNoise = useGaussianNoiseBox.isSelected();
        });

        showStdDeviationBox.addActionListener(event -> {
            showMinMax = showStdDeviationBox.isSelected();
        });

        // sliders
        qSlider.setValue(paramToSliderPosition(INITIAL_Q, MIN_Q, MAX_Q));
        rSlider.setValue(paramToSliderPosition(INITIAL_R, MIN_R, MAX_R));
        pSlider.setValue(paramToSliderPosition(INITIAL_P, MIN_P, MAX_P));
        voltageSlider.setValue(paramToSliderPosition(MIN_VOLTAGE, MIN_VOLTAGE, MAX_VOLTAGE));
        jitterSlider.setValue(paramToSliderPosition(MIN_JITTER, MIN_JITTER, MAX_JITTER));

        qField.setText(formatDouble(INITIAL_Q));
        rField.setText(formatDouble(INITIAL_R));
        pField.setText(formatDouble(INITIAL_P));
        voltageField.setText(formatDouble(MIN_VOLTAGE));
        jitterField.setText(formatDouble(MIN_JITTER));

        qSlider.addChangeListener(event -> {
            double value = interpolateFilterParam(qSlider.getValue(), MIN_Q, MAX_Q);
            qField.setText(formatDouble(value));
            kalmanFilter.setQ(value);
        });

        rSlider.addChangeListener(event -> {
            double value = interpolateFilterParam(rSlider.getValue(), MIN_R, MAX_R);
            rField.setText(formatDouble(value));
            kalmanFilter.setR(value);
        });

        pSlider.addChangeListener(event -> {
            double value = interpolateFilterParam(pSlider.getValue(), MIN_P, MAX_P);
            pField.setText(formatDouble(value));
            kalmanFilter.setP(value);
        });

        voltageSlider.addChangeListener(event -> {
            double value = interpolateFilterParam(voltageSlider.getValue(), MIN_VOLTAGE, MAX_VOLTAGE);
            voltageField.setText(formatDouble(value));
            currentVoltage = value;
        });

        jitterSlider.addChangeListener(event -> {
            double value = interpolateFilterParam(jitterSlider.getValue(), MIN_JITTER, MAX_JITTER);
            jitterField.setText(formatDouble(value));
            currentJitterMagnitude = value;
        });

        // fields
        qField.addActionListener(event -> {
            double value = Double.parseDouble(qField.getText());
            qSlider.setValue(paramToSliderPosition(value, MIN_Q, MAX_Q));
            kalmanFilter.setQ(value);
        });

        rField.addActionListener(event -> {
            double value = Double.parseDouble(rField.getText());
            rSlider.setValue(paramToSliderPosition(value, MIN_R, MAX_R));
            kalmanFilter.setR(value);
        });

        pField.addActionListener(event -> {
            double value = Double.parseDouble(pField.getText());
            pSlider.setValue(paramToSliderPosition(value, MIN_P, MAX_P));
            kalmanFilter.setP(value);
        });

        voltageField.addActionListener(event -> {
            double value = Double.parseDouble(voltageField.getText());
            voltageSlider.setValue(paramToSliderPosition(value, MIN_VOLTAGE, MAX_VOLTAGE));
        });

        jitterField.addActionListener(event -> {
            double value = Double.parseDouble(jitterField.getText());
            jitterSlider.setValue(paramToSliderPosition(value, MIN_JITTER, MAX_JITTER));
        });

    }

    private String formatDouble(final double value) {
        return String.format("%.3f", value);
    }

    // sliderValue is always assumed to be in range [0...SLIDER_TICKS]
    private double interpolateFilterParam(final int sliderValue, final double min, final double max) {
        double normSliderValue = 1.0 * sliderValue / SLIDER_TICKS;
        return min + normSliderValue * (max - min);
    }

    private int paramToSliderPosition(final double value, final double min, final double max) {

        final double normValue = (value - min) / (max - min);
        final int position = (int) Math.round(SLIDER_TICKS * normValue);
        return position;
    }
}
