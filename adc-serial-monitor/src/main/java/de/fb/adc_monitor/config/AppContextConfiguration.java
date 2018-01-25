package de.fb.adc_monitor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.fb.adc_monitor.AdcSerialMonitorApp;

@SpringBootApplication(scanBasePackageClasses = AdcSerialMonitorApp.class)
@EnableAutoConfiguration
public class AppContextConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AppContextConfiguration.class);

}
