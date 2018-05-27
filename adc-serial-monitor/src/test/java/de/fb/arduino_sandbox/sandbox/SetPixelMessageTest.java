package de.fb.arduino_sandbox.sandbox;

import static org.junit.Assert.*;
import org.junit.Test;
import com.bortbort.arduino.FiloFirmata.FirmataHelper;
import com.bortbort.arduino.FiloFirmata.Parser.CommandBytes;
import de.fb.arduino_sandbox.service.firmata.PixelCommands;
import de.fb.arduino_sandbox.service.firmata.SetPixelMessage;

public class SetPixelMessageTest {

    @Test
    public void testSerialize() {

        final SetPixelMessage message = new SetPixelMessage(13, 34, 55, 202, 176);

        final byte[] messageData = message.toByteArray();

        assertEquals((byte) CommandBytes.START_SYSEX.getCommandByte(), messageData[0]);
        assertEquals(PixelCommands.PIXEL_SYSEX, messageData[1]);
        assertEquals(PixelCommands.SET_PIXEL, messageData[2]);

        // pixel index
        assertEquals((byte) 13, messageData[3]);

        // R
        assertEquals((byte) 34, FirmataHelper.decodeTwoSevenBitByteSequence(messageData[4], messageData[5]));

        // G
        assertEquals((byte) 55, FirmataHelper.decodeTwoSevenBitByteSequence(messageData[6], messageData[7]));

        // B
        assertEquals((byte) 202, FirmataHelper.decodeTwoSevenBitByteSequence(messageData[8], messageData[9]));

        // W
        assertEquals((byte) 176, FirmataHelper.decodeTwoSevenBitByteSequence(messageData[10], messageData[11]));

        assertEquals((byte) CommandBytes.END_SYSEX.getCommandByte(), messageData[messageData.length - 1]);
    }
}
