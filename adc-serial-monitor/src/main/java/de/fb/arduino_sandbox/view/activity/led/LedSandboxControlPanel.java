package de.fb.arduino_sandbox.view.activity.led;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

@Component
public class LedSandboxControlPanel extends JPanel {

    private JButton loadButton;
    private JButton saveButton;
    private JButton resetButton;

    private LedSandboxController controller;

    @Autowired
    public LedSandboxControlPanel(final LedSandboxController controller) {
        super();
        this.controller = controller;
        initUI();
        connectEvents();
    }

    private void initUI() {
        setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder(EtchedBorder.LOWERED, null, null)));
        setLayout(new FormLayout(new ColumnSpec[] {
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
                RowSpec.decode("default:grow"),
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
                FormSpecs.DEFAULT_ROWSPEC,
                FormSpecs.RELATED_GAP_ROWSPEC,
        }));

        resetButton = new JButton("Reset LEDs");
        add(resetButton, "2, 2");

        loadButton = new JButton("Load configuration...");
        add(loadButton, "2, 18");

        saveButton = new JButton("Save configuration...");
        add(saveButton, "2, 20");
    }

    private void connectEvents() {

        loadButton.addActionListener(event -> openLoadDialog());
        saveButton.addActionListener(event -> openSaveDialog());
        resetButton.addActionListener(event -> controller.resetLedConfiguration());
    }

    private void openLoadDialog() {

        final JFileChooser chooser = new JFileChooser();
        final FileNameExtensionFilter extFilter = new FileNameExtensionFilter("YAML files", "yml", "yaml");
        chooser.setFileFilter(extFilter);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            controller.loadConfiguration(chooser.getSelectedFile());
        }
    }

    private void openSaveDialog() {

        final JFileChooser chooser = new JFileChooser();
        final FileNameExtensionFilter extFilter = new FileNameExtensionFilter("YAML files", "yml", "yaml");
        chooser.setFileFilter(extFilter);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            controller.saveConfiguration(chooser.getSelectedFile());
        }
    }
}
