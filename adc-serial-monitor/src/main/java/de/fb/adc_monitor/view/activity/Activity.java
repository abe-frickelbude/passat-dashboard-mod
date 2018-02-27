package de.fb.adc_monitor.view.activity;

import javax.swing.*;

/**
 * 
 * @author Ibragim Kuliev
 *
 */
public interface Activity {

    String getTitle();

    Icon getIcon();

    String getTooltip();

    Integer getTabIndex();

    JPanel getControlPanel();

    JPanel getMainPanel();
}
