package de.fb.adc_monitor.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.apache.commons.collections4.CollectionUtils;
import org.kordamp.ikonli.octicons.Octicons;
import org.kordamp.ikonli.swing.FontIcon;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import de.fb.adc_monitor.util.RenderUtils;
import jssc.SerialPort;

public class SerialPortParameterDialog extends JDialog {

    private static final String ARDULINK_NOTE_TEXT = "<html>Note: Ardulink internally sets data bits to 8, <br/> stop bits to 1 and parity to NONE<html>";

    private final JComboBox<String> portNameComboBox;
    private final JComboBox<Integer> baudRateComboBox;

    /**
     * Create the dialog.
     */
    public SerialPortParameterDialog(final JFrame owner, final List<String> portNames) {

        super(owner);
        setBounds(100, 100, 310, 374);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // kustom icon for the window title bar!
        setIconImage(RenderUtils.renderFontIcon(FontIcon.of(Octicons.RADIO_TOWER, DarculaUiColors.WHITE)));

        final JPanel contentPanel = new JPanel();
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
            ColumnSpec.decode("150px"),
            ColumnSpec.decode("100px:grow"),
            FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
            ColumnSpec.decode("33px:grow"),
        },
            new RowSpec[] {
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.UNRELATED_GAP_ROWSPEC,
                RowSpec.decode("26px"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
        }));

        JLabel lblSpecifyParametersFor = new JLabel("Specify parameters for serial connection");
        contentPanel.add(lblSpecifyParametersFor, "1, 2, 3, 1");

        final JLabel lblPortLabel = new JLabel("Port");
        contentPanel.add(lblPortLabel, "1, 4, left, default");

        portNameComboBox = new JComboBox<>();
        contentPanel.add(portNameComboBox, "2, 4, fill, default");

        JLabel lblBaudRate = new JLabel("Baud rate");
        contentPanel.add(lblBaudRate, "1, 6, left, center");

        baudRateComboBox = new JComboBox<>();
        baudRateComboBox.setMinimumSize(new Dimension(100, 26));
        contentPanel.add(baudRateComboBox, "2, 6, fill, top");

        final JLabel noteLabel = new JLabel(ARDULINK_NOTE_TEXT);
        contentPanel.add(noteLabel, "1, 10, 2, 1, left, center");

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton okButton = new JButton("OK");
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);
        getRootPane().setDefaultButton(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);

        okButton.addActionListener(event -> {
            this.setVisible(false);
        });

        cancelButton.addActionListener(event -> {
            this.setVisible(false);
        });

        populatePortNameBox(portNames);
        populateBaudRateBox();
    }

    public SerialPortParams ask() {

        pack();
        setLocationRelativeTo(getParent());
        setVisible(true);
        dispose();
        return buildDataModel();
    }

    private SerialPortParams buildDataModel() {
        final SerialPortParams params = new SerialPortParams();
        params.setPortName(portNameComboBox.getItemAt(portNameComboBox.getSelectedIndex()));
        params.setBaudRate(baudRateComboBox.getItemAt(baudRateComboBox.getSelectedIndex()));
        return params;
    }

    private void populatePortNameBox(final List<String> portNames) {
        if (CollectionUtils.isNotEmpty(portNames)) {
            for (String portName : portNames) {
                portNameComboBox.addItem(portName);
            }
        }
    }

    // Uses constants from the JSCC library
    private void populateBaudRateBox() {
        baudRateComboBox.addItem(SerialPort.BAUDRATE_9600);
        baudRateComboBox.addItem(SerialPort.BAUDRATE_14400);
        baudRateComboBox.addItem(SerialPort.BAUDRATE_19200);
        baudRateComboBox.addItem(SerialPort.BAUDRATE_38400);
        baudRateComboBox.addItem(SerialPort.BAUDRATE_57600);
        baudRateComboBox.addItem(SerialPort.BAUDRATE_115200);
        baudRateComboBox.addItem(SerialPort.BAUDRATE_128000);
        baudRateComboBox.addItem(SerialPort.BAUDRATE_256000);
    }
}
