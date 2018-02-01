package de.fb.adc_monitor.view;

public class KalmanFilter {

    private double q;
    private double r;
    private double p;

    private double k;

    private double x;

    public KalmanFilter(final double q, final double r, final double p, final double x) {
        this.q = q;
        this.r = r;
        this.p = p;
        this.x = x;
    }

    public double addSample(final double measurement) {
        // omit x=x
        p = p + q;
        k = p / (p + r);
        x = x + k * (measurement - x);
        p = (1 - k) * p;
        return x;
    }

    @Override
    public String toString() {
        return "KalmanFilter with p=" + p + ", k=" + k;
    }

    public double getQ() {
        return q;
    }

    public void setQ(final double q) {
        this.q = q;
    }

    public double getR() {
        return r;
    }

    public void setR(final double r) {
        this.r = r;
    }

    public double getP() {
        return p;
    }

    public void setP(final double p) {
        this.p = p;
    }

    public double getX() {
        return x;
    }

    public void setX(final double x) {
        this.x = x;
    }

}
