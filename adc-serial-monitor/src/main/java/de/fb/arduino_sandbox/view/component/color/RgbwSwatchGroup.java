package de.fb.arduino_sandbox.view.component.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.function.Consumer;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import org.kordamp.ikonli.octicons.Octicons;
import org.kordamp.ikonli.swing.FontIcon;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

final class RgbwSwatchGroup extends JComponent {

    private static final Font FONT = new Font("Dialog", Font.PLAIN, 9);
    private static final int SWATCH_SIZE = 32;

    private final ColorSwatch colorSwatch;
    private final ColorSwatch whiteSwatch;
    private final JSpinner groupSizeSpinner;
    private final TinyButtonGroup plusMinusButtons;

    private RgbwSwatchGroup() {

        super();

        colorSwatch = new ColorSwatch();
        colorSwatch.setBorderEnabled(true);

        whiteSwatch = new ColorSwatch();
        whiteSwatch.setBorderEnabled(true);

        final Dimension swatchSize = new Dimension(SWATCH_SIZE, SWATCH_SIZE);
        colorSwatch.setPreferredSize(swatchSize);
        colorSwatch.setMinimumSize(swatchSize);
        colorSwatch.setMaximumSize(swatchSize);

        whiteSwatch.setPreferredSize(swatchSize);
        whiteSwatch.setMinimumSize(swatchSize);
        whiteSwatch.setMaximumSize(swatchSize);

        groupSizeSpinner = new JSpinner();
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 999, 1);
        groupSizeSpinner.setModel(model);
        groupSizeSpinner.setFont(FONT);

        plusMinusButtons = new TinyButtonGroup();
        plusMinusButtons.addButton(FontIcon.of(Octicons.DASH));
        plusMinusButtons.addButton(FontIcon.of(Octicons.PLUS));

        initLayout();
    }

    private void initLayout() {

        final FormLayout layout = new FormLayout(
            new ColumnSpec[] {
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("48px")
            },
            new RowSpec[] {
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("48px"), // color swatch
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("48px"), // white swatch
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC, // count spinner
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC, // remove button
                FormSpecs.RELATED_GAP_ROWSPEC
            });

        this.setLayout(layout);

        // note: stuff always added to EVEN columns and EVEN rows, as the ODD ones contain the gaps
        // row 2 - color swatch
        // row 4 - white swatch
        // row 6 - spinner
        // row 8 - remove button
        this.add(colorSwatch, "2,2,fill,fill");
        this.add(whiteSwatch, "2,4,fill,fill");
        this.add(groupSizeSpinner, "2,6,fill,fill");
        this.add(plusMinusButtons, "2,8,center,center");
    }

    public static RgbwSwatchGroup create() {
        return new RgbwSwatchGroup();
    }

    public TinyButtonGroup getPlusMinusButtons() {
        return plusMinusButtons;
    }

    public Color getColor() {
        return colorSwatch.getColor();
    }

    public Color getWhite() {
        return whiteSwatch.getColor();
    }

    public int getGroupSize() {
        return Integer.class.cast(groupSizeSpinner.getValue());
    }

    public void setMinusEnabled(final boolean enabled) {
        plusMinusButtons.getButton(0).setEnabled(enabled);
    }

    public void setPlusEnabled(final boolean enabled) {
        plusMinusButtons.getButton(1).setEnabled(enabled);
    }

    public void addChangeListener(final ChangeListener listener) {
        colorSwatch.addChangeListener(listener);
        whiteSwatch.addChangeListener(listener);
        groupSizeSpinner.addChangeListener(listener);
    }

    public void removeChangeListener(final ChangeListener listener) {
        colorSwatch.addChangeListener(listener);
        whiteSwatch.addChangeListener(listener);
        groupSizeSpinner.addChangeListener(listener);
    }

    public void registerRemoveCallback(final Consumer<RgbwSwatchGroup> callback) {
        plusMinusButtons.getButton(0).addActionCallback(() -> {
            callback.accept(this);
        });
    }

    public void registerAddCallback(final Consumer<RgbwSwatchGroup> callback) {
        plusMinusButtons.getButton(1).addActionCallback(() -> {
            callback.accept(this);
        });
    }

}