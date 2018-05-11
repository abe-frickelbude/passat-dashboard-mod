package de.fb.arduino_sandbox.view.component.color;

public final class RangeModel {

    private int min;
    private int max;
    private int stepSize;
    private int value;

    public RangeModel() {
        this(0, 100, 1, 0);
    }

    public RangeModel(final int min, final int max, final int stepSize, final int initialValue) {
        this.min = min;
        this.max = max;
        this.stepSize = stepSize;
        this.value = initialValue;
        validate();
    }

    public int getValue() {
        return value;
    }

    public void setValue(final int value) {
        this.value = value;
        validate();
    }

    public int getMin() {
        return min;
    }

    public void setMin(final int min) {
        this.min = min;
        validate();
    }

    public int getMax() {
        return max;
    }

    public void setMax(final int max) {
        this.max = max;
        validate();
    }

    public int getStepSize() {
        return stepSize;
    }

    public void setStepSize(final int stepSize) {
        this.stepSize = stepSize;
        validate();
    }

    public int increment() {
        if (value + stepSize < max) {
            value += stepSize;
        } else {
            value = max;
        }
        return value;
    }

    public int decrement() {
        if (value - stepSize > min) {
            value -= stepSize;
        } else {
            value = min;
        }
        return value;
    }

    private void validate() {
        // basic sanity checks for initial conditions
        if (min >= max) {
            throw new IllegalArgumentException("Min cannot be greater than or equal to max!");
        }

        if (stepSize <= 0) {
            throw new IllegalArgumentException("Step size must be positive!");
        }

        if (!(value >= min && value <= max)) {
            throw new IllegalArgumentException("(current) value not within the specified [min, max] range!");
        }
    }
}
