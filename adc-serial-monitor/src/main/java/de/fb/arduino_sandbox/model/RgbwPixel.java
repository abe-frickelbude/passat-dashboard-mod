package de.fb.arduino_sandbox.model;

public class RgbwPixel {

    private byte red;
    private byte green;
    private byte blue;
    private byte white;

    public RgbwPixel(final byte red, final byte green, final byte blue, final byte white) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.white = white;
    }

    public RgbwPixel(final int red, final int green, final int blue, final int white) {
        this.red = (byte) red;
        this.green = (byte) green;
        this.blue = (byte) blue;
        this.white = (byte) white;
    }

    public byte getRed() {
        return red;
    }

    public void setRed(final byte red) {
        this.red = red;
    }

    public void setRed(final int red) {
        this.red = (byte) red;
    }

    public byte getGreen() {
        return green;
    }

    public void setGreen(final byte green) {
        this.green = green;
    }

    public void setGreen(final int green) {
        this.green = (byte) green;
    }

    public byte getBlue() {
        return blue;
    }

    public void setBlue(final byte blue) {
        this.blue = blue;
    }

    public void setBlue(final int blue) {
        this.blue = (byte) blue;
    }

    public byte getWhite() {
        return white;
    }

    public void setWhite(final byte white) {
        this.white = white;
    }

    public void setWhite(final int white) {
        this.white = (byte) white;
    }
}
