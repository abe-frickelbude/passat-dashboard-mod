package de.fb.arduino_sandbox.sandbox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bortbort.arduino.FiloFirmata.Firmata;
import com.bortbort.arduino.FiloFirmata.FirmataConfiguration;
import com.bortbort.arduino.FiloFirmata.MessageListener;
import com.bortbort.arduino.FiloFirmata.Messages.AnalogMessage;
import com.bortbort.arduino.FiloFirmata.Messages.Message;
import com.bortbort.arduino.FiloFirmata.Messages.SysexReportFirmwareMessage;
import com.bortbort.arduino.FiloFirmata.Messages.SysexReportFirmwareQueryMessage;
import de.fb.arduino_sandbox.service.firmata.*;

public class FirmataSandbox {

    private static final Logger log = LoggerFactory.getLogger(FirmataSandbox.class);

    private Firmata firmata;
    
    static {
        Firmata.addCustomCommandParser(new FbAnalogMessageBuilder());
        //Firmata.addCustomSysexParser(new FbAnalogMessageBuilder());
    }

    public FirmataSandbox() {

        final FirmataConfiguration config = new FirmataConfiguration();
        config.setTestProtocolCommunication(false);
        config.setSerialPortID("COM4");
        config.setSerialPortBaudRate(115200);
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

        // testLedPixelCommands();
        testAnalogInputMessages();

        firmata.stop();
    }

    private void testAnalogInputMessages() {

        MessageListener<Message> messageListener = new MessageListener<>() {
            @Override
            public void messageReceived(final Message message) {
                if (message instanceof FbAnalogMessage) {
                    log.info("Channel: {}, value: {}",
                        FbAnalogMessage.class.cast(message).getChannelByte(),
                        FbAnalogMessage.class.cast(message).getAnalogValue());
                }
            }
        };

        firmata.addMessageListener(messageListener);

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void testLedPixelCommands() {
        // Test LED color transmission

        // for (int i = 0; i < 100; i++) {

        firmata.sendMessage(new SetNumPixelsCommand(16));
        firmata.sendMessage(new ResetPixelsCommand());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // RED
        firmata.sendMessage(new SetPixelCommand(0, 1, 0, 0, 0));
        firmata.sendMessage(new SetPixelCommand(1, 1, 0, 0, 0));
        firmata.sendMessage(new SetPixelCommand(2, 1, 0, 0, 0));

        // YELLOW
        firmata.sendMessage(new SetPixelCommand(3, 1, 1, 0, 0));
        firmata.sendMessage(new SetPixelCommand(4, 1, 1, 0, 0));

        // GREEN
        firmata.sendMessage(new SetPixelCommand(5, 0, 1, 0, 0));

        // BLUE
        firmata.sendMessage(new SetPixelCommand(6, 0, 0, 1, 0));
        firmata.sendMessage(new SetPixelCommand(7, 0, 0, 1, 0));
        firmata.sendMessage(new SetPixelCommand(8, 0, 0, 1, 0));

        // CYAN
        firmata.sendMessage(new SetPixelCommand(9, 0, 1, 1, 0));

        // PURPLE
        firmata.sendMessage(new SetPixelCommand(10, 5, 0, 5, 0));

        // WHITE
        firmata.sendMessage(new SetPixelCommand(11, 0, 0, 0, 1));
        firmata.sendMessage(new SetPixelCommand(12, 0, 0, 0, 5));
        firmata.sendMessage(new SetPixelCommand(13, 0, 0, 0, 10));
        firmata.sendMessage(new SetPixelCommand(14, 0, 0, 0, 20));

        // MIXED
        firmata.sendMessage(new SetPixelCommand(15, 6, 4, 20, 5));

        // update
        firmata.sendMessage(new ShowPixelsCommand());

        // try {
        // Thread.sleep(500);
        // } catch (InterruptedException e) {
        // Thread.currentThread().interrupt();
        // }
        //
        //
        // firmata.sendMessage(new ResetPixelsCommand());
        // }
    }

}
