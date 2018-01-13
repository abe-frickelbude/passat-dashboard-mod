package de.fb.adc_monitor.service;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import jssc.SerialPortList;

@Service
public class SerialPortService {

    private static final Logger log = LoggerFactory.getLogger(SerialPortService.class);

    @PostConstruct
    private void init() {

        String[] portNames = SerialPortList.getPortNames();
        log.info("Port names: {}", portNames);
    }
}
