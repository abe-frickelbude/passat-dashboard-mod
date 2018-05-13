package de.fb.arduino_sandbox.view.component;

import java.awt.Color;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.slf4j.*;
import com.bulenkov.darcula.DarculaLaf;
import com.jgoodies.forms.layout.*;
import de.fb.arduino_sandbox.view.component.color_swatch.JColorSwatch;

public class JColorSwatchTest {

    private static final Logger log = LoggerFactory.getLogger(JColorSwatchTest.class);

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
        contentPane.setLayout(new FormLayout(new ColumnSpec[] {
            ColumnSpec.decode("100px"),
            ColumnSpec.decode("100px"),
            ColumnSpec.decode("100px"),
            ColumnSpec.decode("100px")
        },
            new RowSpec[] {
                RowSpec.decode("70px"),
                RowSpec.decode("70px"),
        }));

        final JColorSwatch swatch1 = new JColorSwatch();
        contentPane.add(swatch1, "2, 2, center, center");

        final JColorSwatch swatch2 = JColorSwatch.Builder.create().color(Color.CYAN).swatchSize(32).build();
        contentPane.add(swatch2, "1, 2, center, center");

        final JColorSwatch swatch3 = JColorSwatch.Builder.create().color(Color.GREEN).useCustomCursor(true).build();
        contentPane.add(swatch3, "3, 2, center, center");

        mainWindow.setBounds(100, 100, 1024, 768);
        mainWindow.setVisible(true);
    }
}
