package de.fb.arduino_sandbox.sandbox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import de.fb.arduino_sandbox.view.component.JColorSwatch;
import de.fb.arduino_sandbox.view.component.color.ColorSwatch;

public class SandboxPanel extends JPanel {

    private JColorSwatch colorSwatch;
    private JColorSwatch colorSwatch2;
    private JSpinner spinner;
    private JSpinner spinner_1;
    private JSpinner spinner_2;
    private JSpinner spinner_3;
    private JSpinner spinner_4;
    private JSpinner spinner_5;
    private JButton btnX;
    private JButton btnX_1;
    private JButton btnX_2;
    private JButton btnX_3;
    private JButton btnX_4;
    private JButton btnNewButton_1;
    private JButton button;
    private ColorSwatch colorSwatch_1;
    private ColorSwatch colorSwatch_2;
    private ColorSwatch colorSwatch_3;
    private ColorSwatch colorSwatch_4;

    /**
     * Create the panel.
     */
    public SandboxPanel() {
        setMaximumSize(new Dimension(32, 32));
        setLayout(new FormLayout(new ColumnSpec[] {
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("27dlu"),
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("48px"),
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("48px"),
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("48px"),
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
                RowSpec.decode("fill:48px"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("default:grow"),
        }));

        colorSwatch = new JColorSwatch.Builder().swatchSize(46).build();
        add(colorSwatch, "4, 4, center, default");

        colorSwatch2 = new JColorSwatch.Builder().swatchSize(46).build();
        add(colorSwatch2, "4, 6, center, default");

        spinner = new JSpinner();
        spinner.setFont(new Font("Dialog", Font.PLAIN, 9));

        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 999, 1);
        spinner.setModel(model);

        colorSwatch_1 = new ColorSwatch();
        colorSwatch_1.setCornerRadius(16);
        colorSwatch_1.setMaximumSize(new Dimension(32, 32));
        colorSwatch_1.setPreferredSize(new Dimension(32, 32));
        colorSwatch_1.setMinimumSize(new Dimension(32, 32));
        // colorSwatch_1.setSwatchSize(32);
        colorSwatch_1.setBorderColor(Color.WHITE);
        add(colorSwatch_1, "6, 4, fill, fill");

        colorSwatch_2 = new ColorSwatch();
        colorSwatch_2.setCornerRadius(10);
        colorSwatch_2.setBorderWidth(2);
        colorSwatch_2.setPreferredSize(new Dimension(32, 32));
        colorSwatch_2.setMinimumSize(new Dimension(32, 32));
        colorSwatch_2.setMaximumSize(new Dimension(32, 32));
        colorSwatch_2.setBorderColor(Color.WHITE);
        add(colorSwatch_2, "8, 4, fill, fill");

        colorSwatch_4 = new ColorSwatch();
        colorSwatch_4.setPreferredSize(new Dimension(20, 16));
        add(colorSwatch_4, "10, 4, center, fill");

        // colorSwatch_3 = new ColorSwatch();
        // add(colorSwatch_3, "10, 4");

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

        button = new JButton("X");
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Dialog", Font.BOLD, 9));
        add(button, "4, 10, center, fill");

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
