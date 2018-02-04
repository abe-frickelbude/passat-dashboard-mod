package de.fb.adc_monitor.view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import com.bulenkov.darcula.DarculaLaf;
import de.fb.adc_monitor.view.component.ZoomableChartView;
import info.monitorenter.gui.chart.ITrace2D;

public class ZoomableChartViewTest {

    public static void main(final String[] args) {

        try {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (Exception ex) {}

        final JFrame mainWindow = new JFrame();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainWindow.setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        ZoomableChartView chartView = new ZoomableChartView();
        chartView.setYaxisEnabled(true);
        chartView.setXaxisEenabled(true);

        chartView.setXaxisTitle("time", Color.YELLOW);
        chartView.setYaxisTitle("voltage", Color.YELLOW);

        ITrace2D trace = chartView.addTrace("sample", Color.CYAN, 50);
        trace.addPoint(10.0, 10.0);
        trace.addPoint(20.0, 25.0);
        trace.addPoint(30.0, 50.0);

        contentPane.add(chartView, BorderLayout.CENTER);

        mainWindow.setBounds(100, 100, 1024, 768);
        mainWindow.setVisible(true);
    }
}
