package de.fb.arduino_sandbox.util;


public final class AdcUtils {

    private AdcUtils() {

    }

    public static double mapAdcSampleToVoltage(final int adcSample) {
        return adcSample * Constants.ADC_RESOLUTION;
    }
}
