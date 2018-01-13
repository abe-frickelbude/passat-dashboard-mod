package de.fb.adc_monitor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "de.fb.adc_monitor")
public class AppContextConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AppContextConfiguration.class);

}
