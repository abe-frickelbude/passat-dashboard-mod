package de.fb.arduino_sandbox.view.activity.led;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.fb.arduino_sandbox.view.component.color.RgbwColorGroups;
import de.fb.arduino_sandbox.view.component.color.RgbwLedGroupController;

@Component
public class LedSandboxMainPanel extends JPanel {

    final LedSandboxController controller;
    final RgbwLedGroupController ledGroupController;

    @Autowired
    public LedSandboxMainPanel(final LedSandboxController controller) {

        super();
        this.controller = controller;
        ledGroupController = new RgbwLedGroupController();

        initUI();
        connectEvents();
    }

    private void initUI() {

        setLayout(new BorderLayout(0, 0));
        final JScrollPane scrollPane = new JScrollPane(ledGroupController);
        scrollPane.setBorder(null);
        this.add(scrollPane);

        final java.awt.Component rigidAreaTop = Box.createRigidArea(new Dimension(20, 100));
        add(rigidAreaTop, BorderLayout.NORTH);
    }

    private void connectEvents() {
        ledGroupController.addChangeCallback(controller::updateLedConfiguration);
        controller.setConfigSupplier(ledGroupController::getColorGroups);
        controller.setConfigUpdater(this::updateLedConfiguration);

    }

    private void updateLedConfiguration(final RgbwColorGroups configuration) {
        ledGroupController.setColorGroups(configuration);
    }
}
