package de.fb.processing.arduino;

public class KalmanFilter {

    private final float q;
    private final float r;
    private float x;
    private float p;
    private float k;

    public KalmanFilter(final float q, final float r, final float p, final float x) {
        this.q = q;
        this.r = r;
        this.p = p;
        this.x = x;
    }

    public float addSample(final float measurement) {
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

    public float getQ() {
        return q;
    }

    public float getR() {
        return r;
    }

    public float getP() {
        return p;
    }

    public float getK() {
        return k;
    }
}
