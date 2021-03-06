package de.fb.arduino_sandbox.util;

public final class Constants {

    // number of bytes in one MB
    public static final int ONE_MEGABYTE = 1048576;

    public static final int SAMPLE_BUFFER_SIZE = 50;

    public static final int NUM_GRAPH_DATA_POINTS = 200;

    public static final int DEFAULT_DISPLAY_UPDATE_FREQUENCY = 100; // Hz

    public static final double MILLIS_TO_SECONDS = 1E-3;
    public static final double MICROS_TO_SECONDS = 1E-6;
    public static final double NANOS_TO_SECONDS = 1E-9;

    public static final double SECONDS_TO_NANOS = 1E9;

    // arduino pin assignments (taken one-to-one from pins_arduino.h)
    public static final int PIN_A0 = 14;
    public static final int PIN_A1 = 15;
    public static final int PIN_A2 = 16;
    public static final int PIN_A3 = 17;
    public static final int PIN_A4 = 18;
    public static final int PIN_A5 = 19;
    public static final int PIN_A6 = 20;
    public static final int PIN_A7 = 21;

    public static final int[] ANALOG_PINS = {
        PIN_A0, PIN_A1, PIN_A2, PIN_A3, PIN_A4, PIN_A5, PIN_A6, PIN_A7
    };

    // with st. AREF of 5V the Atmega ADC has approx. 4.8 mV resolution.
    public static final float ADC_RESOLUTION = 5.0f / 1024.0f;

    private Constants() {

    }
}
