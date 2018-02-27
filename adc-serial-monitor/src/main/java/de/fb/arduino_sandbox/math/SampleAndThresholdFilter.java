package de.fb.arduino_sandbox.math;

public class SampleAndThresholdFilter implements SignalFilter {

    private double currentValue;
    private double threshold;

    public SampleAndThresholdFilter() {
        currentValue = 0;
        threshold = 0;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(final double threshold) {
        this.threshold = threshold;
    }

    @Override
    public double addValue(final double value) {

        if (Math.abs(value - currentValue) > threshold) {
            currentValue = value;
        }
        return currentValue;
    }
}
