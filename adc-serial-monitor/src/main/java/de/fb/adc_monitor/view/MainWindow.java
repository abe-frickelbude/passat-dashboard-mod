package de.fb.adc_monitor.view;

import java.awt.*;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.*;
import org.apache.commons.lang3.tuple.Pair;
import org.kordamp.ikonli.octicons.Octicons;
import org.kordamp.ikonli.swing.FontIcon;
import org.springframework.beans.factory.annotation.Autowired;
import com.jgoodies.forms.layout.*;
import de.fb.adc_monitor.annotations.SwingView;
import de.fb.adc_monitor.controller.MainWindowController;
import de.fb.adc_monitor.math.FilterType;
import de.fb.adc_monitor.util.*;
import de.fb.adc_monitor.view.component.*;
import info.monitorenter.gui.chart.ITrace2D;

@SwingView
public class MainWindow extends JFrame {

    @Autowired
    private MainWindowController controller;

    private JPanel controlPanel;
    private JHeapMonitor heapMonitorPanel;

    private JSlider refreshRateSlider;
    private JTextField refreshRateField;

    private JLabel filterSelectLabel;
    private JComboBox<FilterType> filterSelectionBox;
    private JPanel filterControlPanel;

    private JCheckBox showInputTraceCheckBox;
    private JCheckBox showRmsTraceCheckBox;
    private JCheckBox showFilteredTraceCheckBox;
    private JCheckBox showMinTraceCheckBox;
    private JCheckBox showMaxTraceCheckBox;

    private JCheckBox preFilterThresholdCheckBox;
    private JCheckBox postFilterThresholdCheckBox;
    private JTextField thresholdValueField;

    private JButton connectButton;
    private JButton stopButton;
    private JButton startButton;
    private JButton exitButton;

    private ZoomableChartView chartView;

    private ITrace2D inputSignalTrace;
    private ITrace2D filteredSignalTrace;
    private ITrace2D minSignalTrace;
    private ITrace2D maxSignalTrace;
    private ITrace2D rmsSignalTrace;
    private JLabel thresholdValueLabel;

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

    private void updateChart(final TraceData traceData) {

        SwingUtilities.invokeLater(() -> {
            inputSignalTrace.addPoint(traceData.getInputPoint());
            filteredSignalTrace.addPoint(traceData.getFilteredPoint());
            minSignalTrace.addPoint(traceData.getMinPoint());
            maxSignalTrace.addPoint(traceData.getMaxPoint());
            rmsSignalTrace.addPoint(traceData.getRmsPoint());
        });
    }

    private void clearChart() {
        inputSignalTrace.removeAllPoints();
        filteredSignalTrace.removeAllPoints();
    }

    private void createChart() {

        chartView = new ZoomableChartView();
        chartView.setXaxisTitle("TIME, s", Color.WHITE);
        chartView.setYaxisTitle("VOLTAGE, V", Color.WHITE);

        inputSignalTrace = chartView.addLtdTrace("input signal", Color.CYAN, Constants.NUM_GRAPH_DATA_POINTS);
        filteredSignalTrace = chartView.addLtdTrace("filtered signal", Color.YELLOW, Constants.NUM_GRAPH_DATA_POINTS);

        minSignalTrace = chartView.addLtdTrace("min", Color.GREEN, Constants.NUM_GRAPH_DATA_POINTS, 99);
        maxSignalTrace = chartView.addLtdTrace("max", Color.GREEN, Constants.NUM_GRAPH_DATA_POINTS, 99);
        rmsSignalTrace = chartView.addLtdTrace("RMS", Color.RED, Constants.NUM_GRAPH_DATA_POINTS, 99);

        minSignalTrace.setVisible(false);
        maxSignalTrace.setVisible(false);
        rmsSignalTrace.setVisible(false);
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

        this.setTitle("Arduino ADC Serial Monitor");

        // kustom icon for the window title bar!
        setIconImage(RenderUtils.renderFontIcon(FontIcon.of(Octicons.ZAP, DarculaUiColors.WHITE)));

        this.setBounds(100, 100, 776, 765);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel leftPane = new JPanel();
        this.getContentPane().add(leftPane, BorderLayout.CENTER);
        leftPane.setLayout(new BorderLayout(0, 0));

        createChart();
        leftPane.add(chartView, BorderLayout.CENTER);

        createHeapMonitor();
        leftPane.add(heapMonitorPanel, BorderLayout.SOUTH);

        createControlPanel();
        this.getContentPane().add(controlPanel, BorderLayout.EAST);

        createRefreshRateControls();
        createFilterSelector();
    }

    private void createFilterSelector() {

        filterSelectLabel = new JLabel("Select filter");
        controlPanel.add(filterSelectLabel, "2, 8");

        filterSelectionBox = new JComboBox<>();
        filterSelectionBox.setModel(new DefaultComboBoxModel<>(FilterType.values()));
        filterSelectionBox.setEditable(false);

        controlPanel.add(filterSelectionBox, "2, 10, fill, default");

        filterControlPanel = new JPanel();
        controlPanel.add(filterControlPanel, "2, 12, fill, fill");
        filterControlPanel.setLayout(new BorderLayout(0, 0));
    }

    private void createRefreshRateControls() {

        JLabel refreshRateLabel = new JLabel("Refresh rate");
        controlPanel.add(refreshRateLabel, "2, 2");

        refreshRateSlider = new JSlider();
        refreshRateSlider.setValue(10);
        refreshRateSlider.setMinimum(1);
        controlPanel.add(refreshRateSlider, "2, 4");

        refreshRateField = new JTextField();
        refreshRateField.setEditable(false);
        controlPanel.add(refreshRateField, "2, 6, fill, default");
        refreshRateField.setColumns(10);
    }

    private void createControlPanel() {

        controlPanel = new JPanel();

        controlPanel.setLayout(new FormLayout(new ColumnSpec[] {
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("400px:grow"),
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
                RowSpec.decode("46px"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("52px"),
                FormSpecs.RELATED_GAP_ROWSPEC,
        }));

        controlPanel.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10),
            new EtchedBorder(EtchedBorder.LOWERED, null, null)));

        connectButton = new JButton("Connect");
        connectButton.setIcon(FontIcon.of(Octicons.RADIO_TOWER, DarculaUiColors.LIGHT_GRAY));

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");

        exitButton = new JButton("Exit");
        exitButton.setIcon(FontIcon.of(Octicons.SIGN_OUT, DarculaUiColors.LIGHT_GRAY));

        createTraceOptionPanel();

        controlPanel.add(connectButton, "2, 17, fill, fill");
        controlPanel.add(startButton, "2, 19, fill, fill");
        controlPanel.add(stopButton, "2, 21, fill, fill");
        controlPanel.add(exitButton, "2, 23, fill, fill");
    }

    private void createTraceOptionPanel() {

        final JPanel traceOptionPanel = new JPanel();
        controlPanel.add(traceOptionPanel, "2, 14, fill, fill");
        traceOptionPanel.setLayout(new FormLayout(new ColumnSpec[] {
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            ColumnSpec.decode("default:grow"),
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
            FormSpecs.RELATED_GAP_COLSPEC,
            FormSpecs.DEFAULT_COLSPEC,
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
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
        }));

        showInputTraceCheckBox = new JCheckBox("Show input trace");
        showInputTraceCheckBox.setSelected(true);
        traceOptionPanel.add(showInputTraceCheckBox, "2, 4");

        preFilterThresholdCheckBox = new JCheckBox("Pre-filter threshold");
        traceOptionPanel.add(preFilterThresholdCheckBox, "8, 4");

        showRmsTraceCheckBox = new JCheckBox("Show RMS trace");
        traceOptionPanel.add(showRmsTraceCheckBox, "2, 6");

        postFilterThresholdCheckBox = new JCheckBox("Post-filter threshold");
        traceOptionPanel.add(postFilterThresholdCheckBox, "8, 6");

        showFilteredTraceCheckBox = new JCheckBox("Show filtered trace");
        showFilteredTraceCheckBox.setSelected(true);
        traceOptionPanel.add(showFilteredTraceCheckBox, "2, 8");

        thresholdValueField = new JTextField();
        thresholdValueField.setText(String.valueOf(4.8));
        traceOptionPanel.add(thresholdValueField, "8, 8, left, default");
        thresholdValueField.setColumns(10);

        thresholdValueLabel = new JLabel("mV");
        traceOptionPanel.add(thresholdValueLabel, "10, 8");

        showMinTraceCheckBox = new JCheckBox("Show MIN trace");
        traceOptionPanel.add(showMinTraceCheckBox, "2, 10");

        showMaxTraceCheckBox = new JCheckBox("Show MAX trace");
        traceOptionPanel.add(showMaxTraceCheckBox, "2, 12");
    }

    private void setFilterControlBox(final JPanel controlBox) {
        if (controlBox != null) {
            filterControlPanel.removeAll();
            filterControlPanel.add(controlBox, BorderLayout.CENTER);
            filterControlPanel.revalidate();
        }
    }

    private void connectEventHandlers() {

        controller.setUpdateChartCallback(this::updateChart);
        controller.setClearChartCallback(this::clearChart);
        controller.setSelectFilterCallback(this::setFilterControlBox);

        refreshRateSlider.addChangeListener(event -> {
            refreshRateField.setText(String.valueOf(refreshRateSlider.getValue()));
            controller.setUpdateFrequency(refreshRateSlider.getValue());
        });

        filterSelectionBox.addActionListener(event -> {
            FilterType filterType = (FilterType) filterSelectionBox.getSelectedItem();
            controller.selectFilter(filterType);
        });

        showInputTraceCheckBox.addActionListener(event -> {
            inputSignalTrace.setVisible(showInputTraceCheckBox.isSelected());
        });

        showFilteredTraceCheckBox.addActionListener(event -> {
            filteredSignalTrace.setVisible(showFilteredTraceCheckBox.isSelected());
        });

        showRmsTraceCheckBox.addActionListener(event -> {
            rmsSignalTrace.setVisible(showRmsTraceCheckBox.isSelected());
        });

        showMinTraceCheckBox.addActionListener(event -> {
            minSignalTrace.setVisible(showMinTraceCheckBox.isSelected());
        });

        showMaxTraceCheckBox.addActionListener(event -> {
            maxSignalTrace.setVisible(showMaxTraceCheckBox.isSelected());
        });

        preFilterThresholdCheckBox.addActionListener(event -> {
            controller.setUsePreFilterThreshold(preFilterThresholdCheckBox.isSelected());
        });

        postFilterThresholdCheckBox.addActionListener(event -> {
            controller.setUsePostFilterThreshold(postFilterThresholdCheckBox.isSelected());
        });

        thresholdValueField.addActionListener(event -> {
            // the input box value is in mV!
            double value = Double.parseDouble(thresholdValueField.getText()) / 1000.0;
            controller.setThresholdValue(value);
        });

        connectButton.addActionListener(event -> {
            showSerialPortDialog();
        });

        startButton.addActionListener(event -> {
            controller.start();
        });

        stopButton.addActionListener(event -> {
            controller.stop();
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
