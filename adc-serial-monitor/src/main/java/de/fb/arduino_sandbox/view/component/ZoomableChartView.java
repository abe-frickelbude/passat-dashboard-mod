package de.fb.arduino_sandbox.view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.commons.lang3.ArrayUtils;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import de.fb.arduino_sandbox.view.DarculaUiColors;
import de.fb.arduino_sandbox.view.component.color_swatch.ColorSwatch;
import info.monitorenter.gui.chart.IAxis;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.ZoomableChart;
import info.monitorenter.gui.chart.axistitlepainters.AxisTitlePainterDefault;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.traces.Trace2DLtdReplacing;
import info.monitorenter.gui.chart.traces.Trace2DReplacing;

public class ZoomableChartView extends JPanel {

    public static final int DEFAULT_DATA_POINTS = 200;

    private ZoomableChart chart;
    private final Map<String, ITrace2D> traces;

    private ColorSwatch backgroundColorSwatch;
    private ColorSwatch foregroundColorSwatch;
    private ColorSwatch gridColorSwatch;
    private ColorSwatch traceColorSwatch;

    private JCheckBox showXaxisCheckBox;
    private JCheckBox showYaxisCheckBox;
    private JComboBox<String> traceSelectionBox;
    private JButton resetZoomButton;

    public ZoomableChartView() {
        super();
        traces = new HashMap<>();
        initUI();
        connectControls();
    }

    public ITrace2D addLtdTrace(final String name, final Color color, final int numDataPoints, final int... zIndex) {

        final ITrace2D trace = new Trace2DLtd(numDataPoints, name);
        trace.setColor(color);

        traces.put(name, trace);
        chart.addTrace(trace);
        traceSelectionBox.addItem(name);

        // has to be called AFTER a trace has been attached to a chart, otherwise setZIndex() will throw a tantrum...
        if (ArrayUtils.isNotEmpty(zIndex)) {
            trace.setZIndex(zIndex[0]);
        }
        return trace;
    }

    public ITrace2D addReplacingTrace(final String name, final Color color, final int numDataPoints, final int... zIndex) {

        final ITrace2D trace = new ReplacingLtdTrace2D(numDataPoints, name);
        trace.setColor(color);

        traces.put(name, trace);
        chart.addTrace(trace);
        traceSelectionBox.addItem(name);

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
            backgroundColorSwatch.setColor(background);
        }
    }

    @Override
    public void setForeground(final Color foreground) {
        if (chart != null) {
            chart.setForeground(foreground);
            foregroundColorSwatch.setColor(foreground);
        }
    }

    public void setGridColor(final Color gridColor) {
        chart.setGridColor(gridColor);
        gridColorSwatch.setColor(gridColor);
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
        showXaxisCheckBox.setSelected(enabled);
    }

    public void setYaxisEnabled(final boolean enabled) {
        chart.getAxisY().setPaintScale(enabled);
        chart.getAxisY().setPaintGrid(enabled);
        showYaxisCheckBox.setSelected(enabled);
    }

    private void initUI() {

        setLayout(new BorderLayout());
        chart = new ZoomableChart();
        this.add(chart);

        final JPanel controlPanel = new JPanel();
        controlPanel.setMinimumSize(new Dimension(10, 64));
        add(controlPanel, BorderLayout.SOUTH);
        controlPanel.setLayout(new FormLayout(new ColumnSpec[] {
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            ColumnSpec.decode("16dlu"),
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("default:grow"),
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("default:grow"),
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("default:grow"),
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("default:grow"),
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            ColumnSpec.decode("16dlu"),
        },
            new RowSpec[] {
                RowSpec.decode("8dlu"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.PREF_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.PREF_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.PREF_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
            }));

        showXaxisCheckBox = new JCheckBox("X axis");
        controlPanel.add(showXaxisCheckBox, "2, 3");

        JLabel lblNewLabel = new JLabel("Background");
        controlPanel.add(lblNewLabel, "12, 3, left, default");

        backgroundColorSwatch = new ColorSwatch();
        backgroundColorSwatch.setCornerRadius(4);
        backgroundColorSwatch.setPreferredSize(new Dimension(20, 20));
        backgroundColorSwatch.setBorderEnabled(true);
        controlPanel.add(backgroundColorSwatch, "14, 3, left, center");

        traceSelectionBox = new JComboBox<>();
        controlPanel.add(traceSelectionBox, "18, 3, right, center");

        traceColorSwatch = new ColorSwatch();
        traceColorSwatch.setPreferredSize(new Dimension(20, 20));
        traceColorSwatch.setCornerRadius(4);
        traceColorSwatch.setBorderEnabled(true);
        controlPanel.add(traceColorSwatch, "20, 3, left, default");

        showYaxisCheckBox = new JCheckBox("Y axis");
        controlPanel.add(showYaxisCheckBox, "2, 5");

        JLabel lblNewLabel_1 = new JLabel("Foreground");
        controlPanel.add(lblNewLabel_1, "12, 5, left, default");

        foregroundColorSwatch = new ColorSwatch();
        foregroundColorSwatch.setPreferredSize(new Dimension(20, 20));
        foregroundColorSwatch.setCornerRadius(4);
        foregroundColorSwatch.setBorderEnabled(true);
        controlPanel.add(foregroundColorSwatch, "14, 5, left, center");

        resetZoomButton = new JButton("Reset zoom");
        controlPanel.add(resetZoomButton, "2, 7");

        JLabel lblNewLabel_2 = new JLabel("Grid");
        controlPanel.add(lblNewLabel_2, "12, 7");

        gridColorSwatch = new ColorSwatch();
        gridColorSwatch.setCornerRadius(4);
        gridColorSwatch.setPreferredSize(new Dimension(20, 20));
        gridColorSwatch.setBorderEnabled(true);
        controlPanel.add(gridColorSwatch, "14, 7, left, center");

        // set defaults
        chart.setUseAntialiasing(true);
        chart.setPaintLabels(true);

        chart.setBackground(DarculaUiColors.PRIMARY1);
        chart.setForeground(DarculaUiColors.MEDIUM_GRAY);
        chart.setGridColor(DarculaUiColors.DARKEST_GRAY);
        setYaxisEnabled(true);
        setXaxisEenabled(true);
    }

    private void connectControls() {

        resetZoomButton.addActionListener(event -> {
            chart.zoomAll();
        });

        showXaxisCheckBox.addActionListener(event -> {
            setXaxisEenabled(showXaxisCheckBox.isSelected());
        });

        showYaxisCheckBox.addActionListener(event -> {
            setYaxisEnabled(showYaxisCheckBox.isSelected());
        });

        backgroundColorSwatch.addChangeListener(event -> {
            setBackground(backgroundColorSwatch.getColor());
        });

        foregroundColorSwatch.addChangeListener(event -> {
            setForeground(foregroundColorSwatch.getColor());
        });

        gridColorSwatch.addChangeListener(event -> {
            setGridColor(gridColorSwatch.getColor());
        });

        traceColorSwatch.addChangeListener(event -> {
            if (traceSelectionBox.getSelectedIndex() != -1) {
                ITrace2D trace = traces.get(traceSelectionBox.getSelectedItem());
                trace.setColor(traceColorSwatch.getColor());
            }
        });

        traceSelectionBox.addItemListener(event -> {
            if (traceSelectionBox.getSelectedIndex() != -1) {
                ITrace2D trace = traces.get(traceSelectionBox.getSelectedItem());
                traceColorSwatch.setColor(trace.getColor());
            }
        });
    }
}
