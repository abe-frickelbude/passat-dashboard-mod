package de.fb.arduino_sandbox.view.activity.adc.filter;

import static de.fb.arduino_sandbox.util.GuiUtils.*;
import javax.swing.*;
import com.jgoodies.forms.layout.*;
import de.fb.arduino_sandbox.math.SimpleExponentialFilter;

public class SimpleExponentialControlBox extends JPanel {

    public static final double MIN_SMOOTHING_FACTOR = 0.010;
    public static final double MAX_SMOOTHING_FACTOR = 0.25;

    private JSlider smoothingFactorSlider;
    private JTextField smoothingFactorField;

    private final SimpleExponentialFilter filter;

    public SimpleExponentialControlBox(final SimpleExponentialFilter filter) {

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
        }));

        JLabel factorLabel = new JLabel("Smoothing factor");
        add(factorLabel, "2, 2");

        smoothingFactorSlider = makeSlider();
        add(smoothingFactorSlider, "4, 2");

        smoothingFactorField = new JTextField();
        add(smoothingFactorField, "6, 2, fill, default");
        smoothingFactorField.setColumns(10);
    }

    private void connectControls() {

        smoothingFactorSlider.setValue(
            paramToSliderPosition(MIN_SMOOTHING_FACTOR, MIN_SMOOTHING_FACTOR, MAX_SMOOTHING_FACTOR));

        smoothingFactorField.setText(formatDouble(MIN_SMOOTHING_FACTOR));

        smoothingFactorSlider.addChangeListener(event -> {

            double value = interpolateParam(smoothingFactorSlider.getValue(), MIN_SMOOTHING_FACTOR, MAX_SMOOTHING_FACTOR);
            smoothingFactorField.setText(formatDouble(value));
            filter.setSmoothingFactor(value);
        });

        smoothingFactorField.addActionListener(event -> {

            double value = Double.parseDouble(smoothingFactorField.getText());
            smoothingFactorSlider.setValue(paramToSliderPosition(value, MIN_SMOOTHING_FACTOR, MAX_SMOOTHING_FACTOR));
            filter.setSmoothingFactor(value);
        });
    }

}
