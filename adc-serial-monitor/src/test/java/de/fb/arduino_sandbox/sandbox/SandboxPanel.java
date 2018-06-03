package de.fb.arduino_sandbox.sandbox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import org.kordamp.ikonli.octicons.Octicons;
import org.kordamp.ikonli.swing.FontIcon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import de.fb.arduino_sandbox.view.component.color.TinyButton;
import de.fb.arduino_sandbox.view.component.color.TinyButtonGroup;
import de.fb.arduino_sandbox.view.component.color_swatch.ColorSwatch;
import de.fb.arduino_sandbox.view.component.dial.Dial;

public class SandboxPanel extends JPanel {

    private static final Logger log = LoggerFactory.getLogger(SandboxPanel.class);

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
    private JButton button;
    private ColorSwatch colorSwatch_1;
    private ColorSwatch colorSwatch_2;
    private ColorSwatch colorSwatch_3;
    private ColorSwatch colorSwatch_4;
    private ColorSwatch colorSwatch_5;
    private ColorSwatch colorSwatch_6;

    private TinyButton tinyButton;
    private ColorSwatch colorSwatch_7;
    private ColorSwatch colorSwatch_8;
    private TinyButton tinyButtonMinus;
    private TinyButton tinyButtonPlus;
    private TinyButtonGroup dualButton;
    private Dial dial;
    private Dial dial_1;
    private Dial dial_2;
    private Dial dial_3;

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
                RowSpec.decode("default:grow"),
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

        button = new JButton("+");
        button.setPreferredSize(new Dimension(28, 20));
        button.setMaximumSize(new Dimension(18, 18));
        button.setMinimumSize(new Dimension(18, 18));
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {}
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Dialog", Font.BOLD, 9));
        add(button, "4, 10, left, fill");

        btnX = new JButton("X");
        btnX.setFont(new Font("Dialog", Font.BOLD, 9));
        add(btnX, "6, 10, fill, default");

        btnX_1 = new JButton("X");
        btnX_1.setFont(new Font("Dialog", Font.BOLD, 9));
        add(btnX_1, "8, 10, fill, default");

        tinyButtonPlus = new TinyButton();
        tinyButtonPlus.setFontIcon(FontIcon.of(Octicons.PLUS));
        tinyButtonPlus.addActionCallback(() -> {
            log.info("plus clicked!");
        });

        // tinyButtonPlus.setSize(new Dimension(16, 16));
        // tinyButtonPlus.setPreferredSize(new Dimension(18, 18));
        // tinyButton_2.setForeground(UIManager.getColor("Button.darcula.selectedButtonForeground"));
        // tinyButton_2.setFont(new Font("Consolas", Font.BOLD, 11));
        tinyButtonPlus.setAlignmentX(Component.LEFT_ALIGNMENT);
        // tinyButton_2.setBackground(SystemColor.textInactiveText);
        add(tinyButtonPlus, "10, 10, center, center");

        tinyButton = new TinyButton();
        tinyButton.setFontIcon(FontIcon.of(Octicons.QUESTION));
        // tinyButton.setPreferredSize(new Dimension(18, 18));
        // tinyButton.setForeground(SystemColor.controlShadow);
        // tinyButton.setFont(new Font("Consolas", Font.BOLD, 11));
        // tinyButton.setLabel('-');
        // tinyButton.setBackground(SystemColor.textInactiveText);
        add(tinyButton, "11, 10");

        tinyButtonMinus = new TinyButton();
        tinyButtonMinus.setFontIcon(FontIcon.of(Octicons.PRIMITIVE_DOT));

        tinyButtonMinus.addActionCallback(() -> {
            log.info("minus clicked!");
        });

        // tinyButtonMinus.setSize(new Dimension(16, 16));
        // tinyButtonMinus.setPreferredSize(new Dimension(18, 18));
        // tinyButton_1.setForeground(SystemColor.controlShadow);
        // tinyButton_1.setLabel('+');
        // tinyButton_1.setFont(new Font("Consolas", Font.BOLD, 11));
        // tinyButton_1.setBackground(SystemColor.textInactiveText);
        add(tinyButtonMinus, "12, 10, center, center");

        btnX_4 = new JButton("X");
        btnX_4.setFont(new Font("Dialog", Font.BOLD, 9));
        add(btnX_4, "14, 10, fill, default");

        dualButton = new TinyButtonGroup();

        dualButton.addButton(FontIcon.of(Octicons.PLUS), () -> {
            log.info("plus clicked!");
        });

        dualButton.addButton(FontIcon.of(Octicons.DASH), () -> {
            log.info("minus clicked!");
        });

        add(dualButton, "16, 10, center, default");

        dial = new Dial();
        add(dial, "4, 13");

        dial_1 = new Dial();
        add(dial_1, "6, 13");

        dial_2 = new Dial();
        dial_2.setEnabled(false);
        add(dial_2, "8, 13");

        dial_3 = new Dial();
        dial_3.setValue(37);
        dial_3.setFineStep(1);
        dial_3.setCoarseStep(25);
        dial_3.setMax(255);
        dial_3.setMin(0);
        dial_3.setMinimumSize(new Dimension(48, 48));
        dial_3.setMaximumSize(new Dimension(48, 48));
        dial_3.setPreferredSize(new Dimension(48, 48));

        dial_3.addChangeCallback(value -> log.info("Dial value: {}", value));

        add(dial_3, "16, 15");

        colorSwatch_5.addChangeListener(event -> {

            final Color color = colorSwatch_5.getColor();
            colorSwatch_6.setColor(color);
        });

    }

}
