package de.fb.adc_monitor.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import jssc.SerialPortList;

@Service
public class SerialPortService {

    private static final Logger log = LoggerFactory.getLogger(SerialPortService.class);

    public List<String> getPortNames() {

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

    @PostConstruct
    private void init() {

    }
}
