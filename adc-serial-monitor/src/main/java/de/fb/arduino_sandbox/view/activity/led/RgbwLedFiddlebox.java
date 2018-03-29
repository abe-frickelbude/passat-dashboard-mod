package de.fb.arduino_sandbox.view.activity.led;

import javax.swing.Icon;
import javax.swing.JPanel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.fb.arduino_sandbox.view.activity.Activity;

@Component
public class RgbwLedFiddlebox implements Activity {

    private static final String TITLE = "WS2812 Fiddlebox";

    private final RgbwLedMainPanel mainPanel;
    private final RgbwLedControlPanel controlPanel;

    @Autowired
    public RgbwLedFiddlebox(final RgbwLedMainPanel mainPanel, final RgbwLedControlPanel controlPanel) {
        this.mainPanel = mainPanel;
        this.controlPanel = controlPanel;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

    @Override
    public JPanel getControlPanel() {
        return controlPanel;
    }

    @Override
    public Icon getIcon() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTooltip() {
        return StringUtils.EMPTY;
    }

    @Override
    public Integer getTabIndex() {
        return 1;
    }
}
