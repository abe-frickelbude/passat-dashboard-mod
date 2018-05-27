package de.fb.arduino_sandbox.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bortbort.arduino.FiloFirmata.Firmata;
import com.bortbort.arduino.FiloFirmata.FirmataConfiguration;
import com.bortbort.arduino.FiloFirmata.Messages.SysexReportFirmwareMessage;
import com.bortbort.arduino.FiloFirmata.Messages.SysexReportFirmwareQueryMessage;
import de.fb.arduino_sandbox.service.firmata.ResetAllPixelsMessage;
import de.fb.arduino_sandbox.service.firmata.SetPixelMessage;

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

        // firmata.sendMessage(new ResetAllPixelsMessage());

        firmata.sendMessage(new SetPixelMessage(0, 1, 0, 0, 0));
        firmata.sendMessage(new SetPixelMessage(1, 0, 1, 0, 0));
        // firmata.sendMessage(new SetPixelMessage(2, 0, 0, 1, 0));
        // firmata.sendMessage(new SetPixelMessage(3, 0, 0, 0, 1));
        // firmata.sendMessage(new SetPixelMessage(4, 1, 1, 0, 0));
        // firmata.sendMessage(new SetPixelMessage(5, 0, 1, 1, 0));
        // firmata.sendMessage(new SetPixelMessage(6, 1, 0, 1, 0));
        // firmata.sendMessage(new SetPixelMessage(7, 0, 0, 10, 10));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        firmata.sendMessage(new ResetAllPixelsMessage());

        firmata.stop();
    }

}
