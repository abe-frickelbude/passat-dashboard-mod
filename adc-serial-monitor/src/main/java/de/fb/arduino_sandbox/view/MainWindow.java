package de.fb.arduino_sandbox.view;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.*;
import org.apache.commons.lang3.tuple.Pair;
import org.kordamp.ikonli.octicons.Octicons;
import org.kordamp.ikonli.swing.FontIcon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.jgoodies.forms.layout.*;
import de.fb.arduino_sandbox.controller.MainWindowController;
import de.fb.arduino_sandbox.util.RenderUtils;
import de.fb.arduino_sandbox.view.activity.Activity;
import de.fb.arduino_sandbox.view.component.JHeapMonitor;

@Component
public class MainWindow extends JFrame {

    @Autowired
    private MainWindowController controller;

    @Autowired
    private ApplicationContext appContext;

    private Map<Integer, Activity> activityMap;

    private JTabbedPane activityTabPane;
    private JHeapMonitor heapMonitorPanel;
    private JPanel controlPanel;

    private JButton connectButton;
    private JButton exitButton;

    /**
     * Create the frame.
     */
    public MainWindow() {

        super();
        activityMap = new HashMap<>();

        // default close does nothing to prevent accidentally shutting down the application accidentally
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        initializeUI();
    }

    @PostConstruct
    private void init() {
        connectEventHandlers();
    }

    private void createHeapMonitor() {

        heapMonitorPanel = new JHeapMonitor();
        heapMonitorPanel.setPreferredSize(new Dimension(496, 105));
        heapMonitorPanel.setBorder(new CompoundBorder(new EmptyBorder(5, 10, 10, 5),
            new TitledBorder(
                UIManager.getBorder("TitledBorder.border"), "Memory usage monitor",
                TitledBorder.LEADING, TitledBorder.TOP, null)));

        heapMonitorPanel.setEnabled(true);
    }

    private void initializeUI() {

        this.setTitle("Arduino Sandbox");

        // kustom icon for the window title bar!
        setIconImage(RenderUtils.renderFontIcon(FontIcon.of(Octicons.ZAP, DarculaUiColors.WHITE)));

        this.setBounds(100, 100, 776, 765);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel leftPane = new JPanel();
        this.getContentPane().add(leftPane, BorderLayout.CENTER);
        leftPane.setLayout(new BorderLayout(0, 0));

        createHeapMonitor();
        leftPane.add(heapMonitorPanel, BorderLayout.SOUTH);

        activityTabPane = new JTabbedPane(SwingConstants.TOP);
        activityTabPane.setBorder(new EmptyBorder(10, 10, 5, 5));
        leftPane.add(activityTabPane, BorderLayout.CENTER);

        createControlPanel();
        this.getContentPane().add(controlPanel, BorderLayout.EAST);
    }

    @PostConstruct
    private void initialize() {
        createTabs();
    }

    private void createTabs() {

        final Map<String, Activity> activities = appContext.getBeansOfType(Activity.class);
        for (Activity activity : activities.values()) {

            activityMap.put(activity.getTabIndex(), activity);
            activityTabPane.insertTab(activity.getTitle(),
                activity.getIcon(),
                activity.getMainPanel(),
                activity.getTooltip(),
                activity.getTabIndex());

        }
        activityTabPane.setSelectedIndex(0);
    }

    private void createControlPanel() {

        controlPanel = new JPanel();

        controlPanel.setLayout(new FormLayout(new ColumnSpec[] {
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("150px:grow"),
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
                RowSpec.decode("default:grow"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("default:grow"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("default:grow"),
                RowSpec.decode("43px"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("50px"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("32px"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("32px"),
                FormSpecs.RELATED_GAP_ROWSPEC,
        }));

        controlPanel.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10),
            new EtchedBorder(EtchedBorder.LOWERED, null, null)));

        exitButton = new JButton("Exit");
        exitButton.setIcon(FontIcon.of(Octicons.SIGN_OUT, DarculaUiColors.LIGHT_GRAY));

        connectButton = new JButton("Connect");
        connectButton.setIcon(FontIcon.of(Octicons.RADIO_TOWER, DarculaUiColors.LIGHT_GRAY));

        controlPanel.add(connectButton, "2, 21, fill, fill");
        controlPanel.add(exitButton, "2, 23, fill, fill");
    }

    private void connectEventHandlers() {

        connectButton.addActionListener(event -> {
            showSerialPortDialog();
        });

        exitButton.addActionListener(event -> {
            showExitDialog();
        });

        activityTabPane.addChangeListener(event -> {
            // show selected activity's controls in the control panel
            Activity activity = activityMap.get(activityTabPane.getSelectedIndex());
            controller.showActivityControlPanel(activity.getControlPanel());
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
                controller.connect(dialogResult.getLeft());
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
        Boolean exitAllowed = controller.requestAppExit();
        if (exitAllowed.booleanValue() == true) {

            int result = JOptionPane.showConfirmDialog(null,
                "Are you sure you want to exit the application?",
                "Confirm exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (result == JOptionPane.YES_OPTION) {
                controller.exitApp();
            }

        } else {
            JOptionPane.showMessageDialog(null,
                "Cannot exit at this time - please shut down first and wait for all pending tasks to complete!",
                "Exit not possible", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --------------- The following are event handlers used by the controller to update the main view ----------------

}
