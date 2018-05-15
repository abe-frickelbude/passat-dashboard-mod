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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("RgbwColorGroups [");
        if (colorGroups != null) {
            builder.append("\n");
            for (RgbwColorGroup group : colorGroups) {
                builder.append("\t");
                builder.append(group.toString());
                builder.append("\n");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
