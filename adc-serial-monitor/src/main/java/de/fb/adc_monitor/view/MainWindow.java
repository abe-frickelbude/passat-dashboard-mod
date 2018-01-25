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
import org.apache.commons.lang3.tuple.Pair;
import org.kordamp.ikonli.octicons.Octicons;
import org.kordamp.ikonli.swing.FontIcon;
import org.springframework.beans.factory.annotation.Autowired;
import de.fb.adc_monitor.annotations.SwingView;
import de.fb.adc_monitor.controller.MainWindowController;
import de.fb.adc_monitor.util.RenderUtils;

@SwingView
public class MainWindow extends JFrame {

    // private static final Logger log = LoggerFactory.getLogger(MainWindow.class);

    @Autowired
    private MainWindowController controller;

    private JButton connectButton;
    private JButton stopButton;
    private JButton runButton;
    private JButton exitButton;

    /**
     * Create the frame.
     */
    public MainWindow() {
        super();
        // default close does nothing to prevent accidentally shutting down the application accidentally
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        initializeUI();
    }

    @PostConstruct
    private void init() {
        connectEventHandlers();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initializeUI() {

        this.setTitle("Arduino ADC Serial Monitor");

        // kustom icon for the window title bar!
        setIconImage(RenderUtils.renderFontIcon(FontIcon.of(Octicons.ZAP, DarculaUiColors.WHITE)));

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
        connectButton.setIcon(FontIcon.of(Octicons.RADIO_TOWER, DarculaUiColors.LIGHT_GRAY));

        runButton = new JButton("Start");
        stopButton = new JButton("Stop");

        exitButton = new JButton("Exit");
        exitButton.setIcon(FontIcon.of(Octicons.SIGN_OUT, DarculaUiColors.LIGHT_GRAY));

        GroupLayout gl_controlPanel = new GroupLayout(controlPanel);
        gl_controlPanel.setHorizontalGroup(
            gl_controlPanel.createParallelGroup(Alignment.TRAILING)
            .addGroup(gl_controlPanel.createSequentialGroup()
                .addContainerGap()
                .addGroup(gl_controlPanel.createParallelGroup(Alignment.LEADING)
                    .addComponent(connectButton, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(runButton, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(stopButton, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(exitButton, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addContainerGap()));
        gl_controlPanel.setVerticalGroup(
            gl_controlPanel.createParallelGroup(Alignment.LEADING)
            .addGroup(Alignment.TRAILING, gl_controlPanel.createSequentialGroup()
                .addContainerGap(487, Short.MAX_VALUE)
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

    private void connectEventHandlers() {

        connectButton.addActionListener(event -> {
            showSerialPortDialog();
        });

        runButton.addActionListener(event -> {

        });

        stopButton.addActionListener(event -> {

        });

        exitButton.addActionListener(event -> {
            showExitDialog();
        });
    }

    private void showSerialPortDialog() {

        List<String> portNames = controller.getAvailablePorts();
        if (!portNames.isEmpty()) {

            final SerialPortParameterDialog dialog = new SerialPortParameterDialog(this, controller.getAvailablePorts());
            dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            dialog.setModal(true);

            final Pair<SerialPortParams, Boolean> dialogResult = dialog.showDialog();
            if (dialogResult.getRight() == Boolean.TRUE) {
                controller.handleConnectEvent(dialogResult.getLeft());
            }

        } else {
            JOptionPane.showMessageDialog(null,
                "No serial ports detected!",
                "No serial ports",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showExitDialog() {

        // ask if it is OK to exit
        Boolean exitAllowed = controller.handleAppRequestExitEvent();
        if (exitAllowed.booleanValue() == true) {

            int result = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to exit the application?",
                "Confirm exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                controller.handleAppExitEvent();
            }

        } else {
            JOptionPane.showMessageDialog(null,
                "Cannot exit at this time - please shut down first and wait for all pending tasks to complete!",
                "Exit not possible", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --------------- The following are event handlers used by the controller to update the main view ----------------

}
