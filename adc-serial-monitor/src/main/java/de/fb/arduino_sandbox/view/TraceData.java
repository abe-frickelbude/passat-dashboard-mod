package de.fb.arduino_sandbox.view;

import info.monitorenter.gui.chart.ITracePoint2D;

public class TraceData {

    private ITracePoint2D inputPoint;
    private ITracePoint2D filteredPoint;
    private ITracePoint2D rmsPoint;
    private ITracePoint2D minPoint;
    private ITracePoint2D maxPoint;

    public ITracePoint2D getInputPoint() {
        return inputPoint;
    }

    public void setInputPoint(final ITracePoint2D inputPoint) {
        this.inputPoint = inputPoint;
    }

    public ITracePoint2D getFilteredPoint() {
        return filteredPoint;
    }

    public void setFilteredPoint(final ITracePoint2D filteredPoint) {
        this.filteredPoint = filteredPoint;
    }

    public ITracePoint2D getRmsPoint() {
        return rmsPoint;
    }

    public void setRmsPoint(final ITracePoint2D rmsPoint) {
        this.rmsPoint = rmsPoint;
    }

    public ITracePoint2D getMinPoint() {
        return minPoint;
    }

    public void setMinPoint(final ITracePoint2D minPoint) {
        this.minPoint = minPoint;
    }

    public ITracePoint2D getMaxPoint() {
        return maxPoint;
    }

    public void setMaxPoint(final ITracePoint2D maxPoint) {
        this.maxPoint = maxPoint;
    }
}
