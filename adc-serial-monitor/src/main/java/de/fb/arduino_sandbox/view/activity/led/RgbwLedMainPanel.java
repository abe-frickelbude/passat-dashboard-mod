package de.fb.arduino_sandbox.view.activity.led;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.springframework.stereotype.Component;
import de.fb.arduino_sandbox.view.component.color.RgbwLedGroupController;

@Component
public class RgbwLedMainPanel extends JPanel {

    final RgbwLedGroupController ledGroupController;

    public RgbwLedMainPanel() {

        super();
        setLayout(new BorderLayout());

        ledGroupController = new RgbwLedGroupController();
        final JScrollPane scrollPane = new JScrollPane(ledGroupController);
        this.add(scrollPane, BorderLayout.CENTER);
    }

}
