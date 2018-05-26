package de.fb.arduino_sandbox.sandbox;

import java.io.ByteArrayOutputStream;
import com.bortbort.arduino.FiloFirmata.Messages.TransmittableSysexMessage;

public class RgbwPixelSetMessage extends TransmittableSysexMessage {

    private static final byte PIXEL_COMMAND = 0x40;

    public RgbwPixelSetMessage() {
        super(PIXEL_COMMAND);
    }

    @Override
    protected Boolean serialize(final ByteArrayOutputStream outputStream) {
        // TODO Auto-generated method stub
        return true;
    }

}
