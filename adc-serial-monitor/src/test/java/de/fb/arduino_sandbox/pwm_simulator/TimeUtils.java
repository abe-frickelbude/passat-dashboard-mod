package de.fb.arduino_sandbox.pwm_simulator;


public final class TimeUtils {
    
    public static double microsToSeconds(final double micros) {
        return micros * 1E-6;
    }
    
    public static double nanosToSeconds(final double nanos) {
        return nanos * 1E-9;
    }
}
