package de.fb.arduino_sandbox.pwm_simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bulenkov.darcula.DarculaLaf;
import com.jgoodies.forms.layout.*;
import de.fb.arduino_sandbox.util.GuiUtils;
import de.fb.arduino_sandbox.view.component.ZoomableChartView;
import info.monitorenter.gui.chart.ITrace2D;

public class PulseWidthSimulator {

    private static final Logger log = LoggerFactory.getLogger(PulseWidthSimulator.class);

    private static final float INPUT_AMPLITUDE = 5.0f;

    private static final int MIN_FREQUENCY = 80;
    private static final int MAX_FREQUENCY = 200;

    private static final int MIN_DUTY_CYCLE = 21;
    private static final int MAX_DUTY_CYCLE = 98;

    private static final float MIN_VOLTAGE = 12.0f;
    private static final float MAX_VOLTAGE = 14.9f;

    private static final float MIN_OFFSET = 0.0f;
    private static final float MAX_OFFSET = 10.0f;

    private static final int MIN_TIME_BASE = 1000; // Hz (samples / sec)
    private static final int MAX_TIME_BASE = 10000;

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

    private JSlider timeBaseSlider;

    private JLabel frequencyLabel;
    private JLabel dutyCycleLabel;
    private JLabel inputVoltageLabel;
    private JLabel inputOffsetLabel;
    private JLabel driftedOffsetLabel;
    private JLabel correctedOffsetLabel;

    private JLabel timeBaseLabel;

    // state
    private int inputFrequency;
    private int inputDutyCycle;
    private float inputVoltage;

    private float inputWaveformOffset;
    private float driftedWaveformOffset;
    private float correctedWaveformOffset;

    private int samplingRate = MIN_TIME_BASE;

    private ITrace2D inputWaveformTrace;
    private ITrace2D driftedWaveformTrace;
    private ITrace2D correctedWaveformTrace;

    private long prevSampleTime = 0;
    private long currentSampleTime = 0;

    private PwmSource inputSignalSource;
    private JCheckBox generatorActiveCheckBox;

    public static void main(final String[] args) {

        try {
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (Exception ex) {}

        app = new PulseWidthSimulator();
        app.start();
    }

    public PulseWidthSimulator() {
        createData();
        createUI();
        connectEvents();
    }

    public void start() {

        final Thread uiUpdateWorker = createUiUpdateWorker();
        uiUpdateWorker.start();

        // show UI
        mainWindow.setBounds(100, 100, 1024, 768);
        mainWindow.setVisible(true);
    }

    private void createData() {

        inputSignalSource = new PwmSource();
        inputSignalSource.setClockFrequency(10000); // 10 Khz
        inputSignalSource.setAmplitude(INPUT_AMPLITUDE);
        inputSignalSource.setFrequency(MIN_FREQUENCY);
        inputSignalSource.setDutyCycle(MIN_DUTY_CYCLE);

        inputSignalSource.start();
        inputSignalSource.setActive(true);
    }

    private Thread createUiUpdateWorker() {

        final Thread worker = new Thread(() -> {
            while (true) {
             

                //currentSampleTime = prevSampleTime + sleepTime;
                //updateData(prevSampleTime, currentSampleTime);
                
                updateData(currentSampleTime);
                
                currentSampleTime++;
                
                // convert sampling rate from Hz to delay in microseconds
                final long sleepTime = Math.round((1.0 / samplingRate) * 1E6);
                busyWaitMicros(sleepTime);

                // update previous sample timestamp
                //prevSampleTime = currentSampleTime;
            }
        });
        return worker;
    }

    // instead of Thread.sleep() we use a higher resolution method using System.nanoTime()
    // taken from here: http://www.rationaljava.com/2015/10/measuring-microsecond-in-java.html
    private void busyWaitMicros(final long micros) {
        final long waitUntil = System.nanoTime() + (micros * 1000);
        while (waitUntil > System.nanoTime()) {
            ;
        }
    }

    private void updateData() {

        SwingUtilities.invokeLater(() -> {

            long timestamp = 0;
            final long step = Math.round((1.0 / samplingRate) * 1E6);

            // input waveform
            inputWaveformTrace.removeAllPoints();

            inputSignalSource.setActive(false);
            inputSignalSource.reset();

            inputSignalSource.setActive(true);

            for (int i = 0; i < samplingRate; i++) {

                float sample = inputSignalSource.getValue();

                sample += inputWaveformOffset; // add DC offset (if any)
                inputWaveformTrace.addPoint(timestamp, sample);

                // driftedWaveformTrace.addPoint(timestamp, inputSignalSource.getPhase());
                timestamp += step;

                // log.info("{}, {} -> {}", i, timestamp, sample);
            }

            inputSignalSource.setActive(false);

            // simulated drifted waveform
        });
    }

    private void updateData(final long timeStamp) {

        SwingUtilities.invokeLater(() -> {

            double inputWaveformSample = 0;
            double driftedWaveformSample = 0;
            double correctedWaveformSample = 0;

            // calculate samples
            inputWaveformSample = inputSignalSource.getValue();

            // offset waveforms using values from sliders
            inputWaveformSample += inputWaveformOffset;
            driftedWaveformSample += driftedWaveformOffset;
            correctedWaveformSample += correctedWaveformOffset;

            // update graphs
            //long timeStamp = currentSampleTime;
            // long timeStamp = currentSampleTime % sampleWindowWidth;
            //
            // if (timeStamp == 0) {
            // inputWaveformTrace.removeAllPoints();
            // driftedWaveformTrace.removeAllPoints();
            // correctedWaveformTrace.removeAllPoints();
            // }

            inputWaveformTrace.addPoint(timeStamp, inputWaveformSample);
            // driftedWaveformTrace.addPoint(timeStamp, inputSignalSource.getPhase());

            // driftedWaveformTrace.addPoint(timeStamp, driftedWaveformSample);
            // correctedWaveformTrace.addPoint(timeStamp, correctedWaveformSample);
        });
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

        inputWaveformTrace = chartView.addLtdTrace("input", Color.CYAN, 1000);
        driftedWaveformTrace = chartView.addLtdTrace("drifted", Color.YELLOW, 1000);
        correctedWaveformTrace = chartView.addLtdTrace("corrected", Color.GREEN, 1000);

        contentPane.add(chartView, BorderLayout.CENTER);
    }

    private JPanel createControls() {

        final JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(270, 100));

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

        JLabel lblNewLabel_3 = new JLabel("Input waveform DC offset");
        controlPanel.add(lblNewLabel_3, "2, 20");

        inputWaveformOffsetSlider = GuiUtils.makeSlider();
        controlPanel.add(inputWaveformOffsetSlider, "2, 22");

        inputOffsetLabel = new JLabel("0.0");
        controlPanel.add(inputOffsetLabel, "4, 22");

        JLabel lblNewLabel_4 = new JLabel("Drifting waveform DC offset");
        controlPanel.add(lblNewLabel_4, "2, 26");

        driftedWaveformOffsetSlider = GuiUtils.makeSlider();
        controlPanel.add(driftedWaveformOffsetSlider, "2, 28");

        driftedOffsetLabel = new JLabel("0.0");
        controlPanel.add(driftedOffsetLabel, "4, 28");

        JLabel lblNewLabel_5 = new JLabel("Corrected waveform DC offset");
        controlPanel.add(lblNewLabel_5, "2, 32");

        correctedWaveformOffsetSlider = GuiUtils.makeSlider();
        controlPanel.add(correctedWaveformOffsetSlider, "2, 34");

        correctedOffsetLabel = new JLabel("0.0");
        controlPanel.add(correctedOffsetLabel, "4, 34");

        JLabel lblNewLabel_6 = new JLabel("Time base, Hz");
        controlPanel.add(lblNewLabel_6, "2, 44");

        timeBaseSlider = new JSlider();
        timeBaseSlider.setMinimum(MIN_TIME_BASE);
        timeBaseSlider.setMaximum(MAX_TIME_BASE);
        timeBaseSlider.setValue(MIN_TIME_BASE);
        controlPanel.add(timeBaseSlider, "2, 46");

        timeBaseLabel = new JLabel(String.valueOf(MIN_TIME_BASE));
        controlPanel.add(timeBaseLabel, "4, 46");

        generatorActiveCheckBox = new JCheckBox("Generator clock active");
        generatorActiveCheckBox.setSelected(true);
        controlPanel.add(generatorActiveCheckBox, "2, 50");
        return controlPanel;
    }

    private void connectEvents() {

        frequencySlider.addChangeListener(event -> {
            inputFrequency = frequencySlider.getValue();
            frequencyLabel.setText(String.valueOf(inputFrequency));
            inputSignalSource.setFrequency(inputFrequency);
            updateData();
        });

        dutyCycleSlider.addChangeListener(event -> {
            inputDutyCycle = dutyCycleSlider.getValue();
            dutyCycleLabel.setText(String.valueOf(inputDutyCycle));
            inputSignalSource.setDutyCycle(inputDutyCycle);
            updateData();
        });

        inputVoltageSlider.addChangeListener(event -> {
            inputVoltage = (float) GuiUtils.interpolateParam(inputVoltageSlider.getValue(), MIN_VOLTAGE, MAX_VOLTAGE);
            inputVoltageLabel.setText(MessageFormat.format("{0,number,#.##}", inputVoltage));
            updateData();
        });

        inputWaveformOffsetSlider.addChangeListener(event -> {
            inputWaveformOffset = (float) GuiUtils.interpolateParam(inputWaveformOffsetSlider.getValue(), MIN_OFFSET, MAX_OFFSET);
            inputOffsetLabel.setText(MessageFormat.format("{0,number,#.##}", inputWaveformOffset));
            updateData();
        });

        driftedWaveformOffsetSlider.addChangeListener(event -> {
            driftedWaveformOffset = (float) GuiUtils.interpolateParam(driftedWaveformOffsetSlider.getValue(), MIN_OFFSET,
                MAX_OFFSET);
            driftedOffsetLabel.setText(MessageFormat.format("{0,number,#.##}", driftedWaveformOffset));
            updateData();
        });

        correctedWaveformOffsetSlider.addChangeListener(event -> {
            correctedWaveformOffset = (float) GuiUtils.interpolateParam(correctedWaveformOffsetSlider.getValue(), MIN_OFFSET,
                MAX_OFFSET);
            correctedOffsetLabel.setText(MessageFormat.format("{0,number,#.##}", correctedWaveformOffset));
            updateData();
        });

        timeBaseSlider.addChangeListener(event -> {
            samplingRate = timeBaseSlider.getValue();
            timeBaseLabel.setText(String.valueOf(samplingRate));
            updateData();
        });

        generatorActiveCheckBox.addChangeListener(event -> {
            inputSignalSource.setActive(generatorActiveCheckBox.isSelected());
        });
    }
}
