package de.fb.arduino_sandbox.service.firmata;

public final class ByteUtils {

    private ByteUtils() {

    }

    public static byte[] to7bitBytes(final byte source) {

        final byte[] result = new byte[2];
        result[0] = (byte) (source & 0x7F); // 7 lower bits
        result[1] = (byte) (source >>> 7 & 0x7F); // MSB
        return result;
    }

    public static byte from7bitBytesToByte(final byte[] source) {
        return (byte) (source[0] + (source[1] << 7));
    }

    public static byte from7bitBytesToByte(final byte byte1, final byte byte2) {
        return (byte) (byte1 + (byte2 << 7));
    }
}
