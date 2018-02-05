package de.fb.adc_monitor.view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import com.bulenkov.darcula.DarculaLaf;
import info.monitorenter.gui.chart.ITrace2D;

public class ZoomableChartViewTest {

    // sample generator period
    private static final int SAMPLE_PERIOD = 10; // don't forget Nyquist here!
    private static final float CARRIER_FREQUENCY = 3.0f;
    private static final float MODULATION_FREQUENCY = 0.5f;

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

        chartView.setXaxisEenabled(true);
        chartView.setYaxisEnabled(true);

        chartView.setXaxisTitle("time", Color.YELLOW);
        chartView.setYaxisTitle("voltage", Color.YELLOW);

        ITrace2D trace = chartView.addLtdTrace("carrier", Color.CYAN, 400);
        ITrace2D trace2 = chartView.addLtdTrace("envelope", Color.GRAY, 400);
        contentPane.add(chartView, BorderLayout.CENTER);

        mainWindow.setBounds(100, 100, 1024, 768);
        mainWindow.setVisible(true);

        createSampleGenerator(trace, trace2);
    }

    public static void createSampleGenerator(final ITrace2D trace, final ITrace2D trace2) {

        final Thread generator = new Thread(() -> {

            final long startTime = System.currentTimeMillis();
            while (true) {

                double timeValue = 0.001 * (System.currentTimeMillis() - startTime);
                double carrier = Math.sin(2 * Math.PI * CARRIER_FREQUENCY * timeValue);
                double envelope = Math.sin(2 * Math.PI * MODULATION_FREQUENCY * timeValue);

                trace.addPoint(timeValue, carrier);
                trace2.addPoint(timeValue, envelope);

                try {
                    Thread.sleep(SAMPLE_PERIOD);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        generator.start();
    }

}
