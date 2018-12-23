package de.fb.arduino_sandbox.service.firmata;

import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bortbort.arduino.FiloFirmata.FirmataHelper;
import com.bortbort.arduino.FiloFirmata.Messages.Message;
import com.bortbort.arduino.FiloFirmata.Parser.CommandBytes;
import com.bortbort.arduino.FiloFirmata.Parser.MessageBuilder;

public class FbAnalogMessageBuilder extends MessageBuilder {

    private static final Logger log = LoggerFactory.getLogger(FbAnalogMessageBuilder.class);

    public FbAnalogMessageBuilder() {
        super(CommandBytes.ANALOG_MESSAGE);
    }

    @Override
    public Message buildMessage(final Byte channelByte, final InputStream inputStream) {

        Message result = null;
        byte[] data = new byte[2];
        try {

            if (FirmataHelper.fastReadBytesWithTimeout(inputStream, data, 2000)) {
                // combine two 7-bit bytes into a 14-bit short integer
                short value = (short) ((data[0] & 0x7F) | (short) (data[1] << 7));
                result = new FbAnalogMessage(channelByte, value);
            } else {
                log.error("Timeout while reading analog message value for channel {}", channelByte);
            }
        } catch (IOException ex) {
            log.error("Error reading from serial port. Unable to read analog message!", ex);
        }
        return result;
    }
}
