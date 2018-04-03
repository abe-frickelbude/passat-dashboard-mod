package de.fb.arduino_sandbox.view.component.color;

import java.awt.Color;

public class RgbwColorGroup {

    private Color color;
    private int luminance;
    private int groupSize;

    public RgbwColorGroup() {

    }

    public RgbwColorGroup(final Color color, final int luminance, final int groupSize) {
        this.color = color;
        this.luminance = luminance;
        this.groupSize = groupSize;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(final Color color) {
        this.color = color;
    }

    public int getLuminance() {
        return luminance;
    }

    public void setLuminance(final int luminance) {
        this.luminance = luminance;
    }

    public int getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(final int groupSize) {
        this.groupSize = groupSize;
    }
}
