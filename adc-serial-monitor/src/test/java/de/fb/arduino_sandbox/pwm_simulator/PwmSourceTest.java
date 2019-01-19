package de.fb.arduino_sandbox.pwm_simulator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PwmSourceTest {
    
    private static final Logger log = LoggerFactory.getLogger(PwmSourceTest.class);
    
    @Test
    public void testRun() {
                
        PwmSource source = new PwmSource();
        source.setAmplitude(1.0f);
        
        source.setClockFrequency(100);
        source.setFrequency(10.0f);
        source.setDutyCycle(35);
        
        source.start(); // init worker thread
        source.setActive(true);
        
        try {
            Thread.sleep(5000);
        } catch(InterruptedException ex) {
            
        }
    }
}
