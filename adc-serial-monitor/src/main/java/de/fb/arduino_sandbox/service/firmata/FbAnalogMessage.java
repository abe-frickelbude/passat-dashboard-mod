package de.fb.arduino_sandbox.service.firmata;

import com.bortbort.arduino.FiloFirmata.Messages.ChannelMessage;

public class FbAnalogMessage extends ChannelMessage {

    final private short analogValue;

    public FbAnalogMessage(byte channelByte, short analogValue) {
        super(channelByte);
        this.analogValue = analogValue;
    }

    public short getAnalogValue() {
        return analogValue;
    }
}
