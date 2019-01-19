package de.fb.arduino_sandbox.pwm_simulator;

import java.util.concurrent.atomic.AtomicBoolean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// max. clock is 1 MHz due to microsecond-based internal representation!
public class PwmSource {

    private static final Logger log = LoggerFactory.getLogger(PwmSource.class);

    // waveform parameters - all timings are in microseconds!
    private long clockPeriod; // used by busyWait() below
    private long period;
    private long tOn;

    private float amplitude;

    // state
    private float value = 0.0f;
    private long phaseCounter = 0;

    private Thread worker;
    private AtomicBoolean active;

    public PwmSource() {
        active = new AtomicBoolean(false);
        worker = new Thread(this::tick);
    }

    public void start() {
        worker.start();
    }

    public void setActive(final boolean active) {
        log.info("Clock period: {} uS, period: {} uS, tOn: {} uS, amplitude: {}", clockPeriod, period, tOn, amplitude);
        this.active.set(active);
    }
    
    public void reset() {
        this.value = 0;
        this.phaseCounter = 0;
    }

    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
        reset();
    }

    public void setClockFrequency(final int clockFrequency) {
        this.clockPeriod = Math.round(1E6f * (1.0f / clockFrequency));
        reset();
    }

    public void setFrequency(float frequency) {
        this.period = Math.round(1E6f * (1.0f / frequency));
        reset();
    }

    // in percent
    public void setDutyCycle(int percent) {
        tOn = Math.round(period * (percent / 100.f));
        reset();
    }

    public float getValue() {
        return value;
    }
    
    public long getPhase() {
        return phaseCounter;
    }

    // this is to be called at the clock frequency
    private void tick() {
        
        while(true) {
            if (active.get() == true) {
                value = (phaseCounter <= tOn) ? amplitude : 0.0f;                
                phaseCounter += clockPeriod;
                if (phaseCounter >= period) {
                    phaseCounter = 0; // reset
                }
                busyWait(clockPeriod);
            }            
        }
    }

    // taken from here: http://www.rationaljava.com/2015/10/measuring-microsecond-in-java.html
    private void busyWait(final long microseconds) {
        final long waitUntil = System.nanoTime() + (microseconds * 1000);
        while (waitUntil > System.nanoTime()) {
            ;
        }
    }
}
