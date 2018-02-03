package de.fb.adc_monitor.sandbox;

import static de.fb.adc_monitor.sandbox.GuiUtils.*;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import de.fb.adc_monitor.math.KalmanFilter;

public class KalmanControlBox extends JPanel {

    // kalman filter parameters
    private static final double INITIAL_PROCESS_NOISE = 0.25;
    private static final double MIN_PROCESS_NOISE = 0.1;
    private static final double MAX_PROCESS_NOISE = 1.0;

    private static final double INITIAL_MEASUREMENT_NOISE = 100.0;
    private static final double MIN_MEASUREMENT_NOISE = 10.0;
    private static final double MAX_MEASUREMENT_NOISE = 5000.0;

    private static final double INITIAL_ERROR_FACTOR = 100.0;
    private static final double MIN_ERROR_FACTOR = 10.0;
    private static final double MAX_ERROR_FACTOR = 1000.0;

    // controls
    private JSlider processNoiseSlider;
    private JSlider measurementNoiseSlider;
    private JSlider errorFactorSlider;

    private JTextField processNoiseField;
    private JTextField measurementNoiseField;
    private JTextField errorFactorField;

    private final KalmanFilter filter;

    public KalmanControlBox(final KalmanFilter filter) {

        super();
        this.filter = filter;
        initUI();
        connectControls();
    }

    private void initUI() {

        setMinimumSize(new Dimension(250, 10));

        setLayout(new FormLayout(new ColumnSpec[] {
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
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
        }));

        final JLabel qLabel = new JLabel("Process noise");
        add(qLabel, "2, 2, left, default");

        processNoiseSlider = makeSlider();
        add(processNoiseSlider, "4, 2");

        processNoiseField = new JTextField();
        add(processNoiseField, "6, 2, fill, default");
        processNoiseField.setColumns(10);

        final JLabel rLabel = new JLabel("Measurement noise");
        add(rLabel, "2, 4, left, default");

        measurementNoiseSlider = makeSlider();
        add(measurementNoiseSlider, "4, 4");

        measurementNoiseField = new JTextField();
        add(measurementNoiseField, "6, 4, fill, default");
        measurementNoiseField.setColumns(10);

        final JLabel pLabel = new JLabel("Error factor");
        add(pLabel, "2, 6, left, default");

        errorFactorSlider = makeSlider();
        add(errorFactorSlider, "4, 6");

        errorFactorField = new JTextField();
        add(errorFactorField, "6, 6, fill, default");
        errorFactorField.setColumns(10);

    }

    private void connectControls() {

        // sliders
        processNoiseSlider.setValue(
            paramToSliderPosition(INITIAL_PROCESS_NOISE, MIN_PROCESS_NOISE, MAX_PROCESS_NOISE));

        measurementNoiseSlider.setValue(
            paramToSliderPosition(INITIAL_MEASUREMENT_NOISE, MIN_MEASUREMENT_NOISE, MAX_MEASUREMENT_NOISE));

        errorFactorSlider.setValue(
            paramToSliderPosition(INITIAL_ERROR_FACTOR, MIN_ERROR_FACTOR, MAX_ERROR_FACTOR));

        processNoiseField.setText(formatDouble(INITIAL_PROCESS_NOISE));
        measurementNoiseField.setText(formatDouble(INITIAL_MEASUREMENT_NOISE));
        errorFactorField.setText(formatDouble(INITIAL_ERROR_FACTOR));

        processNoiseSlider.addChangeListener(event -> {
            double value = interpolateFilterParam(processNoiseSlider.getValue(), MIN_PROCESS_NOISE, MAX_PROCESS_NOISE);
            processNoiseField.setText(formatDouble(value));
            filter.setProcessNoise(value);
        });

        measurementNoiseSlider.addChangeListener(event -> {
            double value = interpolateFilterParam(measurementNoiseSlider.getValue(), MIN_MEASUREMENT_NOISE,
                MAX_MEASUREMENT_NOISE);
            measurementNoiseField.setText(formatDouble(value));
            filter.setMeasurementNoise(value);
        });

        errorFactorSlider.addChangeListener(event -> {
            double value = interpolateFilterParam(errorFactorSlider.getValue(), MIN_ERROR_FACTOR, MAX_ERROR_FACTOR);
            errorFactorField.setText(formatDouble(value));
            filter.setErrorFactor(value);
        });

        // fields
        processNoiseField.addActionListener(event -> {
            double value = Double.parseDouble(processNoiseField.getText());
            processNoiseSlider.setValue(paramToSliderPosition(value, MIN_PROCESS_NOISE, MAX_PROCESS_NOISE));
            filter.setProcessNoise(value);
        });

        measurementNoiseField.addActionListener(event -> {
            double value = Double.parseDouble(measurementNoiseField.getText());
            measurementNoiseSlider.setValue(paramToSliderPosition(value, MIN_MEASUREMENT_NOISE, MAX_MEASUREMENT_NOISE));
            filter.setMeasurementNoise(value);
        });

        errorFactorField.addActionListener(event -> {
            double value = Double.parseDouble(errorFactorField.getText());
            errorFactorSlider.setValue(paramToSliderPosition(value, MIN_ERROR_FACTOR, MAX_ERROR_FACTOR));
            filter.setErrorFactor(value);
        });
    }
}
