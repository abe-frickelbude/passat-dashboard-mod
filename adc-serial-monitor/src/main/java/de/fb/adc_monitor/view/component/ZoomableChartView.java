package de.fb.adc_monitor.view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import org.apache.commons.lang3.ArrayUtils;
import de.fb.adc_monitor.view.DarculaUiColors;
import info.monitorenter.gui.chart.IAxis;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.ZoomableChart;
import info.monitorenter.gui.chart.axistitlepainters.AxisTitlePainterDefault;
import info.monitorenter.gui.chart.traces.Trace2DLtd;

public class ZoomableChartView extends JPanel {

    public static final int DEFAULT_DATA_POINTS = 200;

    private ZoomableChart chart;
    private final Map<String, ITrace2D> traces;

    public ZoomableChartView() {
        super();
        traces = new HashMap<>();
        initUI();
    }

    public ITrace2D addTrace(final String name, final Color color, final int numDataPoints, final int... zIndex) {

        final ITrace2D trace = new Trace2DLtd(numDataPoints, name);
        trace.setColor(color);

        traces.put(name, trace);
        chart.addTrace(trace);

        // has to be called AFTER a trace has been attached to a chart, otherwise setZIndex() will throw a tantrum...
        if (ArrayUtils.isNotEmpty(zIndex)) {
            trace.setZIndex(zIndex[0]);
        }
        return trace;
    }

    public void removeTrace(final String name) {
        if (traces.containsKey(name)) {
            final ITrace2D trace = traces.get(name);
            chart.removeTrace(trace);
        }
    }

    public void removeAllTraces() {
        chart.removeAllTraces();
    }

    @Override
    public void setBackground(final Color background) {
        if (chart != null) {
            chart.setBackground(background);
        }
    }

    @Override
    public void setForeground(final Color foreground) {
        if (chart != null) {
            chart.setForeground(foreground);
        }
    }

    public void setGridColor(final Color gridColor) {
        chart.setGridColor(gridColor);
    }

    public void setXaxisTitle(final String title, final Color color) {
        IAxis<?> axis = chart.getAxisX();
        axis.getAxisTitle().setTitle(title);
        axis.getAxisTitle().setTitlePainter(new AxisTitlePainterDefault());
        axis.getAxisTitle().setTitleColor(color);
    }

    public void setYaxisTitle(final String title, final Color color) {
        IAxis<?> axis = chart.getAxisY();
        axis.getAxisTitle().setTitle(title);
        axis.getAxisTitle().setTitlePainter(new AxisTitlePainterDefault());
        axis.getAxisTitle().setTitleColor(color);
    }

    public void setXaxisEenabled(final boolean enabled) {
        chart.getAxisX().setPaintScale(enabled);
        chart.getAxisX().setPaintGrid(enabled);
    }

    public void setYaxisEnabled(final boolean enabled) {
        chart.getAxisY().setPaintScale(enabled);
        chart.getAxisY().setPaintGrid(enabled);
    }

    private void initUI() {

        setLayout(new BorderLayout());
        chart = new ZoomableChart();

        chart.setUseAntialiasing(true);
        chart.setPaintLabels(true);

        chart.setBackground(DarculaUiColors.PRIMARY1);
        chart.setForeground(DarculaUiColors.MEDIUM_GRAY);
        chart.setGridColor(DarculaUiColors.DARKEST_GRAY);

        // setXaxisTitle("TIME, s", Color.YELLOW);
        // setYaxisTitle("VOLTAGE, v", Color.YELLOW);
        setYaxisEnabled(true);
        setXaxisEenabled(true);

        // addTrace("Reference voltage", Color.WHITE, DEFAULT_DATA_POINTS);
        // addTrace("Input voltage", Color.CYAN, DEFAULT_DATA_POINTS);
        // addTrace("Filtered voltage", Color.YELLOW, DEFAULT_DATA_POINTS, 99);
        // addTrace("Filtered min", Color.GREEN, DEFAULT_DATA_POINTS);
        // addTrace("Filtered max", Color.GREEN, DEFAULT_DATA_POINTS);

        this.add(chart);
    }
}
