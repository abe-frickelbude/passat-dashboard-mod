package de.fb.arduino_sandbox.service.firmata;

import java.io.ByteArrayOutputStream;
import com.bortbort.arduino.FiloFirmata.Messages.TransmittableSysexMessage;

public class ResetPixelsCommand extends TransmittableSysexMessage {

    public ResetPixelsCommand() {
        super(PixelCommands.PIXEL_SYSEX);
    }

    @Override
    protected Boolean serialize(final ByteArrayOutputStream outputStream) {
        outputStream.write(PixelCommands.RESET_ALL);
        return true;
    }
}
