package de.fb.arduino_sandbox.sandbox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import de.fb.arduino_sandbox.view.component.color.BiColorSwatch;
import de.fb.arduino_sandbox.view.component.color.ColorSwatch;
import de.fb.arduino_sandbox.view.component.color.TinyButton;

public class SandboxPanel extends JPanel {

    private ColorSwatch colorSwatch;
    private JSpinner spinner;
    private JSpinner spinner_1;
    private JSpinner spinner_2;
    private JSpinner spinner_3;
    private JSpinner spinner_4;
    private JSpinner spinner_5;
    private JButton btnX;
    private JButton btnX_1;
    private JButton btnX_4;
    private JButton btnNewButton_1;
    private JButton button;
    private ColorSwatch colorSwatch_1;
    private ColorSwatch colorSwatch_2;
    private ColorSwatch colorSwatch_3;
    private ColorSwatch colorSwatch_4;
    private ColorSwatch colorSwatch_5;
    private ColorSwatch colorSwatch_6;
    private BiColorSwatch biColorSwatch;
    private BiColorSwatch biColorSwatch_1;
    private TinyButton tinyButton;
    private ColorSwatch colorSwatch_7;
    private ColorSwatch colorSwatch_8;
    private TinyButton tinyButton_1;
    private TinyButton tinyButton_2;

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
            FormSpecs.DEFAULT_COLSPEC,
            ColumnSpec.decode("48px"),
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("48px"),
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("100px"),
        },
            new RowSpec[] {
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("fill:48px"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("48px"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("100px"),
        }));

        colorSwatch = new ColorSwatch();
        colorSwatch.setBorderWidth(3);
        colorSwatch.setBorderEnabled(true);
        add(colorSwatch, "4, 4, fill, default");

        spinner = new JSpinner();
        spinner.setFont(new Font("Dialog", Font.PLAIN, 9));

        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 999, 1);
        spinner.setModel(model);

        colorSwatch_1 = new ColorSwatch();
        colorSwatch_1.setBorderWidth(2);
        colorSwatch_1.setCornerRadius(10);
        colorSwatch_1.setBorderEnabled(true);
        colorSwatch_1.setMaximumSize(new Dimension(32, 32));
        colorSwatch_1.setPreferredSize(new Dimension(32, 32));
        colorSwatch_1.setMinimumSize(new Dimension(32, 32));
        // colorSwatch_1.setSwatchSize(32);
        colorSwatch_1.setBorderColor(Color.WHITE);
        add(colorSwatch_1, "6, 4, fill, fill");

        colorSwatch_2 = new ColorSwatch();
        colorSwatch_2.setBorderWidth(2);
        colorSwatch_2.setPreferredSize(new Dimension(32, 32));
        colorSwatch_2.setMinimumSize(new Dimension(32, 32));
        colorSwatch_2.setMaximumSize(new Dimension(32, 32));
        colorSwatch_2.setBorderColor(Color.WHITE);
        add(colorSwatch_2, "8, 4, fill, fill");

        colorSwatch_4 = new ColorSwatch();
        colorSwatch_4.setColor(SystemColor.scrollbar);
        colorSwatch_4.setBorderWidth(2);
        colorSwatch_4.setBorderColor(SystemColor.activeCaption);
        colorSwatch_4.setBorderEnabled(true);
        add(colorSwatch_4, "10, 4, fill, fill");

        colorSwatch_6 = new ColorSwatch();
        colorSwatch_6.setBorderWidth(2);
        colorSwatch_6.setBorderEnabled(true);
        colorSwatch_6.setBorderColor(SystemColor.textText);
        add(colorSwatch_6, "12, 4");

        colorSwatch_5 = new ColorSwatch();
        colorSwatch_5.setCustomCursorEnabled(true);
        colorSwatch_5.setBorderEnabled(true);
        add(colorSwatch_5, "14, 4");

        biColorSwatch_1 = new BiColorSwatch();
        biColorSwatch_1.setBorderEnabled(true);
        add(biColorSwatch_1, "4, 6, fill, fill");

        colorSwatch_7 = new ColorSwatch();
        colorSwatch_7.setCustomCursorEnabled(true);
        colorSwatch_7.setBorderEnabled(true);
        add(colorSwatch_7, "10, 6, fill, fill");

        colorSwatch_8 = new ColorSwatch();
        colorSwatch_8.setCustomCursorEnabled(true);
        colorSwatch_8.setBorderEnabled(true);
        add(colorSwatch_8, "12, 6, default, fill");

        // colorSwatch_3 = new ColorSwatch();
        // add(colorSwatch_3, "10, 4");

        add(spinner, "4, 8, fill, default");

        spinner_1 = new JSpinner();
        spinner_1.setFont(new Font("Dialog", Font.PLAIN, 9));
        add(spinner_1, "6, 8, fill, fill");

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

        tinyButton_2 = new TinyButton();
        tinyButton_2.setBackground(SystemColor.textInactiveText);
        add(tinyButton_2, "10, 10, center, default");

        tinyButton = new TinyButton();
        tinyButton.setBackground(SystemColor.textInactiveText);
        add(tinyButton, "11, 10");

        tinyButton_1 = new TinyButton();
        tinyButton_1.setLabel('+');
        tinyButton_1.setBackground(SystemColor.textInactiveText);
        add(tinyButton_1, "12, 10, center, default");

        btnX_4 = new JButton("X");
        btnX_4.setFont(new Font("Dialog", Font.BOLD, 9));
        add(btnX_4, "14, 10, fill, default");

        btnNewButton_1 = new JButton("+");
        btnNewButton_1.setFont(new Font("Dialog", Font.BOLD, 9));
        btnNewButton_1.setIcon(null);
        add(btnNewButton_1, "16, 10, fill, fill");

        biColorSwatch = new BiColorSwatch();
        biColorSwatch.setBorderWidth(4);
        biColorSwatch.setBorderEnabled(true);
        add(biColorSwatch, "16, 15, fill, fill");

    }

}
