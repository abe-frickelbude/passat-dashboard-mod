package de.fb.arduino_sandbox.pwm_simulator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrigPulseTrainSource {
    
    private static final Logger log = LoggerFactory.getLogger(PwmSource.class);

    // waveform parameters
    private float amplitude;
    private float frequency; // Hz
    private float dutyCycle; // percent
    private int samplingRate; // Hz (or samples / sec)
    
    private float phase = 0; // will be in [0..PI]
    
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
        phase = 0.0f;
        prevTimestamp = 0;
    }

    public float getDutyCycle() {
        return dutyCycle;
    }

    public void setDutyCycle(float dutyCycle) {
        this.dutyCycle = dutyCycle;
        phase = 0.0f;
        prevTimestamp = 0;
    }

    public int getSamplingRate() {
        return samplingRate;
    }

    public void setSamplingRate(int samplingRate) {
        this.samplingRate = samplingRate;
        phase = 0.0f;
        prevTimestamp = 0;
    }
    
    public float getPhase() {
        return phase;
    }
    
    public float nextSample(final long timeStamp) {
     
        
        
        return 0;
    }

}
