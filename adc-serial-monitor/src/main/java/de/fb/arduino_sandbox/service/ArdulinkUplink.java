package de.fb.arduino_sandbox.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.annotation.PreDestroy;
import org.apache.commons.lang3.ArrayUtils;
import org.ardulink.core.Link;
import org.ardulink.core.Pin;
import org.ardulink.core.convenience.Links;
import org.ardulink.core.events.AnalogPinValueChangedEvent;
import org.ardulink.core.events.DigitalPinValueChangedEvent;
import org.ardulink.core.events.EventListener;
import org.ardulink.util.URIs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import de.fb.arduino_sandbox.model.RgbwPixel;
import de.fb.arduino_sandbox.util.Constants;
import de.fb.arduino_sandbox.view.SerialPortParams;
import jssc.SerialPortList;

/**
 * Wrapper for ArduLink API facilities.
 * 
 * Warning: this is a stateful component that holds the current open connection to an Arduino serial port!
 *
 * Due to Ardulink API specifics, there also a lot more boilerplate code in here than would be strictly necessary!
 * E.g. event listeners can only be registered on a Link when the link is open, which requires some care in the
 * startListening() and stopListening() methods.
 * 
 * @author Ibragim Kuliev
 *
 */
// @Service
public class ArdulinkUplink implements HardwareUplink {

    private static final Logger log = LoggerFactory.getLogger(ArdulinkUplink.class);

    private Link serialLink;

    private final Map<Integer, EventListener> eventListeners;
    private final Map<Integer, Consumer<Integer>> adcSampleConsumers;

    @Value("${arduino.connect.ping}")
    private Boolean pingProbe;

    @Value("${arduino.connect.timeout}")
    private Integer waitTimeout;

    public ArdulinkUplink() {
        adcSampleConsumers = new HashMap<>();
        eventListeners = new HashMap<>();
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

        log.info("Connecting to Arduino on {} with {} baud...", params.getPortName(), params.getBaudRate());

        final String portConfig = "ardulink://serial-jssc?port=" + params.getPortName()
        + "&baudrate=" + params.getBaudRate()
        + "&pingprobe=" + pingProbe
        + "&waitsecs=" + waitTimeout;

        serialLink = Links.getLink(URIs.newURI(portConfig));
        log.info("Connected!");
    }

    @Override
    public void disconnect() {

        if (serialLink != null) {
            try {
                serialLink.close();
            } catch (IOException ex) {
                log.error("Cannot close serial link!");
            }
        }
    }

    @Override
    public void sendRgbwPixels(final List<RgbwPixel> pixels) {
        // TODO Auto-generated method stub

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

        if (serialLink != null) {
            try {
                Consumer<Integer> sampleConsumer = adcSampleConsumers.get(pin);
                final EventListener listener = new EventListener() {

                    @Override
                    public void stateChanged(final DigitalPinValueChangedEvent event) {
                        // NO-OP
                    }

                    @Override
                    public void stateChanged(final AnalogPinValueChangedEvent event) {
                        sampleConsumer.accept(event.getValue());
                    }
                };

                serialLink.addListener(listener);
                eventListeners.put(pin, listener);

                final Pin adcPin = Pin.analogPin(pin);
                serialLink.startListening(adcPin);

            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    @Override
    public void stopListening(final Integer pin) {

        if (serialLink != null) {
            try {
                final Pin adcPin = Pin.analogPin(pin);
                serialLink.stopListening(adcPin);
                removeEventListener(pin);

            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
        }
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

        if (serialLink != null && eventListeners.containsKey(pin)) {
            EventListener listener = eventListeners.remove(pin);
            if (listener != null) {
                try {
                    serialLink.removeListener(listener);
                } catch (IOException ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
        }
    }

    @Override
    public void resetPixels() {
        // TODO Auto-generated method stub

    }

}
