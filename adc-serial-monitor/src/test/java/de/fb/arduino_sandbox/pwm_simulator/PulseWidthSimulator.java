package de.fb.arduino_sandbox.pwm_simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import com.bulenkov.darcula.DarculaLaf;
import de.fb.arduino_sandbox.util.GuiUtils;
import de.fb.arduino_sandbox.view.component.ZoomableChartView;
import info.monitorenter.gui.chart.ITrace2D;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JSlider;
import com.jgoodies.forms.layout.Sizes;

public class PulseWidthSimulator {

    private static final int SAMPLING_RATE = 1000; // samples / sec

    private static final int MIN_FREQUENCY = 50;
    private static final int MAX_FREQUENCY = 200;
    private static final int MIN_DUTY_CYCLE = 20;
    private static final int MAX_DUTY_CYCLE = 98;

    private static final float MIN_VOLTAGE = 12.0f;
    private static final float MAX_VOLTAGE = 14.9f;

    private static final float MIN_OFFSET = 0.0f;
    private static final float MAX_OFFSET = 10.0f;

    // app instance
    private static PulseWidthSimulator app;

    private JFrame mainWindow;
    private ZoomableChartView chartView;

    private JSlider frequencySlider;
    private JSlider dutyCycleSlider;
    private JSlider inputVoltageSlider;
    private JSlider inputWaveformOffsetSlider;
    private JSlider driftedWaveformOffsetSlider;
    private JSlider correctedWaveformOffsetSlider;

    private JLabel frequencyLabel;
    private JLabel dutyCycleLabel;
    private JLabel inputVoltageLabel;
    private JLabel inputOffsetLabel;
    private JLabel driftedOffsetLabel;
    private JLabel correctedOffsetLabel;

    // state
    private int inputFrequency;
    private int inputDutyCycle;
    private double inputVoltage;

    private double inputWaveformOffset;
    private double driftedWaveformOffset;
    private double correctedWaveformOffset;

    private ITrace2D inputWaveformTrace;
    private ITrace2D driftedWaveformTrace;
    private ITrace2D correctedWaveformTrace;

    public static void main(final String[] args) {

        try {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (Exception ex) {}

        app = new PulseWidthSimulator();
        app.start();
    }

    public PulseWidthSimulator() {
        createUI();
        connectEvents();
    }

    public void start() {

        final Thread worker = createWorker();
        worker.start();

        // show UI
        mainWindow.setBounds(100, 100, 1024, 768);
        mainWindow.setVisible(true);
    }

    private Thread createWorker() {

        final long sleepTime;

        final Thread worker = new Thread(() -> {
            while (true) {

                // instead of Thread.sleep() we use a higher resolution method using System.nanoTime()
                // busyWaitMicros(sleepTime);
            }
        });
        return worker;
    }

    private void busyWaitMicros(final long micros) {
        final long waitUntil = System.nanoTime() + (micros * 1000);
        while (waitUntil > System.nanoTime()) {
            ;
        }
    }

    // public static void createSampleGenerator(final ITrace2D trace, final ITrace2D trace2) {
    //
    // final Thread generator = new Thread(() -> {
    //
    // final long startTime = System.currentTimeMillis();
    // while (true) {
    //
    // double timeValue = 0.001 * (System.currentTimeMillis() - startTime);
    // double carrier = Math.sin(2 * Math.PI * CARRIER_FREQUENCY * timeValue);
    // double envelope = Math.sin(2 * Math.PI * MODULATION_FREQUENCY * timeValue);
    //
    // trace.addPoint(timeValue, carrier);
    // trace2.addPoint(timeValue, envelope);
    //
    // try {
    // Thread.sleep(SAMPLE_PERIOD);
    // } catch (InterruptedException ex) {
    // Thread.currentThread().interrupt();
    // }
    // }
    // });
    // generator.start();
    // }

    private void processData() {

    }

    private void createUI() {

        mainWindow = new JFrame();
        mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainWindow.setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        createCharts(contentPane);

        final JPanel controlPanel = createControls();
        contentPane.add(controlPanel, BorderLayout.WEST);
    }

    private void createCharts(JPanel contentPane) {
        chartView = new ZoomableChartView();

        chartView.setXaxisEenabled(true);
        chartView.setYaxisEnabled(true);

        chartView.setXaxisTitle("time", Color.YELLOW);
        chartView.setYaxisTitle("voltage", Color.YELLOW);

        inputWaveformTrace = chartView.addLtdTrace("input", Color.CYAN, 400);
        driftedWaveformTrace = chartView.addLtdTrace("drifted", Color.YELLOW, 400);
        correctedWaveformTrace = chartView.addLtdTrace("corrected", Color.GREEN, 400);

        contentPane.add(chartView, BorderLayout.CENTER);
    }

    private JPanel createControls() {

        final JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(250, 100));

        controlPanel.setLayout(new FormLayout(new ColumnSpec[] {
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("pref:grow"),
            FormSpecs.RELATED_GAP_COLSPEC,
            new ColumnSpec(ColumnSpec.FILL,
                Sizes.bounded(Sizes.PREFERRED, Sizes.constant("10dlu", true), Sizes.constant("30dlu", true)), 0),
            FormSpecs.RELATED_GAP_COLSPEC,
        },
            new RowSpec[] {
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
            }));

        final JLabel lblNewLabel = new JLabel("Frequency, Hz");
        controlPanel.add(lblNewLabel, "2, 2, left, default");

        frequencySlider = new JSlider();
        frequencySlider.setMinimum(MIN_FREQUENCY);
        frequencySlider.setMaximum(MAX_FREQUENCY);
        controlPanel.add(frequencySlider, "2, 4, fill, default");

        frequencyLabel = new JLabel(String.valueOf(MIN_FREQUENCY));
        controlPanel.add(frequencyLabel, "4, 4, left, default");

        final JLabel lblNewLabel_1 = new JLabel("Duty cycle, %");
        controlPanel.add(lblNewLabel_1, "2, 8");

        dutyCycleSlider = new JSlider();
        dutyCycleSlider.setMinimum(MIN_DUTY_CYCLE);
        dutyCycleSlider.setMaximum(MAX_DUTY_CYCLE);
        dutyCycleSlider.setValue(MIN_DUTY_CYCLE);
        controlPanel.add(dutyCycleSlider, "2, 10");

        dutyCycleLabel = new JLabel(String.valueOf(MIN_DUTY_CYCLE));
        controlPanel.add(dutyCycleLabel, "4, 10, left, default");

        final JLabel lblNewLabel_2 = new JLabel("Input voltage, V");
        controlPanel.add(lblNewLabel_2, "2, 14");

        inputVoltageSlider = GuiUtils.makeSlider();
        controlPanel.add(inputVoltageSlider, "2, 16");

        inputVoltageLabel = new JLabel(String.valueOf(MIN_VOLTAGE));
        controlPanel.add(inputVoltageLabel, "4, 16, left, default");

        JLabel lblNewLabel_3 = new JLabel("Input waveform offset");
        controlPanel.add(lblNewLabel_3, "2, 20");

        inputWaveformOffsetSlider = GuiUtils.makeSlider();
        controlPanel.add(inputWaveformOffsetSlider, "2, 22");

        inputOffsetLabel = new JLabel("0.0");
        controlPanel.add(inputOffsetLabel, "4, 22");

        JLabel lblNewLabel_4 = new JLabel("Drifting waveform offset");
        controlPanel.add(lblNewLabel_4, "2, 26");

        driftedWaveformOffsetSlider = GuiUtils.makeSlider();
        controlPanel.add(driftedWaveformOffsetSlider, "2, 28");

        driftedOffsetLabel = new JLabel("0.0");
        controlPanel.add(driftedOffsetLabel, "4, 28");

        JLabel lblNewLabel_5 = new JLabel("Corrected waveform offset");
        controlPanel.add(lblNewLabel_5, "2, 32");

        correctedWaveformOffsetSlider = GuiUtils.makeSlider();
        controlPanel.add(correctedWaveformOffsetSlider, "2, 34");

        correctedOffsetLabel = new JLabel("0.0");
        controlPanel.add(correctedOffsetLabel, "4, 34");
        return controlPanel;
    }

    private void connectEvents() {

        frequencySlider.addChangeListener(event -> {
            inputFrequency = frequencySlider.getValue();
            frequencyLabel.setText(String.valueOf(inputFrequency));
        });

        dutyCycleSlider.addChangeListener(event -> {
            inputDutyCycle = dutyCycleSlider.getValue();
            dutyCycleLabel.setText(String.valueOf(inputDutyCycle));
        });

        inputVoltageSlider.addChangeListener(event -> {
            inputVoltage = GuiUtils.interpolateParam(inputVoltageSlider.getValue(), MIN_VOLTAGE, MAX_VOLTAGE);
            inputVoltageLabel.setText(MessageFormat.format("{0,number,#.##}", inputVoltage));
        });

        inputWaveformOffsetSlider.addChangeListener(event -> {
            inputWaveformOffset = GuiUtils.interpolateParam(inputWaveformOffsetSlider.getValue(), MIN_OFFSET, MAX_OFFSET);
            inputOffsetLabel.setText(MessageFormat.format("{0,number,#.##}", inputWaveformOffset));
        });

        driftedWaveformOffsetSlider.addChangeListener(event -> {
            driftedWaveformOffset = GuiUtils.interpolateParam(driftedWaveformOffsetSlider.getValue(), MIN_OFFSET, MAX_OFFSET);
            driftedOffsetLabel.setText(MessageFormat.format("{0,number,#.##}", driftedWaveformOffset));
        });

        correctedWaveformOffsetSlider.addChangeListener(event -> {
            correctedWaveformOffset = GuiUtils.interpolateParam(correctedWaveformOffsetSlider.getValue(), MIN_OFFSET, MAX_OFFSET);
            correctedOffsetLabel.setText(MessageFormat.format("{0,number,#.##}", correctedWaveformOffset));
        });
    }
}
