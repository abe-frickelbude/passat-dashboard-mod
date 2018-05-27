package de.fb.arduino_sandbox.service.firmata;


public class PixelCommands {

    public static final byte PIXEL_SYSEX = 0x40;

    public static final byte SET_PIXEL = 0x10;
    public static final byte RESET_PIXEL = 0x11;
    public static final byte RESET_ALL = 0x12;
}
