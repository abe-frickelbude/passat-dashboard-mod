package de.fb.adc_monitor.view;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import info.monitorenter.gui.chart.Chart2D;

public class AdcMonitorView extends JComponent {

    private Chart2D chart;

    public AdcMonitorView() {
        super();
        initUI();
    }

    private void initUI() {

        chart = new Chart2D();
        // TODO: set various properties i.e. axis labels etc.
        chart.setPaintLabels(true);
        chart.setUseAntialiasing(true);

        this.add(chart, BorderLayout.CENTER);
    }
}
