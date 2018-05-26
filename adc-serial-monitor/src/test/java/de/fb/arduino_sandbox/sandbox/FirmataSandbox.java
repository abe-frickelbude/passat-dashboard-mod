package de.fb.arduino_sandbox.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bortbort.arduino.FiloFirmata.Firmata;
import com.bortbort.arduino.FiloFirmata.FirmataConfiguration;
import com.bortbort.arduino.FiloFirmata.Messages.SysexReportFirmwareMessage;
import com.bortbort.arduino.FiloFirmata.Messages.SysexReportFirmwareQueryMessage;

public class FirmataSandbox {

    private static final Logger log = LoggerFactory.getLogger(FirmataSandbox.class);

    private Firmata firmata;

    public FirmataSandbox() {

        final FirmataConfiguration config = new FirmataConfiguration();
        config.setTestProtocolCommunication(false);
        config.setSerialPortBaudRate(57600);
        firmata = new Firmata(config);
    }

    public static void main(final String[] args) {
        final FirmataSandbox sandbox = new FirmataSandbox();
        sandbox.run(args);
    }

    private void run(final String[] args) {

        firmata.start();

        SysexReportFirmwareMessage message = firmata.sendMessageSynchronous(SysexReportFirmwareMessage.class,
            new SysexReportFirmwareQueryMessage());


        log.info("Firmware: {}, {}.{}", message.getFirmwareName(), message.getMajorVersion(), message.getMinorVersion());

        // Test LED color transmission

        firmata.sendMessage(new RgbwPixelSetMessage());

        firmata.stop();
    }

}
