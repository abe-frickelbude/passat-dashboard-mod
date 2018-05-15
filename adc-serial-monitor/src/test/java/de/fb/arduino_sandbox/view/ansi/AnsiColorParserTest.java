package de.fb.arduino_sandbox.view.ansi;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AnsiColorParserTest {

    private static final Logger log = LoggerFactory.getLogger(AnsiColorParserTest.class);

    @Test
    public void testParseColorCodes() {

        // Regex:
        // \\u001b\[(\d+)m for standard codes
        // \\u001b\[1;(\d+)m for bright codes
        // \\u001b\[(\d+)m([^\\u001b]*) for standard codes along with the string until the next code
        // \\u001b\[(\d+;)?(\d+)m([^\\u001b]*) will also capture the modifier byte sequence
        // example: \\u001b[1;30m A QUICK FOX \\u001b[31m JUMPS \\u001b[32m OVER \\u001b[33m A LAZY DOG'S \\u001b[0m DEN

        String example = "\u001b[1;30m A QUICK FOX \u001b[31m JUMPS \u001b[32m OVER \u001b[36m A LAZY DOG'S \u001b[0m DEN";

        Pattern pattern = Pattern.compile("\\u001b\\[(\\d+);?(\\d+)?m([^\\u001b]*)");
        Matcher matcher = pattern.matcher(example);

        while (matcher.find()) {
            log.info("group 1: {}, group 2: {}, group 3: {}", matcher.group(1), matcher.group(2), matcher.group(3));
        }

    }
}
