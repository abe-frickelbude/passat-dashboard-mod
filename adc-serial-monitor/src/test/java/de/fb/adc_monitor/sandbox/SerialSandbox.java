package de.fb.adc_monitor.sandbox;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialSandbox {

    private static final Logger log = LoggerFactory.getLogger(SerialSandbox.class);

    static SerialPort serialPort;

    public static void main(final String[] args) {

        try {

            serialPort = new SerialPort("COM5");
            serialPort.openPort();// Open port
            serialPort.setParams(57600, 8, 1, 0);// Set params
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);

            // int mask = SerialPort.MASK_RXCHAR;// Prepare mask
            // serialPort.setEventsMask(mask);// Set mask
            serialPort.addEventListener(new SerialPortReader());// Add SerialPortEventListener

            // try (SerialInputStream inputStream = new SerialInputStream(serialPort)) {
            // while (true) {
            //
            // try {
            // byte[] data = new byte[inputStream.available()];
            // int numRead = inputStream.read(data);
            //
            // log.info("Bytes read: {}, bytes: {}", numRead, data);
            //
            // } catch (Exception ex) {
            // log.info(ex.getMessage(), ex);
            // }
            // }
            // } catch (IOException ex) {
            // log.error(ex.getMessage(), ex);
            // }

        } catch (SerialPortException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    /*
     * In this class must implement the method serialEvent, through it we learn about
     * events that happened to our port. But we will not report on all events but only
     * those that we put in the mask. In this case the arrival of the data and change the
     * status lines CTS and DSR
     */
    static class SerialPortReader implements SerialPortEventListener {

        @Override
        @SuppressWarnings("synthetic-access")
        public void serialEvent(final SerialPortEvent event) {

            if (event.isRXCHAR() && event.getEventValue() > 0) {
                // if (true) {
                //if (event.isRXCHAR() /* && event.getEventValue() == 2 */) {

                try {

                    // byte[] data = serialPort.readBytes(2);
                    // int data = bytes[0] | bytes[1] >> 8;

                    // final String data = serialPort.readString();
                    final int numAvailable = serialPort.getInputBufferBytesCount();
                    final byte[] data = serialPort.readBytes();

                    log.info("Bytes read: {}", numAvailable);

                    final String dataString = new String(data);
                    for (String value : StringUtils.split(dataString)) {
                        log.info("Value: {}", value);
                    }

                } catch (SerialPortException ex) {
                    log.error(ex.getMessage(), ex);
                }

                // // If data is available
                // if (event.getEventValue() == 10) {// Check bytes count in the input buffer
                // // Read data, if 10 bytes available
                // try {
                // byte buffer[] = serialPort.readBytes(10);
                // log.info("values: {}", buffer);
                //
                // } catch (SerialPortException ex) {
                // log.error(ex.getMessage(), ex);
                // }
                // }
            }
        }
    }
}
