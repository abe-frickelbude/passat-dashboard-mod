package de.fb.adc_monitor.sandbox;

import javax.swing.JSlider;

public final class GuiUtils {

    static final int SLIDER_TICKS = 1000;

    private GuiUtils() {

    }

    static String formatDouble(final double value) {
        return String.format("%.3f", value);
    }

    // sliderValue is always assumed to be in range [0...SLIDER_TICKS]
    static double interpolateFilterParam(final int sliderValue, final double min, final double max) {
        double normSliderValue = 1.0 * sliderValue / SLIDER_TICKS;
        return min + normSliderValue * (max - min);
    }

    static int paramToSliderPosition(final double value, final double min, final double max) {

        final double normValue = (value - min) / (max - min);
        final int position = (int) Math.round(SLIDER_TICKS * normValue);
        return position;
    }

    static JSlider makeSlider() {
        final JSlider slider = new JSlider();
        slider.setValue(0);
        slider.setMinimum(0);
        slider.setMaximum(SLIDER_TICKS);
        return slider;
    }

}
