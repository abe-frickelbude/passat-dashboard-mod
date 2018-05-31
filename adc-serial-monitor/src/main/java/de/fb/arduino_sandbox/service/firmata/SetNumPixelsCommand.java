package de.fb.arduino_sandbox.service.firmata;

import java.io.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bortbort.arduino.FiloFirmata.Messages.TransmittableSysexMessage;

public class SetNumPixelsCommand extends TransmittableSysexMessage {

    private static final Logger log = LoggerFactory.getLogger(SetNumPixelsCommand.class);

    private final byte numPixels;

    public SetNumPixelsCommand(final int numPixels) {
        super(PixelCommands.PIXEL_SYSEX);
        this.numPixels = (byte) numPixels;
    }

    @Override
    protected Boolean serialize(final ByteArrayOutputStream outputStream) {

        try {
            outputStream.write(PixelCommands.SET_NUM_PIXELS);
            outputStream.write(numPixels);
            return true;

        } catch (Exception ex) {
            log.error("Cannot serialize message!", ex);
            return false;
        }
    }
}
