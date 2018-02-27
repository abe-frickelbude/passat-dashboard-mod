package de.fb.arduino_sandbox.math;

/**
 * https://en.wikipedia.org/wiki/Exponential_smoothing
 * 
 * @author Ibragim Kuliev
 *
 */
public class DoubleExponentialFilter implements SignalFilter {

    private double alpha; // alpha
    private double beta; // beta

    private double copAlpha;
    private double copBeta;

    private double currentValue;
    private double currentSlope;

    public double getSmoothingFactor() {
        return alpha;
    }

    public void setSmoothingFactor(final double smoothingFactor) {
        this.alpha = smoothingFactor;
        copAlpha = 1.0 - alpha;
    }

    public double getTrendSmoothingFactor() {
        return beta;
    }

    public void setTrendSmoothingFactor(final double trendSmoothingFactor) {
        this.beta = trendSmoothingFactor;
        copBeta = 1.0 - beta;
    }

    @Override
    public double addValue(final double value) {

        double prevValue = currentValue; // required for slope calculation

        currentValue = alpha * value + copAlpha * (currentValue + currentSlope);
        currentSlope = beta * (currentValue - prevValue) + copBeta * currentSlope;
        return currentValue;
    }
}

