package de.fb.arduino_sandbox.view.component.dial;

public final class IntegerRangeModel {

    private int min;
    private int max;
    private int coarseStep;
    private int fineStep;
    private int value;

    public IntegerRangeModel() {
        this(0, 100, 10, 1, 0);
    }

    public IntegerRangeModel(final int min, final int max, final int coarseStep, final int fineStep, final int initialValue) {
        this.min = min;
        this.max = max;
        this.coarseStep = coarseStep;
        this.fineStep = fineStep;
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

    public int getCoarseStep() {
        return coarseStep;
    }

    public void setCoarseStep(final int stepSize) {
        this.coarseStep = stepSize;
        validate();
    }

    public int getFineStep() {
        return fineStep;
    }

    public void setFineStep(final int stepSize) {
        this.fineStep = stepSize;
        validate();
    }

    public int coarseIncrement() {
        if (value + coarseStep < max) {
            value += coarseStep;
        } else {
            value = max;
        }
        return value;
    }

    public int coarseDecrement() {
        if (value - coarseStep > min) {
            value -= coarseStep;
        } else {
            value = min;
        }
        return value;
    }

    public int fineIncrement() {
        if (value + fineStep < max) {
            value += fineStep;
        } else {
            value = max;
        }
        return value;
    }

    public int fineDecrement() {
        if (value - fineStep > min) {
            value -= fineStep;
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

        if (coarseStep <= 0) {
            throw new IllegalArgumentException("Coarse step size must be positive!");
        }

        if (fineStep <= 0) {
            throw new IllegalArgumentException("Fine step size must be positive!");
        }

        if (coarseStep < fineStep) {
            throw new IllegalArgumentException("Coarse step size cannot be smaller than fine step size!");
        }

        if (!(value >= min && value <= max)) {
            throw new IllegalArgumentException("(current) value not within the specified [min, max] range!");
        }
    }
}
