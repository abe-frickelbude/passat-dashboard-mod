package de.fb.arduino_sandbox.view.component.color;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class RgbwColorGroups {

    private final List<RgbwColorGroup> colorGroups;

    public RgbwColorGroups() {
        colorGroups = new ArrayList<>();
    }

    public void addGroup(final Color rgb, final int white, final int groupSize) {
        final RgbwColorGroup group = new RgbwColorGroup(rgb, white, groupSize);
        colorGroups.add(group);
    }

    public List<RgbwColorGroup> getGroups() {
        return colorGroups;
    }
}
