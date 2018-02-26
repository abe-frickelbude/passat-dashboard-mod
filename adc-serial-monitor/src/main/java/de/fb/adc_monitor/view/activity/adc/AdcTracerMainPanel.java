package de.fb.adc_monitor.view.activity.adc;

import java.awt.*;
import javax.swing.*;
import org.springframework.stereotype.Component;
import de.fb.adc_monitor.util.Constants;
import de.fb.adc_monitor.view.TraceData;
import de.fb.adc_monitor.view.component.ZoomableChartView;
import info.monitorenter.gui.chart.ITrace2D;

@Component
public class AdcTracerMainPanel extends JPanel {

    private ZoomableChartView chartView;

    private ITrace2D inputSignalTrace;
    private ITrace2D filteredSignalTrace;
    private ITrace2D minSignalTrace;
    private ITrace2D maxSignalTrace;
    private ITrace2D rmsSignalTrace;

    public AdcTracerMainPanel() {
        initUI();
    }

    public void updateChart(final TraceData traceData) {

        SwingUtilities.invokeLater(() -> {
            inputSignalTrace.addPoint(traceData.getInputPoint());
            filteredSignalTrace.addPoint(traceData.getFilteredPoint());
            minSignalTrace.addPoint(traceData.getMinPoint());
            maxSignalTrace.addPoint(traceData.getMaxPoint());
            rmsSignalTrace.addPoint(traceData.getRmsPoint());
        });
    }

    public void clearChart() {
        inputSignalTrace.removeAllPoints();
        filteredSignalTrace.removeAllPoints();
    }

    public void setInputSignalTraceVisible(final boolean visible) {
        inputSignalTrace.setVisible(visible);
    }

    public void setFilteredSignalTraceVisible(final boolean visible) {
        filteredSignalTrace.setVisible(visible);
    }

    public void setMinSignalTraceVisible(final boolean visible) {
        minSignalTrace.setVisible(visible);
    }

    public void setMaxSignalTraceVisible(final boolean visible) {
        maxSignalTrace.setVisible(visible);
    }

    public void setRmsSignalTraceVisible(final boolean visible) {
        rmsSignalTrace.setVisible(visible);
    }

    private void initUI() {

        this.setLayout(new BorderLayout());
        createChart();
        add(chartView, BorderLayout.CENTER);
    }

    private void createChart() {

        chartView = new ZoomableChartView();
        chartView.setXaxisTitle("TIME, s", Color.WHITE);
        chartView.setYaxisTitle("VOLTAGE, V", Color.WHITE);

        inputSignalTrace = chartView.addLtdTrace("input signal", Color.CYAN, Constants.NUM_GRAPH_DATA_POINTS);
        filteredSignalTrace = chartView.addLtdTrace("filtered signal", Color.YELLOW, Constants.NUM_GRAPH_DATA_POINTS);

        minSignalTrace = chartView.addLtdTrace("min", Color.GREEN, Constants.NUM_GRAPH_DATA_POINTS, 99);
        maxSignalTrace = chartView.addLtdTrace("max", Color.GREEN, Constants.NUM_GRAPH_DATA_POINTS, 99);
        rmsSignalTrace = chartView.addLtdTrace("RMS", Color.RED, Constants.NUM_GRAPH_DATA_POINTS, 99);

        minSignalTrace.setVisible(false);
        maxSignalTrace.setVisible(false);
        rmsSignalTrace.setVisible(false);
    }
}
