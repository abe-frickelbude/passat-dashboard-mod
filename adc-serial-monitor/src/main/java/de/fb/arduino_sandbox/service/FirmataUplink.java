package de.fb.arduino_sandbox.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.PreDestroy;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.bortbort.arduino.FiloFirmata.Firmata;
import com.bortbort.arduino.FiloFirmata.FirmataConfiguration;
import de.fb.arduino_sandbox.model.RgbwPixel;
import de.fb.arduino_sandbox.service.firmata.ResetPixelsCommand;
import de.fb.arduino_sandbox.service.firmata.SetNumPixelsCommand;
import de.fb.arduino_sandbox.service.firmata.SetPixelCommand;
import de.fb.arduino_sandbox.service.firmata.ShowPixelsCommand;
import de.fb.arduino_sandbox.util.Constants;
import de.fb.arduino_sandbox.view.SerialPortParams;
import jssc.SerialPortList;

/**
 * 
 * @author Ibragim Kuliev
 *
 */
@Service
public class FirmataUplink implements HardwareUplink {

    private static final Logger log = LoggerFactory.getLogger(FirmataUplink.class);

    private Firmata firmataLink;

    // private final Map<Integer, EventListener> eventListeners;
    private final Map<Integer, Consumer<Integer>> adcSampleConsumers;

    @Value("${arduino.connect.ping}")
    private Boolean pingProbe;

    @Value("${arduino.connect.timeout}")
    private Integer waitTimeout;

    public FirmataUplink() {
        adcSampleConsumers = new HashMap<>();
        // eventListeners = new HashMap<>();
    }

    @Override
    public List<String> getAvailablePorts() {

        List<String> portNames = Collections.emptyList();
        try {
            String[] names = SerialPortList.getPortNames();
            if (ArrayUtils.isNotEmpty(names)) {
                portNames = Arrays.asList(names);
            }
        } catch (Exception ex) {
            log.warn("Cannot enumerate serial ports!", ex);
        }
        return portNames;
    }

    @Override
    public void connect(final SerialPortParams params) {

        log.info("Initializing Firmata on {} with {} baud...", params.getPortName(), params.getBaudRate());

        final FirmataConfiguration config = new FirmataConfiguration();
        config.setTestProtocolCommunication(pingProbe);
        config.setSerialPortID(params.getPortName());
        config.setSerialPortBaudRate(params.getBaudRate());

        firmataLink = new Firmata(config);
        boolean startedOk = firmataLink.start();
        if (!startedOk) {
            // TODO: exception
        }
        log.info("Connected!");
    }

    @Override
    public void disconnect() {
        if (firmataLink != null && firmataLink.getStarted()) {
            try {
                firmataLink.stop();
            } catch (Exception ex) {
                log.error("Cannot close serial link!");
            }
        }
    }

    @Override
    public void setAdcSampleConsumer(final Integer pin, final Consumer<Integer> consumer) {
        adcSampleConsumers.put(pin, consumer);
    }

    @Override
    public void removeAdcSampleConsumer(final Integer pin) {
        if (adcSampleConsumers.containsKey(pin)) {
            adcSampleConsumers.remove(pin);
            removeEventListener(pin);
        }
    }

    @Override
    public void startListening(final Integer pin) {

        // TODO
        // if (serialLink != null) {
        // try {
        // Consumer<Integer> sampleConsumer = adcSampleConsumers.get(pin);
        // final EventListener listener = new EventListener() {
        //
        // @Override
        // public void stateChanged(final DigitalPinValueChangedEvent event) {
        // // NO-OP
        // }
        //
        // @Override
        // public void stateChanged(final AnalogPinValueChangedEvent event) {
        // sampleConsumer.accept(event.getValue());
        // }
        // };
        //
        // serialLink.addListener(listener);
        // eventListeners.put(pin, listener);
        //
        // final Pin adcPin = Pin.analogPin(pin);
        // serialLink.startListening(adcPin);
        //
        // } catch (IOException ex) {
        // log.error(ex.getMessage(), ex);
        // }
        // }
    }

    @Override
    public void resetPixels() {
        if (firmataLink != null && firmataLink.getStarted()) {
            // clear out previous LED strip configuration
            firmataLink.sendMessage(new ResetPixelsCommand());
        }
    }

    @Override
    public void sendRgbwPixels(final List<RgbwPixel> pixels) {

        if (firmataLink != null && firmataLink.getStarted()) {

            // supply the correct number of pixels to use (important on the firmware side !!)
            firmataLink.sendMessage(new SetNumPixelsCommand(pixels.size()));

            for (int index = 0; index < pixels.size(); index++) {

                final RgbwPixel pixel = pixels.get(index);
                SetPixelCommand pixelMessage = new SetPixelCommand(index,
                    pixel.getRed(), pixel.getGreen(), pixel.getBlue(),
                    pixel.getWhite());

                firmataLink.sendMessage(pixelMessage);
            }

            firmataLink.sendMessage(new ShowPixelsCommand());
        }
    }

    @Override
    public void stopListening(final Integer pin) {
        // TODO
    }

    @Override
    public void stopAll() {
        for (int pin : Constants.ANALOG_PINS) {
            stopListening(pin);
        }
    }

    @PreDestroy
    private void cleanUp() {
        // stopAll();
        disconnect();
        adcSampleConsumers.clear();
    }

    private void removeEventListener(final Integer pin) {

        // if (serialLink != null && eventListeners.containsKey(pin)) {
        // EventListener listener = eventListeners.remove(pin);
        // if (listener != null) {
        // try {
        // serialLink.removeListener(listener);
        // } catch (IOException ex) {
        // log.error(ex.getMessage(), ex);
        // }
        // }
        // }
    }

}
