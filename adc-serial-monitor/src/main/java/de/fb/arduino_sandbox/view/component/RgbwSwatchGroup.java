package de.fb.arduino_sandbox.view.component;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

class RgbwSwatchGroup {

    private static final int SWATCH_SIZE = 46;
    private static final Font FONT = new Font("Dialog", Font.PLAIN, 9);

    private final JColorSwatch colorSwatch;
    private final JColorSwatch whiteSwatch;
    private final JSpinner groupSizeSpinner;

    private RgbwSwatchGroup(final JColorSwatch colorSwatch,
        final JColorSwatch whiteSwatch,
        final JSpinner groupSizeSpinner) {
        super();
        this.colorSwatch = colorSwatch;
        this.whiteSwatch = whiteSwatch;
        this.groupSizeSpinner = groupSizeSpinner;
    }

    public static RgbwSwatchGroup create() {

        final JColorSwatch colorSwatch = new JColorSwatch.Builder().swatchSize(SWATCH_SIZE).color(Color.BLACK).build();
        final JColorSwatch lumiSwatch = new JColorSwatch.Builder().swatchSize(SWATCH_SIZE).color(Color.BLACK).build();

        final JSpinner groupSizeSpinner = new JSpinner();
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 999, 1);
        groupSizeSpinner.setModel(model);
        groupSizeSpinner.setFont(FONT);

        return new RgbwSwatchGroup(colorSwatch, lumiSwatch, groupSizeSpinner);
    }

    public JColorSwatch getColorSwatch() {
        return colorSwatch;
    }

    public JColorSwatch getWhiteSwatch() {
        return whiteSwatch;
    }

    public JSpinner getGroupSizeSpinner() {
        return groupSizeSpinner;
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
}