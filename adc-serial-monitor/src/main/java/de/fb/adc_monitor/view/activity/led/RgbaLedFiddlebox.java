package de.fb.adc_monitor.view.activity.led;

import javax.swing.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.fb.adc_monitor.view.activity.Activity;

@Component
public class RgbaLedFiddlebox implements Activity {

    private static final String TITLE = "WS2812 Fiddlebox";

    @Autowired
    public RgbaLedFiddlebox() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public JPanel getControlPanel() {
        return new JPanel();
    }

    @Override
    public JPanel getMainPanel() {
        return new JPanel();
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
