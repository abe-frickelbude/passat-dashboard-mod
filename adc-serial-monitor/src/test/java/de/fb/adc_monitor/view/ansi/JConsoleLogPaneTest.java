package de.fb.adc_monitor.view.ansi;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bulenkov.darcula.DarculaLaf;

public class JConsoleLogPaneTest {

    private static final Logger log = LoggerFactory.getLogger(JConsoleLogPaneTest.class);

    public static void main(final String[] args) {

        try {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (Exception ex) {}

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        window.setContentPane(contentPane);

        JConsoleLogPane consoleLogArea = new JConsoleLogPane();

        consoleLogArea.setColorScheme("/ansi_color_schemes/monokai");
        consoleLogArea.setColorsEnabled(true);
        consoleLogArea.setUseDefaultLafColors(true);
        consoleLogArea.setMaxContentLength(256000);

        JScrollPane scrollPane = new JScrollPane(consoleLogArea);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        window.setVisible(true);
        window.setBounds(100, 100, 692, 765);

        for (int i = 0; i < 10; i++) {
            consoleLogArea
            .append(
                "\u001b[1;30m A quick Fox \u001b[31m jumps \u001b[43;30m oVeR \u001b[36m a LaZy Dog's \u001b[34m Dusty \u001b[0m DeN\n");
        }

        consoleLogArea.setCaptureStandardStreams(true);

        // now log via a logger
        final Thread messager = new Thread(() -> {

            while (true) {
                int color = RandomUtils.nextInt(31, 38);
                int foxId = RandomUtils.nextInt();

                log.info(
                    "\u001b[1;{}m A quick fox #{} \u001b[31m jumps \u001b[34m over \u001b[36m a lazy dog's \u001b[34m dusty \u001b[0m kennel",
                    color, foxId);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        messager.start();
        // consoleLogArea.setCaptureStandardStreams(false);
    }
}
