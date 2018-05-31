package de.fb.arduino_sandbox.service.firmata;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bortbort.arduino.FiloFirmata.FirmataHelper;
import com.bortbort.arduino.FiloFirmata.Messages.TransmittableSysexMessage;

/**
 * 
 * Explanations:
 * 
 * value & 0x7F -> mask out the MSB, used for conversion to 7-bit byte
 *
 */
public class SetPixelCommand extends TransmittableSysexMessage {

    private static final Logger log = LoggerFactory.getLogger(SetPixelCommand.class);

    private final byte index;
    private final byte rgbw[];

    /**
     * 
     * @param index
     *        can be 0 <= index <= 127 (sent as one 7-bit byte)
     * @param r
     *        can be 0 <= r <= 255 (sent as 2 7-bit bytes)
     * @param g
     *        can be 0 <= g <= 255 (sent as 2 7-bit bytes)
     * @param b
     *        can be 0 <= b <= 255 (sent as 2 7-bit bytes)
     * @param w
     *        can be 0 <= w <= 255 (sent as 2 7-bit bytes)
     */
    public SetPixelCommand(final int index, final int r, final int g, final int b, final int w) {
        this((byte) index, (byte) r, (byte) g, (byte) b, (byte) w);
    }

    public SetPixelCommand(final byte index, final byte r, final byte g, final byte b, final byte w) {
        super(PixelCommands.PIXEL_SYSEX);

        this.index = index;
        this.rgbw = new byte[4];
        this.rgbw[0] = r;
        this.rgbw[1] = g;
        this.rgbw[2] = b;
        this.rgbw[3] = w;
    }

    @Override
    protected Boolean serialize(final ByteArrayOutputStream outputStream) {

        try {
            outputStream.write(PixelCommands.SET_PIXEL);
            outputStream.write(index);
            outputStream.write(FirmataHelper.encodeTwoSevenBitByteSequence(rgbw));
            return true;
        } catch (IOException ex) {
            log.error("Cannot serialize message!", ex);
            return false;
        }
    }
}
