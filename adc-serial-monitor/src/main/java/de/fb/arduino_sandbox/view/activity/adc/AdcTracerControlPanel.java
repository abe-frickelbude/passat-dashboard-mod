package de.fb.arduino_sandbox.view.activity.adc;

import java.awt.BorderLayout;
import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import de.fb.arduino_sandbox.math.FilterType;

@Component
public class AdcTracerControlPanel extends JPanel {

    private JSlider refreshRateSlider;
    private JTextField refreshRateField;

    private JLabel filterSelectLabel;
    private JComboBox<FilterType> filterSelectionBox;
    private JPanel filterControlPanel;

    private JCheckBox showRawInputTraceBox;
    private JCheckBox showInputTraceCheckBox;
    private JCheckBox showRmsTraceCheckBox;
    private JCheckBox showFilteredTraceCheckBox;
    private JCheckBox showMinTraceCheckBox;
    private JCheckBox showMaxTraceCheckBox;

    private JCheckBox preFilterThresholdCheckBox;
    private JCheckBox postFilterThresholdCheckBox;

    private JTextField thresholdValueField;
    private JLabel thresholdValueLabel;

    private JButton stopButton;
    private JButton startButton;

    private ApplicationContext appContext;

    @Autowired
    public AdcTracerControlPanel(final ApplicationContext appContext) {
        this.appContext = appContext;
        initUI();
    }

    public void setFilterControlBox(final JPanel controlBox) {
        if (controlBox != null) {
            filterControlPanel.removeAll();
            filterControlPanel.add(controlBox);
            filterControlPanel.revalidate();
            // filterControlPanel.repaint();
        }
    }

    private void initUI() {
        initPanel();
        createRefreshRateControls();
        createFilterSelector();
        createTraceOptionPanel();
        createButtons();
    }

    @PostConstruct
    private void init() {
        connectEvents();
    }

    private void connectEvents() {

        AdcTracerMainPanel mainPanel = appContext.getBean(AdcTracerMainPanel.class);
        AdcTracerController controller = appContext.getBean(AdcTracerController.class);

        refreshRateSlider.addChangeListener(event -> {
            refreshRateField.setText(String.valueOf(refreshRateSlider.getValue()));
            controller.setUpdateFrequency(refreshRateSlider.getValue());
        });

        filterSelectionBox.addActionListener(event -> {
            FilterType filterType = (FilterType) filterSelectionBox.getSelectedItem();
            controller.selectFilter(filterType);
        });

        showInputTraceCheckBox.addActionListener(event -> {
            mainPanel.setInputSignalTraceVisible(showInputTraceCheckBox.isSelected());
        });

        showFilteredTraceCheckBox.addActionListener(event -> {
            mainPanel.setFilteredSignalTraceVisible(showFilteredTraceCheckBox.isSelected());
        });

        showRmsTraceCheckBox.addActionListener(event -> {
            mainPanel.setRmsSignalTraceVisible(showRmsTraceCheckBox.isSelected());
        });

        showMinTraceCheckBox.addActionListener(event -> {
            mainPanel.setMinSignalTraceVisible(showMinTraceCheckBox.isSelected());
        });

        showMaxTraceCheckBox.addActionListener(event -> {
            mainPanel.setMaxSignalTraceVisible(showMaxTraceCheckBox.isSelected());
        });

        showRawInputTraceBox.addActionListener(event -> {
            mainPanel.setRawInputSignalTraceVisible(showRawInputTraceBox.isSelected());
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

        startButton.addActionListener(event -> {
            controller.start();
        });

        stopButton.addActionListener(event -> {
            controller.stop();
        });
    }

    private void initPanel() {

        this.setLayout(new FormLayout(new ColumnSpec[] {
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
                RowSpec.decode("top:pref:grow(3)"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                RowSpec.decode("default:grow"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
        }));

        this.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10),
            new EtchedBorder(EtchedBorder.LOWERED, null, null)));

    }

    private void createFilterSelector() {

        filterSelectLabel = new JLabel("Select filter");
        this.add(filterSelectLabel, "2, 8");

        filterSelectionBox = new JComboBox<>();
        filterSelectionBox.setModel(new DefaultComboBoxModel<>(FilterType.values()));
        filterSelectionBox.setEditable(false);

        this.add(filterSelectionBox, "2, 10, fill, default");

        filterControlPanel = new JPanel();
        this.add(filterControlPanel, "2, 12, fill, default");
        filterControlPanel.setLayout(new BorderLayout(0, 0));
    }

    private void createRefreshRateControls() {

        JLabel refreshRateLabel = new JLabel("Refresh rate");
        this.add(refreshRateLabel, "2, 2");

        refreshRateSlider = new JSlider();
        refreshRateSlider.setValue(10);
        refreshRateSlider.setMinimum(1);
        this.add(refreshRateSlider, "2, 4");

        refreshRateField = new JTextField();
        refreshRateField.setEditable(false);
        this.add(refreshRateField, "2, 6, fill, default");
        refreshRateField.setColumns(10);
    }

    private void createTraceOptionPanel() {

        final JPanel traceOptionPanel = new JPanel();
        this.add(traceOptionPanel, "2, 14, fill, fill");

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

        showRawInputTraceBox = new JCheckBox("Show raw input trace");
        traceOptionPanel.add(showRawInputTraceBox, "2, 14");
    }

    private void createButtons() {

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");

        this.add(startButton, "2, 16, fill, fill");
        this.add(stopButton, "2, 18, fill, fill");
    }
}
