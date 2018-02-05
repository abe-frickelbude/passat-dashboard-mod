package de.fb.adc_monitor.view.component;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import org.apache.commons.io.IOUtils;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Static information display dialog used by {@linkplain JColorSwatch} to provide detailed information about the current
 * color. Multiple representations are provided for convenience, and the entire contents can be copied as text to the system
 * clipboard if necessary.
 * 
 * @author Ibragim Kuliev
 *
 */
public class ColorSwatchInfoDialog extends JDialog {

    private static final String FLOAT_FIELD_TEMPLATE = "{0,number,#.###}";
    private static final String CLIPBOARD_DATA_TEMPLATE = "color_swatch_clipboard_template.txt";

    private static final float WIDTH_ADJUSTMENT_FACTOR = 1.1f;

    private Color color;

    private JPanel contentPane;

    private JTextField redField;
    private JTextField greenField;
    private JTextField blueField;
    private JTextField alphaField;
    private JTextField rgbaField;
    private JTextField rgbField;
    private JTextField hexField;
    private JTextField hsbField;
    private JTextField redFloatField;
    private JTextField greenFloatField;
    private JTextField blueFloatField;
    private JTextField alphaFloatField;
    private JTextField rgbaFloatField;
    private JTextField rgbFloatField;
    private JTextField hueAngleField;
    private JTextField hueAngleRadianField;

    private JButton clipboardButton;

    public ColorSwatchInfoDialog(final JFrame owner, final Color color) {

        super(owner);
        this.color = color;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        configureUI();
        registerEvents();
        updateValues();
    }

    public void setColor(final Color color) {
        this.color = color;
        updateValues();
    }

    @Override
    public void setVisible(final boolean visible) {

        // without pack the dialog will not be displayed correctly
        pack();

        // slightly increase the initial dialog width to accommodate for display of "long" values
        final Rectangle bounds = getBounds();
        final int width = Math.round(WIDTH_ADJUSTMENT_FACTOR * bounds.width);
        setBounds(bounds.x, bounds.y, width, bounds.height);

        setLocationRelativeTo(getParent());
        setResizable(false);
        setModal(true);
        super.setVisible(visible);
    }

    private void updateValues() {

        // ---------------------------------- RGBA --------------------------------------------------------

        float scale = 1.0f / 255.0f;

        int r = color.getRed(), g = color.getGreen(), b = color.getBlue(), a = color.getAlpha();
        float rr = r * scale, gg = g * scale, bb = b * scale, aa = a * scale;

        redField.setText(String.valueOf(r));
        redFloatField.setText(MessageFormat.format(FLOAT_FIELD_TEMPLATE, rr));

        greenField.setText(String.valueOf(g));
        greenFloatField.setText(MessageFormat.format(FLOAT_FIELD_TEMPLATE, gg));

        blueField.setText(String.valueOf(b));
        blueFloatField.setText(MessageFormat.format(FLOAT_FIELD_TEMPLATE, bb));

        alphaField.setText(String.valueOf(a));
        alphaFloatField.setText(MessageFormat.format(FLOAT_FIELD_TEMPLATE, aa));

        rgbaField.setText(MessageFormat.format("({0}, {1}, {2}, {3})", r, g, b, a));

        rgbaFloatField.setText(
            MessageFormat.format("({0,number,#.###}, {1,number,#.###}, {2,number,#.###}, {3,number,#.###})",
                rr, gg, bb, aa));

        rgbField.setText(MessageFormat.format("({0}, {1}, {2})", r, g, b));
        rgbFloatField.setText(
            MessageFormat.format("({0,number,#.###}, {1,number,#.###}, {2,number,#.###})", rr, gg, bb));

        // ----------------------------------- HEX -----------------------------------------------------

        hexField.setText(Integer.toHexString(color.getRGB()).toUpperCase());

        // ----------------------------------- HSB -----------------------------------------------------

        float hsbValues[] = Color.RGBtoHSB(r, g, b, null);
        hsbField.setText(MessageFormat.format("({0,number,#.###}, {1,number,#.###}, {2,number,#.###})",
            hsbValues[0], hsbValues[1], hsbValues[2]));

        hueAngleField.setText(MessageFormat.format("{0,number,#.###}\u00B0", 360.0f * hsbValues[0]));
        hueAngleRadianField.setText(MessageFormat.format("{0,number,#.###}", 2 * Math.PI * hsbValues[0]));
    }

    private void copyToClipboard() {

        final Clipboard clipboard = getToolkit().getSystemClipboard();
        if (clipboard != null) {
            try (final InputStream templateFile = getClass().getResourceAsStream(CLIPBOARD_DATA_TEMPLATE)) {

                // format data for clipboard transfer
                final String template = IOUtils.toString(templateFile, StandardCharsets.UTF_8);
                StringSelection clipboardData = new StringSelection(formatClipboardData(template));
                clipboard.setContents(clipboardData, null);

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
    }

    private String formatClipboardData(final String template) {

        return MessageFormat.format(template,
            redField.getText(), redFloatField.getText(),
            greenField.getText(), greenFloatField.getText(),
            blueField.getText(), blueFloatField.getText(),
            alphaField.getText(), alphaFloatField.getText(),
            rgbaField.getText(), rgbaFloatField.getText(),
            rgbField.getText(), rgbFloatField.getText(),
            hexField.getText(),
            hsbField.getText(),
            hueAngleField.getText(),
            hueAngleRadianField.getText());
    }

    private void registerEvents() {
        registerEscEvent();

        clipboardButton.addActionListener(event -> {
            copyToClipboard();
        });
    }

    private void registerEscEvent() {
        // Register ESC key to close the dialog
        KeyStroke cancelKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        InputMap inputMap = contentPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = contentPane.getActionMap();

        AbstractAction closeAction = new AbstractAction() {

            @Override
            public void actionPerformed(final ActionEvent event) {
                dispose();
            }
        };

        if (inputMap != null && actionMap != null) {
            inputMap.put(cancelKeyStroke, "cancel");
            actionMap.put("cancel", closeAction);
        }
    }

    private void configureUI() {

        contentPane = new JPanel();
        contentPane.setBorder(
            new CompoundBorder(
                new EmptyBorder(10, 10, 10, 10),
                new TitledBorder(
                    new LineBorder(null, 1, true),
                    "Color parameters",
                    TitledBorder.LEADING,
                    TitledBorder.TOP, null, null)));

        contentPane.setLayout(new FormLayout(new ColumnSpec[] {
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("default:grow"),
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("default:grow"),
            FormSpecs.RELATED_GAP_COLSPEC,
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
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
        }));

        JLabel lblNewLabel = new JLabel("As float \u2193");
        contentPane.add(lblNewLabel, "6, 2, right, default");

        JLabel redLabel = new JLabel("RED");
        contentPane.add(redLabel, "2, 4, left, default");

        redField = new JTextField();
        redField.setHorizontalAlignment(SwingConstants.TRAILING);
        redField.setEditable(false);
        contentPane.add(redField, "4, 4, fill, default");
        redField.setColumns(10);

        redFloatField = new JTextField();
        redFloatField.setHorizontalAlignment(SwingConstants.TRAILING);
        redFloatField.setEditable(false);
        contentPane.add(redFloatField, "6, 4, fill, default");
        redFloatField.setColumns(10);

        JLabel greenLabel = new JLabel("GREEN");
        contentPane.add(greenLabel, "2, 6, left, default");

        greenField = new JTextField();
        greenField.setHorizontalAlignment(SwingConstants.TRAILING);
        greenField.setEditable(false);
        contentPane.add(greenField, "4, 6, fill, default");
        greenField.setColumns(10);

        greenFloatField = new JTextField();
        greenFloatField.setHorizontalAlignment(SwingConstants.TRAILING);
        greenFloatField.setEditable(false);
        contentPane.add(greenFloatField, "6, 6, fill, default");
        greenFloatField.setColumns(10);

        final JLabel blueLabel = new JLabel("BLUE");
        contentPane.add(blueLabel, "2, 8, left, default");

        blueField = new JTextField();
        blueField.setHorizontalAlignment(SwingConstants.TRAILING);
        blueField.setEditable(false);
        contentPane.add(blueField, "4, 8, fill, default");
        blueField.setColumns(10);

        blueFloatField = new JTextField();
        blueFloatField.setHorizontalAlignment(SwingConstants.TRAILING);
        blueFloatField.setEditable(false);
        contentPane.add(blueFloatField, "6, 8, fill, default");
        blueFloatField.setColumns(10);

        final JLabel alphaLabel = new JLabel("ALPHA");
        contentPane.add(alphaLabel, "2, 10, left, default");

        alphaField = new JTextField();
        alphaField.setHorizontalAlignment(SwingConstants.TRAILING);
        alphaField.setEditable(false);
        contentPane.add(alphaField, "4, 10, fill, default");
        alphaField.setColumns(10);

        alphaFloatField = new JTextField();
        alphaFloatField.setHorizontalAlignment(SwingConstants.TRAILING);
        alphaFloatField.setEditable(false);
        contentPane.add(alphaFloatField, "6, 10, fill, default");
        alphaFloatField.setColumns(10);

        final JLabel rgbaLabel = new JLabel("RGBA");
        contentPane.add(rgbaLabel, "2, 12, left, default");

        rgbaField = new JTextField();
        rgbaField.setHorizontalAlignment(SwingConstants.TRAILING);
        rgbaField.setEditable(false);
        contentPane.add(rgbaField, "4, 12, fill, default");
        rgbaField.setColumns(10);

        rgbaFloatField = new JTextField();
        rgbaFloatField.setHorizontalAlignment(SwingConstants.TRAILING);
        rgbaFloatField.setEditable(false);
        contentPane.add(rgbaFloatField, "6, 12, fill, default");
        rgbaFloatField.setColumns(10);

        final JLabel rgbLabel = new JLabel("RGB");
        contentPane.add(rgbLabel, "2, 14, left, default");

        rgbField = new JTextField();
        rgbField.setHorizontalAlignment(SwingConstants.TRAILING);
        rgbField.setEditable(false);
        contentPane.add(rgbField, "4, 14, fill, default");
        rgbField.setColumns(10);

        rgbFloatField = new JTextField();
        rgbFloatField.setHorizontalAlignment(SwingConstants.TRAILING);
        rgbFloatField.setEditable(false);
        contentPane.add(rgbFloatField, "6, 14, fill, default");
        rgbFloatField.setColumns(10);

        final JLabel hexLabel = new JLabel("HEX (ARGB)");
        contentPane.add(hexLabel, "2, 16, left, default");

        hexField = new JTextField();
        hexField.setHorizontalAlignment(SwingConstants.TRAILING);
        hexField.setEditable(false);
        contentPane.add(hexField, "4, 16, fill, default");
        hexField.setColumns(10);

        final JLabel hsbLabel = new JLabel("HSB");
        contentPane.add(hsbLabel, "2, 18, left, default");

        hsbField = new JTextField();
        hsbField.setHorizontalAlignment(SwingConstants.TRAILING);
        hsbField.setEditable(false);
        contentPane.add(hsbField, "4, 18, fill, default");
        hsbField.setColumns(10);

        JLabel lblNewLabel_1 = new JLabel("In radian \u2193");
        contentPane.add(lblNewLabel_1, "6, 18, right, default");

        JLabel hueLabel = new JLabel("HUE ANGLE");
        contentPane.add(hueLabel, "2, 20, right, default");

        hueAngleField = new JTextField();
        hueAngleField.setHorizontalAlignment(SwingConstants.TRAILING);
        hueAngleField.setEditable(false);
        contentPane.add(hueAngleField, "4, 20, fill, default");
        hueAngleField.setColumns(10);

        setContentPane(contentPane);

        hueAngleRadianField = new JTextField();
        hueAngleRadianField.setHorizontalAlignment(SwingConstants.TRAILING);
        hueAngleRadianField.setEditable(false);
        contentPane.add(hueAngleRadianField, "6, 20, fill, default");
        hueAngleRadianField.setColumns(10);

        clipboardButton = new JButton("Copy all data to clipboard");
        contentPane.add(clipboardButton, "1, 24, 6, 1, center, default");
    }
}
