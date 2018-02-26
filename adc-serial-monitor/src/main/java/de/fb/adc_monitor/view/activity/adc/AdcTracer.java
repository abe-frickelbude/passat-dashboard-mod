package de.fb.adc_monitor.view.activity.adc;

import javax.swing.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.fb.adc_monitor.view.activity.Activity;

@Component
public class AdcTracer implements Activity {

    private final static String TITLE = "ADC Tracer";

    private final AdcTracerMainPanel mainPanel;
    private final AdcTracerControlPanel controlPanel;
    private final AdcTracerController controller;

    @Autowired
    public AdcTracer(final AdcTracerMainPanel mainPanel,
        final AdcTracerControlPanel controlPanel,
        final AdcTracerController controller) {

        this.mainPanel = mainPanel;
        this.controlPanel = controlPanel;
        this.controller = controller;

        // connect callbacks
        controller.setClearChartCallback(mainPanel::clearChart);
        controller.setUpdateChartCallback(mainPanel::updateChart);

        controller.setSelectFilterCallback(controlPanel::setFilterControlBox);
    }

    @Override
    public String getTitle() {
        return TITLE;
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
        return 0;
    }

    @Override
    public JPanel getControlPanel() {
        return controlPanel;
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }

}
