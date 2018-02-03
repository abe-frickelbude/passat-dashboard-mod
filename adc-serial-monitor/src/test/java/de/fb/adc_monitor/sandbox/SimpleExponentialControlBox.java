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
import de.fb.adc_monitor.math.SimpleExponentialFilter;

public class SimpleExponentialControlBox extends JPanel {

    private static final double MIN_SMOOTHING_FACTOR = 0.0;
    private static final double MAX_SMOOTHING_FACTOR = 1.0;

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

            double value = interpolateFilterParam(smoothingFactorSlider.getValue(), MIN_SMOOTHING_FACTOR, MAX_SMOOTHING_FACTOR);
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
