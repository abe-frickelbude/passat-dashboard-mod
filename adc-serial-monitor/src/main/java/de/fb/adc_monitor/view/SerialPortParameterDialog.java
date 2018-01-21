package de.fb.adc_monitor.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

public class SerialPortParameterDialog extends JDialog {

    private static final Logger log = LoggerFactory.getLogger(SerialPortParameterDialog.class);

    private final JComboBox<Integer> baudRateComboBox;
    private final JComboBox<Integer> dataBitsComboBox;
    private final JComboBox<Integer> stopBitsComboBox;
    private final JComboBox<Integer> parityComboBox;

    /**
     * Create the dialog.
     */
    public SerialPortParameterDialog(final JFrame owner) {

        super(owner);
        setBounds(100, 100, 310, 374);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        final JPanel contentPanel = new JPanel();
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.NORTH);
        contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
            ColumnSpec.decode("150px"),
            ColumnSpec.decode("100px"),
            FormSpecs.LABEL_COMPONENT_GAP_COLSPEC,
            ColumnSpec.decode("33px:grow"),
        },
            new RowSpec[] {
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

        JLabel lblBaudRate = new JLabel("Baud rate");
        contentPanel.add(lblBaudRate, "1, 4, left, center");

        baudRateComboBox = new JComboBox<>();
        baudRateComboBox.setMinimumSize(new Dimension(100, 26));
        baudRateComboBox.setEditable(true);
        contentPanel.add(baudRateComboBox, "2, 4, fill, top");

        JLabel lblDataBits = new JLabel("Data bits");
        contentPanel.add(lblDataBits, "1, 6, left, default");

        dataBitsComboBox = new JComboBox<>();
        contentPanel.add(dataBitsComboBox, "2, 6, fill, default");

        JLabel lblStopBits = new JLabel("Stop bits");
        contentPanel.add(lblStopBits, "1, 8");

        stopBitsComboBox = new JComboBox<>();
        contentPanel.add(stopBitsComboBox, "2, 8, fill, default");

        JLabel lblParity = new JLabel("Parity");
        contentPanel.add(lblParity, "1, 10, left, default");

        parityComboBox = new JComboBox<>();
        contentPanel.add(parityComboBox, "2, 10, fill, default");

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

    }

    public SerialPortParams ask() {

        pack();
        setLocationRelativeTo(getParent());
        setVisible(true);
        dispose();

        log.info("Dialog closed!");
        return buildDataModel();
    }

    private SerialPortParams buildDataModel() {
        return new SerialPortParams();
    }
}
