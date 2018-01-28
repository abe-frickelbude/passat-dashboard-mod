package de.fb.adc_monitor.view.ansi;

import static org.junit.Assert.assertTrue;
import java.awt.Color;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

public class ColorSchemeLoaderTest {

    @Test
    public void testLoadScheme() {
        Map<String, Pair<Color, Color>> colorMap = ColorSchemeLoader.loadScheme("/ansi_color_schemes/xterm");
        assertTrue(!colorMap.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoadEmptySchemeName() {
        ColorSchemeLoader.loadScheme(StringUtils.EMPTY);
    }

    @Test
    public void testLoadBadSchemeName() {
        Map<String, Pair<Color, Color>> colorMap = ColorSchemeLoader.loadScheme("/blah");
        assertTrue(colorMap.isEmpty());
    }
}
