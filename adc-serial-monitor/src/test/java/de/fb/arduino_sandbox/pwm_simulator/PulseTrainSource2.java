package de.fb.arduino_sandbox.pwm_simulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * - all internal / external time units are in microseconds (rounded to the nearest integer value)
 * - max. precision is 1 microsecond, i.e. could go up to roughly ~ 1 MHz
 * 
 *
 */
public class PulseTrainSource2 {

    private static final Logger log = LoggerFactory.getLogger(PwmSource.class);

    // waveform parameters
    private float amplitude;
    private float frequency; // Hz
    private float dutyCycle; // percent
    private int samplingRate; // Hz (or samples / sec)
    
    private long phaseAccumulator = 0;
    private long prevTimestamp = 0;

    public float getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }

    public float getFrequency() {
        return frequency;
    }

    public void setFrequency(float frequency) {
        this.frequency = frequency;
        phaseAccumulator = 0;
        prevTimestamp = 0;
    }

    public float getDutyCycle() {
        return dutyCycle;
    }

    public void setDutyCycle(float dutyCycle) {
        this.dutyCycle = dutyCycle;
        phaseAccumulator = 0;
        prevTimestamp = 0;
    }

    public int getSamplingRate() {
        return samplingRate;
    }

    public void setSamplingRate(int samplingRate) {
        this.samplingRate = samplingRate;
        phaseAccumulator = 0;
        prevTimestamp = 0;
    }
    
    public long getPhase() {
        return phaseAccumulator;
    }

    public float nextSample(final long timeStamp) {
        
        // calculate input params
        //long sampleInterval = Math.round(1.0f / samplingRate);
        long period = Math.round(1E6 * (1.0f / frequency));
        long tOn = Math.round(period * (dutyCycle * 0.01));
        
        float sample = (phaseAccumulator < tOn) ? amplitude: 0.0f;
        
        phaseAccumulator += (timeStamp - prevTimestamp);
        prevTimestamp = timeStamp; // remember last timestamp
        
        if(phaseAccumulator >= period) {
            phaseAccumulator = 0; // reset
        }
        return sample;
    }
}
