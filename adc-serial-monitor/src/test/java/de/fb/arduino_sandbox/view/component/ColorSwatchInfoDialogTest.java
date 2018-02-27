package de.fb.arduino_sandbox.view.component;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bulenkov.darcula.DarculaLaf;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import de.fb.arduino_sandbox.view.component.*;

public class ColorSwatchInfoDialogTest {

    private static final Logger log = LoggerFactory.getLogger(ColorSwatchInfoDialogTest.class);

    public static void main(final String[] args) {

        try {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (Exception ex) {}

        final JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        window.setContentPane(contentPane);
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

        window.setBounds(100, 100, 500, 500);
        window.setVisible(true);

        // -----------------------------------------------------------------------

        swatch1.addChangeListener(event -> {
            final ColorSwatchInfoDialog dialog = new ColorSwatchInfoDialog(window, swatch1.getColor());
            dialog.setVisible(true);
        });
    }
}
