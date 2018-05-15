package de.fb.arduino_sandbox.view.ansi;

import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Verdict of this bugger: it don't make any sense whatsoever to replace a 'real' string-based switch with a kustom
 * parse-to-int-then-switch-over-int construct, the difference in performance is marginal, most of the time in the second
 * case being eaten up by the Integer.parseInt() call!
 * 
 * @author Ibragim Kuliev
 *
 */
public class StringSwitchPerformanceTest {

    private static final Logger log = LoggerFactory.getLogger(StringSwitchPerformanceTest.class);

    private static final int NUM_ITERATIONS = 1000000;

    private static final int INT_CODES[] = {
        1, 2, 3, 4, 5, 6, 6, 7, 8, 9, 10
    };

    private static final String STRING_CODES[] = {
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
    };

    @Test
    public void benchmarkStringSwitch() {

        long start = 0, stop = 0;
        long sum = 0;

        for (int i = 0; i < NUM_ITERATIONS; i++) {

            String code = String.valueOf(RandomUtils.nextInt(1001, 1011));
            int switched = 0;

            start = System.nanoTime();
            switch (code) {
                case "1001":
                    switched = 1;
                    break;

                case "1002":
                    switched = 2;
                    break;

                case "1003":
                    switched = 3;
                    break;

                case "1004":
                    switched = 4;
                    break;

                case "1005":
                    switched = 5;
                    break;

                case "1006":
                    switched = 6;
                    break;

                case "1007":
                    switched = 7;
                    break;

                case "1008":
                    switched = 8;
                    break;

                case "1009":
                    switched = 9;
                    break;

                case "1010":
                    switched = 10;
                    break;

                default:
                    break;
            }
            stop = System.nanoTime();
            sum += (stop - start);
            switched = switched * switched;
        }

        log.info("STRING: Avg. exec: {} nS", TimeUnit.NANOSECONDS.toNanos(Math.round(1.0 * sum / NUM_ITERATIONS)));

    }

    @Test
    public void benchmarkIntParseSwitch() {

        long start = 0, stop = 0;
        long sum = 0;

        for (int i = 0; i < NUM_ITERATIONS; i++) {

            String code = String.valueOf(RandomUtils.nextInt(1001, 1011));
            int switched = 0;

            start = System.nanoTime();
            switch (Integer.parseInt(code)) {
                case 1001:
                    switched = 1;
                    break;

                case 1002:
                    switched = 2;
                    break;

                case 1003:
                    switched = 3;
                    break;

                case 1004:
                    switched = 4;
                    break;

                case 1005:
                    switched = 5;
                    break;

                case 1006:
                    switched = 6;
                    break;

                case 1007:
                    switched = 7;
                    break;

                case 1008:
                    switched = 8;
                    break;

                case 1009:
                    switched = 9;
                    break;

                case 1010:
                    switched = 10;
                    break;

                default:
                    break;
            }
            stop = System.nanoTime();
            sum += (stop - start);
            switched = switched * switched;
        }

        log.info("INT: Avg. exec: {} nS", TimeUnit.NANOSECONDS.toNanos(Math.round(1.0 * sum / NUM_ITERATIONS)));

    }

}
