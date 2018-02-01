package de.fb.adc_monitor.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bulenkov.darcula.DarculaLaf;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;

public class AdcMonitorViewTest {

    private static final Logger log = LoggerFactory.getLogger(AdcMonitorViewTest.class);

    // kalman filter parameters
    private static final double INITIAL_Q = 0.25;
    private static final double MIN_Q = 0.1;
    private static final double MAX_Q = 10.0;

    private static final double INITIAL_R = 100.0;
    private static final double MIN_R = 10.0;
    private static final double MAX_R = 5000.0;

    private static final double INITIAL_P = 100.0;
    private static final double MIN_P = 10.0;
    private static final double MAX_P = 1000.0;

    // initial conditions
    private static final double MIN_VOLTAGE = 1.0;
    private static final double MAX_VOLTAGE = 5.0;
    private static final double VOLTAGE_JITTER = 0.015;

    private JFrame mainWindow;

    // controls
    private JTextField qField;
    private JTextField rField;
    private JTextField pField;
    private JTextField voltageField;

    private JSlider qSlider;
    private JSlider rSlider;
    private JSlider pSlider;
    private JSlider voltageSlider;

    private Chart2D chart;
    private ITrace2D voltageTrace;
    private ITrace2D filteredVoltageTrace;

    private double currentVoltage = MIN_VOLTAGE;
    private final KalmanFilter kalmanFilter;

    public static void main(final String[] args) {
        AdcMonitorViewTest testApp = new AdcMonitorViewTest();
        testApp.runApp();
    }

    public AdcMonitorViewTest() {
        kalmanFilter = new KalmanFilter(INITIAL_Q, INITIAL_R, INITIAL_P, MIN_VOLTAGE);
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
                double voltageValue = currentVoltage + VOLTAGE_JITTER * (2 * RandomUtils.nextDouble(0.0, 1.0) - 1.0);
                double timeValue = System.currentTimeMillis() - startTime;

                voltageTrace.addPoint(timeValue, voltageValue);
                filteredVoltageTrace.addPoint(timeValue, kalmanFilter.addSample(voltageValue));

                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        messager.start();
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

        ///////////////////////////////////////////////////////////////////

        JPanel controlPanel = new JPanel();
        controlPanel.setMinimumSize(new Dimension(250, 10));
        contentPane.add(controlPanel, BorderLayout.EAST);
        controlPanel.setLayout(new FormLayout(new ColumnSpec[] {
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
            ColumnSpec.decode("300px"),
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
        }));

        final JLabel qLabel = new JLabel("Filter Q");
        controlPanel.add(qLabel, "2, 2");

        qSlider = new JSlider();
        qSlider.setValue(1);
        qSlider.setMinimum(1);
        controlPanel.add(qSlider, "4, 2");

        qField = new JTextField();
        controlPanel.add(qField, "6, 2, fill, default");
        qField.setColumns(10);

        final JLabel rLabel = new JLabel("Filter R");
        controlPanel.add(rLabel, "2, 4");

        rSlider = new JSlider();
        rSlider.setValue(1);
        rSlider.setMinimum(1);
        controlPanel.add(rSlider, "4, 4");

        rField = new JTextField();
        controlPanel.add(rField, "6, 4, fill, default");
        rField.setColumns(10);

        final JLabel pLabel = new JLabel("Filter P");
        controlPanel.add(pLabel, "2, 6");

        pSlider = new JSlider();
        pSlider.setValue(1);
        controlPanel.add(pSlider, "4, 6");

        pField = new JTextField();
        controlPanel.add(pField, "6, 6, fill, default");
        pField.setColumns(10);

        JLabel voltageLabel = new JLabel("Voltage");
        controlPanel.add(voltageLabel, "2, 8");

        voltageSlider = new JSlider();
        controlPanel.add(voltageSlider, "4, 8");

        voltageField = new JTextField();
        controlPanel.add(voltageField, "6, 8, fill, default");
        voltageField.setColumns(10);

        ///////////////////////////////////////////////////////////////////

        chart = new Chart2D();

        chart.setPaintLabels(true);
        chart.setUseAntialiasing(true);
        chart.setBackground(DarculaUiColors.PRIMARY1);
        chart.setForeground(DarculaUiColors.MEDIUM_GRAY);

        contentPane.add(chart, BorderLayout.CENTER);

        ////////////////////////////////////////////////////////////

        voltageTrace = new Trace2DLtd(200);

        voltageTrace.setColor(Color.CYAN);
        voltageTrace.setName("Voltage");
        voltageTrace.setPhysicalUnits("time", "voltage");

        chart.addTrace(voltageTrace);

        filteredVoltageTrace = new Trace2DLtd(200);

        filteredVoltageTrace.setColor(Color.YELLOW);
        filteredVoltageTrace.setName("Voltage");
        filteredVoltageTrace.setPhysicalUnits("time", "voltage");

        chart.addTrace(filteredVoltageTrace);

        ////////////////////////////////////////////////////////////

        heapMonitor.setUpdateInterval(500);
        heapMonitor.setEnabled(true);
    }

    private void connectControls() {

        // sliders

        qSlider.setValue(paramToSliderPosition(INITIAL_Q, MIN_Q, MAX_Q));
        rSlider.setValue(paramToSliderPosition(INITIAL_R, MIN_R, MAX_R));
        pSlider.setValue(paramToSliderPosition(INITIAL_P, MIN_P, MAX_P));
        voltageSlider.setValue(paramToSliderPosition(MIN_VOLTAGE, MIN_VOLTAGE, MAX_VOLTAGE));

        qField.setText(String.valueOf(INITIAL_Q));
        rField.setText(String.valueOf(INITIAL_R));
        pField.setText(String.valueOf(INITIAL_P));
        voltageField.setText(String.valueOf(MIN_VOLTAGE));

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

    }

    private String formatDouble(final double value) {
        return String.format("%.3f", value);
    }

    // sliderValue is always assumed to be in range [0...100]
    private static double interpolateFilterParam(final int sliderValue, final double min, final double max) {
        double normSliderValue = sliderValue / 100.0;
        return min + normSliderValue * (max - min);
    }

    private static int paramToSliderPosition(final double value, final double min, final double max) {

        final double normValue = (value - min) / (max - min);
        final int position = (int) Math.round(100.0 * normValue);
        return position;
    }

}
