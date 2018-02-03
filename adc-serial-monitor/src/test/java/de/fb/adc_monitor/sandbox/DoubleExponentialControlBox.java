package de.fb.adc_monitor.sandbox;

import static de.fb.adc_monitor.sandbox.GuiUtils.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import de.fb.adc_monitor.math.DoubleExponentialFilter;

public class DoubleExponentialControlBox extends JPanel {

    private static final double MIN_SMOOTHING_FACTOR = 0.0;
    private static final double MAX_SMOOTHING_FACTOR = 1.0;

    private static final double MIN_TREND_SMOOTHING_FACTOR = 0.0;
    private static final double MAX_TREND_SMOOTHING_FACTOR = 1.0;

    private JSlider smoothingFactorSlider;
    private JSlider trendSmoothingFactorSlider;

    private JTextField smoothingFactorField;
    private JTextField trendSmoothingFactorField;

    private final DoubleExponentialFilter filter;

    public DoubleExponentialControlBox(final DoubleExponentialFilter filter) {
        super();
        this.filter = filter;
        initUI();
        connectControls();
    }

    private void initUI() {
        setLayout(new FormLayout(new ColumnSpec[] {
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("default:grow"),
        },
            new RowSpec[] {
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
        }));

        JLabel factorLabel = new JLabel("Smoothing factor (alpha)");
        add(factorLabel, "2, 2");

        smoothingFactorSlider = makeSlider();
        add(smoothingFactorSlider, "4, 2");

        smoothingFactorField = new JTextField();
        add(smoothingFactorField, "6, 2, fill, default");
        smoothingFactorField.setColumns(10);

        JLabel betaLabel = new JLabel("Trend smoothing factor (beta)");
        add(betaLabel, "2, 4");

        trendSmoothingFactorSlider = makeSlider();
        add(trendSmoothingFactorSlider, "4, 4");

        trendSmoothingFactorField = new JTextField();
        add(trendSmoothingFactorField, "6, 4, fill, default");
        trendSmoothingFactorField.setColumns(10);
    }

    private void connectControls() {

        // smoothing factor (alpha)
        smoothingFactorSlider.setValue(
            paramToSliderPosition(
                MIN_SMOOTHING_FACTOR,
                MIN_SMOOTHING_FACTOR,
                MAX_SMOOTHING_FACTOR));

        smoothingFactorField.setText(formatDouble(MIN_SMOOTHING_FACTOR));

        smoothingFactorSlider.addChangeListener(event -> {

            double value = interpolateFilterParam(smoothingFactorSlider.getValue(), MIN_SMOOTHING_FACTOR, MAX_SMOOTHING_FACTOR);
            smoothingFactorField.setText(formatDouble(value));
            filter.setSmoothingFactor(value);
        });

        smoothingFactorField.addActionListener(event -> {

            double value = Double.parseDouble(smoothingFactorField.getText());
            smoothingFactorSlider.setValue(paramToSliderPosition(value, MIN_SMOOTHING_FACTOR, MAX_SMOOTHING_FACTOR));
            filter.setSmoothingFactor(value);
        });

        // trend smoothing factor (beta)
        trendSmoothingFactorSlider.setValue(
            paramToSliderPosition(
                MIN_TREND_SMOOTHING_FACTOR,
                MIN_TREND_SMOOTHING_FACTOR,
                MAX_TREND_SMOOTHING_FACTOR));

        trendSmoothingFactorField.setText(formatDouble(MIN_TREND_SMOOTHING_FACTOR));

        trendSmoothingFactorSlider.addChangeListener(event -> {

            double value = interpolateFilterParam(
                trendSmoothingFactorSlider.getValue(), MIN_SMOOTHING_FACTOR, MAX_SMOOTHING_FACTOR);

            trendSmoothingFactorField.setText(formatDouble(value));
            filter.setTrendSmoothingFactor(value);
        });

        trendSmoothingFactorField.addActionListener(event -> {

            double value = Double.parseDouble(trendSmoothingFactorField.getText());
            trendSmoothingFactorSlider.setValue(paramToSliderPosition(value, MIN_SMOOTHING_FACTOR, MAX_SMOOTHING_FACTOR));
            filter.setTrendSmoothingFactor(value);
        });
    }
}
