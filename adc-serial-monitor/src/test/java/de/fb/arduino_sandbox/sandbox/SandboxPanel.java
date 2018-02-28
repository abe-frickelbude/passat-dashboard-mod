package de.fb.arduino_sandbox.sandbox;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import de.fb.arduino_sandbox.view.component.JColorSwatch;

public class SandboxPanel extends JPanel {

    private JColorSwatch colorSwatch;
    private JColorSwatch colorSwatch2;
    private JSpinner spinner;
    private JSpinner spinner_1;
    private JSpinner spinner_2;
    private JSpinner spinner_3;
    private JSpinner spinner_4;
    private JSpinner spinner_5;
    private JButton btnNewButton;
    private JButton btnX;
    private JButton btnX_1;
    private JButton btnX_2;
    private JButton btnX_3;
    private JButton btnX_4;
    private JButton btnNewButton_1;

    /**
     * Create the panel.
     */
    public SandboxPanel() {
        setLayout(new FormLayout(new ColumnSpec[] {
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("max(30dlu;default)"),
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("max(30dlu;default)"),
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("max(30dlu;default)"),
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("max(30dlu;default)"),
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("max(30dlu;default)"),
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("max(30dlu;default)"),
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
        },
            new RowSpec[] {
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
        }));

        colorSwatch = new JColorSwatch.Builder().swatchSize(40).build();
        add(colorSwatch, "4, 4, center, default");

        colorSwatch2 = new JColorSwatch.Builder().swatchSize(40).build();
        colorSwatch2.setSize(new Dimension(32, 32));
        add(colorSwatch2, "4, 6, center, default");

        spinner = new JSpinner();
        spinner.setFont(new Font("Dialog", Font.PLAIN, 9));
        add(spinner, "4, 8, fill, default");

        spinner_1 = new JSpinner();
        spinner_1.setFont(new Font("Dialog", Font.PLAIN, 9));
        add(spinner_1, "6, 8");

        spinner_2 = new JSpinner();
        spinner_2.setFont(new Font("Dialog", Font.PLAIN, 9));
        add(spinner_2, "8, 8");

        spinner_3 = new JSpinner();
        spinner_3.setFont(new Font("Dialog", Font.PLAIN, 9));
        add(spinner_3, "10, 8");

        spinner_4 = new JSpinner();
        spinner_4.setFont(new Font("Dialog", Font.PLAIN, 9));
        add(spinner_4, "12, 8");

        spinner_5 = new JSpinner();
        spinner_5.setFont(new Font("Dialog", Font.PLAIN, 9));
        add(spinner_5, "14, 8");

        btnNewButton = new JButton("X");
        btnNewButton.setMargin(new Insets(0, 0, 0, 0));
        btnNewButton.setFont(new Font("Dialog", Font.BOLD, 9));
        // btnNewButton.setIcon(FontIcon.of(Octicons.X, DarculaUiColors.LIGHT_GRAY));
        add(btnNewButton, "4, 10, fill, default");

        btnX = new JButton("X");
        btnX.setFont(new Font("Dialog", Font.BOLD, 9));
        add(btnX, "6, 10, fill, default");

        btnX_1 = new JButton("X");
        btnX_1.setFont(new Font("Dialog", Font.BOLD, 9));
        add(btnX_1, "8, 10, fill, default");

        btnX_2 = new JButton("X");
        btnX_2.setFont(new Font("Dialog", Font.BOLD, 9));
        add(btnX_2, "10, 10, fill, default");

        btnX_3 = new JButton("X");
        btnX_3.setFont(new Font("Dialog", Font.BOLD, 9));
        add(btnX_3, "12, 10, fill, default");

        btnX_4 = new JButton("X");
        btnX_4.setFont(new Font("Dialog", Font.BOLD, 9));
        add(btnX_4, "14, 10, fill, default");

        btnNewButton_1 = new JButton("+");
        btnNewButton_1.setFont(new Font("Dialog", Font.BOLD, 9));
        btnNewButton_1.setIcon(null);
        add(btnNewButton_1, "16, 10, center, default");

    }

}
