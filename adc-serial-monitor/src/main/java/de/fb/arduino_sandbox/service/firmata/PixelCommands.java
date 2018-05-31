package de.fb.arduino_sandbox.service.firmata;


public class PixelCommands {

    public static final byte PIXEL_SYSEX = 0x40;

    public static final byte SET_NUM_PIXELS = 0x01;
    public static final byte SET_PIXEL = 0x02;
    public static final byte RESET_PIXEL = 0x03;
    public static final byte RESET_ALL = 0x04;
    public static final byte SHOW_PIXELS = 0x05;
}
