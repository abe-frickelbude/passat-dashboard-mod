package de.fb.arduino_sandbox.util;

import static de.fb.arduino_sandbox.util.Constants.*;

public final class TimeUtils {

    public static double nanosToSeconds(final long nanos) {
        return nanos * NANOS_TO_SECONDS;
    }

    public static double microsToSeconds(final long micros) {
        return micros * MICROS_TO_SECONDS;
    }

    public static double millisToSeconds(final long millis) {
        return millis * MILLIS_TO_SECONDS;
    }
}
