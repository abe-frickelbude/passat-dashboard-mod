package de.fb.arduino_sandbox.view.component.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.function.Consumer;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import org.kordamp.ikonli.octicons.Octicons;
import org.kordamp.ikonli.swing.FontIcon;

final class RgbwSwatchGroup {

    private static final Font FONT = new Font("Dialog", Font.PLAIN, 9);
    private static final int SWATCH_SIZE = 32;

    private final ColorSwatch colorSwatch;
    private final ColorSwatch whiteSwatch;
    private final JSpinner groupSizeSpinner;

    private final TinyButtonGroup plusMinusButtons;

    private RgbwSwatchGroup(final ColorSwatch colorSwatch, final ColorSwatch whiteSwatch,
        final JSpinner groupSizeSpinner, final TinyButtonGroup plusMinusButtons) {
        super();
        this.colorSwatch = colorSwatch;
        this.whiteSwatch = whiteSwatch;
        this.groupSizeSpinner = groupSizeSpinner;
        this.plusMinusButtons = plusMinusButtons;
    }

    public static RgbwSwatchGroup create() {

        final ColorSwatch colorSwatch = new ColorSwatch();
        colorSwatch.setBorderEnabled(true);

        final ColorSwatch whiteSwatch = new ColorSwatch();
        whiteSwatch.setBorderEnabled(true);

        final Dimension swatchSize = new Dimension(SWATCH_SIZE, SWATCH_SIZE);
        colorSwatch.setPreferredSize(swatchSize);
        colorSwatch.setMinimumSize(swatchSize);
        colorSwatch.setMaximumSize(swatchSize);

        whiteSwatch.setPreferredSize(swatchSize);
        whiteSwatch.setMinimumSize(swatchSize);
        whiteSwatch.setMaximumSize(swatchSize);

        final JSpinner groupSizeSpinner = new JSpinner();
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 999, 1);
        groupSizeSpinner.setModel(model);
        groupSizeSpinner.setFont(FONT);

        final TinyButtonGroup plusMinusGroup = new TinyButtonGroup();
        plusMinusGroup.addButton(FontIcon.of(Octicons.DASH));
        plusMinusGroup.addButton(FontIcon.of(Octicons.PLUS));

        return new RgbwSwatchGroup(colorSwatch, whiteSwatch, groupSizeSpinner, plusMinusGroup);
    }

    public ColorSwatch getColorSwatch() {
        return colorSwatch;
    }

    public ColorSwatch getWhiteSwatch() {
        return whiteSwatch;
    }

    public JSpinner getGroupSizeSpinner() {
        return groupSizeSpinner;
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