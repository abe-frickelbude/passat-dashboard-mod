package de.fb.adc_monitor.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import de.fb.adc_monitor.annotations.SwingView;
import de.fb.adc_monitor.controller.MainWindowController;
import de.fb.adc_monitor.service.SerialPortService;

@SwingView
public class MainWindow extends JFrame {

    private static final Logger log = LoggerFactory.getLogger(MainWindow.class);

    @Autowired
    private MainWindowController controller;

    @Autowired
    private SerialPortService serialPortService;

    private JButton connectButton;
    private JButton stopButton;
    private JButton runButton;
    private JButton exitButton;

    private JComboBox<String> portSelectionBox;

    /**
     * Create the frame.
     */
    public MainWindow() {
        super();
        // default close does nothing to prevent accidentally shutting down the application accidentally
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        initializeUI();
    }

    public void showSerialPortDialog() {

        final SerialPortParameterDialog dialog = new SerialPortParameterDialog(this);
        dialog.setModal(true);

        dialog.ask();
    }

    @PostConstruct
    private void init() {

        connectEventHandlers();
        populatePortSelectorBox(serialPortService.getPortNames());
    }

    /**
     * Initialize the contents of the frame.
     */
    @SuppressWarnings({
        "unchecked", "rawtypes"
    })
    private void initializeUI() {

        this.setTitle("Arduino ADC Serial Monitor");
        this.setBounds(100, 100, 776, 765);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel leftPane = new JPanel();
        this.getContentPane().add(leftPane, BorderLayout.CENTER);
        leftPane.setLayout(new BorderLayout(0, 0));

        HeapMonitorWidget heapMonitorPanel = new HeapMonitorWidget();
        heapMonitorPanel.setPreferredSize(new Dimension(496, 105));
        heapMonitorPanel.setBorder(new CompoundBorder(new EmptyBorder(5, 10, 10, 5),
            new TitledBorder(
                UIManager.getBorder("TitledBorder.border"), "Memory usage monitor",
                TitledBorder.LEADING, TitledBorder.TOP, null)));

        heapMonitorPanel.setEnabled(true);
        leftPane.add(heapMonitorPanel, BorderLayout.SOUTH);

        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder(EtchedBorder.LOWERED, null,
            null)));
        this.getContentPane().add(controlPanel, BorderLayout.EAST);

        connectButton = new JButton("Connect");
        runButton = new JButton("Start / Resume");
        stopButton = new JButton("Stop");
        exitButton = new JButton("Exit");

        portSelectionBox = new JComboBox<>();
        portSelectionBox.setModel(new DefaultComboBoxModel(new String[] {
            "No source selected"
        }));

        JLabel lblArduinoPort = new JLabel("Serial port");

        GroupLayout gl_controlPanel = new GroupLayout(controlPanel);
        gl_controlPanel.setHorizontalGroup(
            gl_controlPanel.createParallelGroup(Alignment.TRAILING)
            .addGroup(gl_controlPanel.createSequentialGroup()
                .addContainerGap()
                .addGroup(gl_controlPanel.createParallelGroup(Alignment.LEADING)
                    .addComponent(portSelectionBox, 0, 121, Short.MAX_VALUE)
                    .addComponent(lblArduinoPort)
                    .addComponent(connectButton, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(runButton, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(stopButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(exitButton, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addContainerGap()));
        gl_controlPanel.setVerticalGroup(
            gl_controlPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(gl_controlPanel.createSequentialGroup()
                .addGap(17)
                .addComponent(lblArduinoPort)
                .addPreferredGap(ComponentPlacement.UNRELATED)
                .addComponent(portSelectionBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                    GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED, 416, Short.MAX_VALUE)
                .addComponent(connectButton, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(runButton, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(stopButton, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(ComponentPlacement.RELATED)
                .addComponent(exitButton, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
                .addContainerGap()));
        controlPanel.setLayout(gl_controlPanel);
        heapMonitorPanel.setEnabled(true);
    }

    /**
     * All of the ugly glue logic with local listeners is here until I figure out a better way.
     */
    private void connectEventHandlers() {

        connectButton.addActionListener(event -> {
            showSerialPortDialog();
        });

        runButton.addActionListener(event -> {

        });

        stopButton.addActionListener(event -> {

        });

        exitButton.addActionListener(event -> {

            // ask if it is OK to exit
            Boolean exitAllowed = controller.handleAppRequestExitEvent(event);
            if (exitAllowed.booleanValue() == true) {

                int result = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to exit the application?",
                    "Confirm exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    controller.handleAppExitEvent(event);
                }

            } else {
                JOptionPane.showMessageDialog(null,
                    "Cannot exit at this time - please shut down first and wait for all pending tasks to complete!",
                    "Exit not possible", JOptionPane.ERROR_MESSAGE);
                // System.exit(0); // for testing
            }
        });

        portSelectionBox.addItemListener(event -> {
            // only send events for the >currently< selected item!
            if (event.getItem().equals(portSelectionBox.getSelectedItem())) {
                log.info("Selected port: {}", portSelectionBox.getSelectedItem());
            }
        });
    }

    // --------------- The following are event handlers used by the controller to update the main view ----------------

    private void populatePortSelectorBox(final List<String> portNames) {

        portSelectionBox.setEnabled(false);
        portSelectionBox.removeAllItems();

        for (String portName : portNames) {
            portSelectionBox.addItem(portName);
        }
        portSelectionBox.setEnabled(true);
    }
}
