package de.fb.adc_monitor.view;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JTextPane;
import javax.swing.text.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Appropriated from
 * https://stackoverflow.com/questions/6899282/ansi-colors-in-java-swing-text-fields
 * and modernized (lookup map added, some formatting etc.)
 */
public class JAnsiTextPane extends JTextPane {

    private static final Logger log = LoggerFactory.getLogger(JAnsiTextPane.class);

    // ANSI color constants
    private static final Color BLACK = Color.getHSBColor(0.000f, 0.000f, 0.000f);
    private static final Color RED = Color.getHSBColor(0.000f, 1.000f, 0.502f);
    private static final Color BLUE = Color.getHSBColor(0.667f, 1.000f, 0.502f);
    private static final Color MAGENTA = Color.getHSBColor(0.833f, 1.000f, 0.502f);
    private static final Color GREEN = Color.getHSBColor(0.333f, 1.000f, 0.502f);
    private static final Color YELLOW = Color.getHSBColor(0.167f, 1.000f, 0.502f);
    private static final Color CYAN = Color.getHSBColor(0.500f, 1.000f, 0.502f);
    private static final Color WHITE = Color.getHSBColor(0.000f, 0.000f, 0.753f);

    private static final Color BRIGHT_BLACK = Color.getHSBColor(0.000f, 0.000f, 0.502f);
    private static final Color BRIGHT_RED = Color.getHSBColor(0.000f, 1.000f, 1.000f);
    private static final Color BRIGHT_BLUE = Color.getHSBColor(0.667f, 1.000f, 1.000f);
    private static final Color BRIGHT_MAGENTA = Color.getHSBColor(0.833f, 1.000f, 1.000f);
    private static final Color BRIGHT_GREEN = Color.getHSBColor(0.333f, 1.000f, 1.000f);
    private static final Color BRIGHT_YELLOW = Color.getHSBColor(0.167f, 1.000f, 1.000f);
    private static final Color BRIGHT_CYAN = Color.getHSBColor(0.500f, 1.000f, 1.000f);
    private static final Color BRIGHT_WHITE = Color.getHSBColor(0.000f, 0.000f, 1.000f);

    private static final Color RESET = Color.getHSBColor(0.000f, 0.000f, 1.000f);

    // global lookup map
    private static final Map<String, Color> colorMap;

    static {
        colorMap = new HashMap<>();

        colorMap.put("\u001B[30m", BLACK);
        colorMap.put("\u001B[31m", RED);
        colorMap.put("\u001B[32m", GREEN);
        colorMap.put("\u001B[33m", YELLOW);
        colorMap.put("\u001B[34m", BLUE);
        colorMap.put("\u001B[35m", MAGENTA);
        colorMap.put("\u001B[36m", CYAN);
        colorMap.put("\u001B[37m", WHITE);

        colorMap.put("\u001B[0;30m", BLACK);
        colorMap.put("\u001B[0;31m", RED);
        colorMap.put("\u001B[0;32m", GREEN);
        colorMap.put("\u001B[0;33m", YELLOW);
        colorMap.put("\u001B[0;34m", BLUE);
        colorMap.put("\u001B[0;35m", MAGENTA);
        colorMap.put("\u001B[0;36m", CYAN);
        colorMap.put("\u001B[0;37m", WHITE);

        colorMap.put("\u001B[1;30m", BRIGHT_BLACK);
        colorMap.put("\u001B[1;31m", BRIGHT_RED);
        colorMap.put("\u001B[1;32m", BRIGHT_GREEN);
        colorMap.put("\u001B[1;33m", BRIGHT_YELLOW);
        colorMap.put("\u001B[1;34m", BRIGHT_BLUE);
        colorMap.put("\u001B[1;35m", BRIGHT_MAGENTA);
        colorMap.put("\u001B[1;36m", BRIGHT_CYAN);
        colorMap.put("\u001B[1;37m", BRIGHT_WHITE);

        colorMap.put("\u001B[0m", RESET);
    }

    private String remainingText = "";
    private Color currentColor = RESET;

    public void appendANSI(final String text) {

        // convert ANSI color codes first
        int aPos = 0;   // current char position in addString
        int aIndex = 0; // index of next Escape sequence
        int mIndex = 0; // index of "m" terminating Escape sequence
        String tmpString = "";

        boolean stillSearching = true; // true until no more Escape sequences
        String addString = remainingText + text;
        remainingText = "";

        if (addString.length() > 0) {

            // find first escape
            aIndex = addString.indexOf("\u001B");
            if (aIndex == -1) {
                // no escape/color change in this string, so just send it with current color
                append(currentColor, addString);
                return;
            }

            // otherwise There is an escape character in the string, so we must process it
            if (aIndex > 0) {
                // Escape is not first char, so send text up to first escape
                tmpString = addString.substring(0, aIndex);
                append(currentColor, tmpString);
                aPos = aIndex;
            }
            // aPos is now at the beginning of the first escape sequence

            stillSearching = true;

            while (stillSearching) {

                // find the end of the escape sequence
                mIndex = addString.indexOf("m", aPos);

                if (mIndex < 0) {
                    // the buffer ends halfway through the ANSI string!
                    remainingText = addString.substring(aPos, addString.length());
                    stillSearching = false;
                    continue;
                }

                tmpString = addString.substring(aPos, mIndex + 1);
                currentColor = getANSIColor(tmpString);

                aPos = mIndex + 1;
                // now we have the color, send text that is in that color (up to next escape)

                aIndex = addString.indexOf("\u001B", aPos);

                if (aIndex == -1) {
                    // if that was the last sequence of the input, send remaining text
                    tmpString = addString.substring(aPos, addString.length());
                    append(currentColor, tmpString);
                    stillSearching = false;
                    continue; // jump out of loop early, as the whole string has been sent now
                }

                // there is another escape sequence, so send part of the string and prepare for the next
                tmpString = addString.substring(aPos, aIndex);
                aPos = aIndex;
                append(currentColor, tmpString);

            } // while there's text in the input buffer
        }
    }

    private void append(final Color color, final String text) {

        final StyleContext sc = StyleContext.getDefaultStyleContext();
        final AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        int len = getDocument().getLength();
        try {
            getDocument().insertString(len, text, aset);
        } catch (BadLocationException ex) {
            log.warn(ex.getMessage());
        }
    }

    private Color getANSIColor(final String ansiCode) {

        Color color;
        if (StringUtils.isNotBlank(ansiCode) && colorMap.containsKey(ansiCode)) {
            color = colorMap.get(ansiCode);
        } else {
            color = BRIGHT_WHITE;
        }
        return color;
    }

}