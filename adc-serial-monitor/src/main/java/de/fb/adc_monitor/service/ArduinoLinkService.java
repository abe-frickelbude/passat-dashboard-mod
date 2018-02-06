package de.fb.adc_monitor.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
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
import org.springframework.stereotype.Service;
import de.fb.adc_monitor.view.SerialPortParams;
import jssc.SerialPortList;

/**
 * Wrapper for ArduLink API facilities.
 * 
 * Warning: this is a stateful component that holds the current open connection to an Arduino serial port!
 * 
 * @author Ibragim Kuliev
 *
 */
@Service
public class ArduinoLinkService {

    private static final Logger log = LoggerFactory.getLogger(ArduinoLinkService.class);

    private Link serialLink;

    @Value("${arduino.connect.ping}")
    private Boolean pingProbe;

    @Value("${arduino.connect.timeout}")
    private Integer waitTimeout;

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

    public void connect(final SerialPortParams params) {

        log.info("Connecting to Arduino on {} with {} baud...", params.getPortName(), params.getBaudRate());

        final String portConfig = "ardulink://serial-jssc?port=" + params.getPortName()
        + "&baudrate=" + params.getBaudRate()
        + "&pingprobe=" + pingProbe
        + "&waitsecs=" + waitTimeout;

        serialLink = Links.getLink(URIs.newURI(portConfig));
        log.info("Connected!");

        testAdc();
    }

    public void disconnect() {

        if (serialLink != null) {
            try {
                serialLink.close();
            } catch (IOException ex) {
                log.error("Cannot close serial link!");
            }
        }
    }

    public int readAdcSample(final int pin) {

        return 0;
    }

    @PostConstruct
    private void init() {

    }

    @PreDestroy
    private void cleanUp() {
        disconnect();
    }

    private void testAdc() {

        try {

            serialLink.addListener(new EventListener() {

                @Override
                public void stateChanged(final DigitalPinValueChangedEvent event) {
                }

                @Override
                @SuppressWarnings("synthetic-access")
                public void stateChanged(final AnalogPinValueChangedEvent event) {
                    log.info("Current ADC value: {}", event.getValue());
                }
            });

            Pin adcPin = Pin.analogPin(0);
            serialLink.startListening(adcPin);

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
