package de.fb.arduino_sandbox.math;


public class SimpleExponentialFilter implements SignalFilter {

    private double currentValue;
    private double alpha;
    private double copAlpha;

    public SimpleExponentialFilter() {
    }

    public double getSmoothingFactor() {
        return alpha;
    }

    public void setSmoothingFactor(final double smoothingFactor) {
        this.alpha = smoothingFactor;
        copAlpha = 1.0 - alpha;
    }

    @Override
    public double addValue(final double value) {
        currentValue = alpha * value + copAlpha * currentValue;
        // currentValue = copAlpha * value + alpha * currentValue;
        return currentValue;
    }

}
