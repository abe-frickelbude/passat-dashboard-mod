package de.fb.adc_monitor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import de.fb.adc_monitor.AdcSerialMonitorApp;

@SpringBootApplication(scanBasePackageClasses = AdcSerialMonitorApp.class)
@EnableAutoConfiguration
@EnableScheduling
@EnableAsync
public class AppContextConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AppContextConfiguration.class);

}
