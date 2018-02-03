package de.fb.adc_monitor.math;

public class KalmanFilter implements SignalFilter {

    private double processNoise;
    private double measurementNoise;
    private double errorFactor;

    private double k;

    private double currentValue;

    public KalmanFilter() {
        processNoise = 0.1;
        measurementNoise = 10.0;
        errorFactor = 100.0;
        currentValue = 0.0;
    }

    public KalmanFilter(final double processNoise, final double measurementNoise,
        final double errorFactor, final double initialValue) {

        this();

        this.processNoise = processNoise;
        this.measurementNoise = measurementNoise;
        this.errorFactor = errorFactor;
        this.currentValue = initialValue;
    }

    @Override
    public double addValue(final double value) {
        errorFactor = errorFactor + processNoise;
        k = errorFactor / (errorFactor + measurementNoise);
        currentValue = currentValue + k * (value - currentValue);
        errorFactor = (1 - k) * errorFactor;
        return currentValue;
    }

    @Override
    public String toString() {
        return "KalmanFilter with p=" + errorFactor + ", k=" + k;
    }

    public double getProcessNoise() {
        return processNoise;
    }

    public void setProcessNoise(final double processNoise) {
        this.processNoise = processNoise;
    }

    public double getMeasurementNoise() {
        return measurementNoise;
    }

    public void setMeasurementNoise(final double measurementNoise) {
        this.measurementNoise = measurementNoise;
    }

    public double getErrorFactor() {
        return errorFactor;
    }

    public void setErrorFactor(final double errorFactor) {
        this.errorFactor = errorFactor;
    }

    public double getCurrentValue() {
        return currentValue;
    }
}
