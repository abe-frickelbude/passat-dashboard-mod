package de.fb.arduino_sandbox.service.firmata;

import java.io.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bortbort.arduino.FiloFirmata.Messages.TransmittableSysexMessage;

public class ShowPixelsCommand extends TransmittableSysexMessage {

    private static final Logger log = LoggerFactory.getLogger(ShowPixelsCommand.class);

    public ShowPixelsCommand() {
        super(PixelCommands.PIXEL_SYSEX);
    }

    @Override
    protected Boolean serialize(final ByteArrayOutputStream outputStream) {

        try {
            outputStream.write(PixelCommands.SHOW_PIXELS);
            return true;

        } catch (Exception ex) {
            log.error("Cannot serialize message!", ex);
            return false;
        }
    }
}
