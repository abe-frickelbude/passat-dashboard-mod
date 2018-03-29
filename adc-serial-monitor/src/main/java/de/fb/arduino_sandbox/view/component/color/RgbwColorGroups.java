package de.fb.arduino_sandbox.view.component.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;

public class RgbwColorGroups {

    private final List<Triple<Color, Integer, Integer>> colorGroups;

    public RgbwColorGroups() {
        colorGroups = new ArrayList<>();
    }

    public void addGroup(final Color rgb, final int white, final int groupSize) {
        Triple<Color, Integer, Integer> item = new ImmutableTriple<>(rgb, Integer.valueOf(white), Integer.valueOf(groupSize));
        colorGroups.add(item);
    }

    public List<Triple<Color, Integer, Integer>> getGroups() {
        return colorGroups;
    }
}
