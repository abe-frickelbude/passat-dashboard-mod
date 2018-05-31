package de.fb.arduino_sandbox.view.activity.led;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.fb.arduino_sandbox.model.RgbwPixel;
import de.fb.arduino_sandbox.service.HardwareUplink;
import de.fb.arduino_sandbox.view.component.color.RgbwColorGroup;
import de.fb.arduino_sandbox.view.component.color.RgbwColorGroups;

@Component
public class LedSandboxController {

    private static final Logger log = LoggerFactory.getLogger(LedSandboxController.class);

    private final ApplicationContext appContext;
    private final HardwareUplink hardwareUplink;
    private final ObjectMapper yamlMapper;

    private Supplier<RgbwColorGroups> configSupplier;
    private Consumer<RgbwColorGroups> configUpdater;

    @Autowired
    public LedSandboxController(final ApplicationContext appContext,
        final HardwareUplink arduinoLinkService,
        @Qualifier("yamlMapper") final ObjectMapper yamlMapper) {
        this.appContext = appContext;
        this.hardwareUplink = arduinoLinkService;
        this.yamlMapper = yamlMapper;
    }

    public void setConfigSupplier(final Supplier<RgbwColorGroups> callback) {
        this.configSupplier = callback;
    }

    public void setConfigUpdater(final Consumer<RgbwColorGroups> callback) {
        this.configUpdater = callback;
    }

    public void resetLedConfiguration() {
        hardwareUplink.resetPixels();
    }

    public void updateLedConfiguration(final RgbwColorGroups colorGroups) {

        final List<RgbwPixel> pixels = prepareLedData(colorGroups);
        hardwareUplink.sendRgbwPixels(pixels);
    }

    public void loadConfiguration(final File inputFile) {

        try {
            final RgbwColorGroups ledConfiguration = yamlMapper.readValue(inputFile, RgbwColorGroups.class);
            configUpdater.accept(ledConfiguration);
            log.info("Configuration loaded!");

        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    public void saveConfiguration(final File outputFile) {

        try {
            RgbwColorGroups ledConfiguration = configSupplier.get();
            String data = yamlMapper.writeValueAsString(ledConfiguration);
            FileUtils.writeStringToFile(outputFile, data, StandardCharsets.UTF_8);
            log.info("Configuration saved!");

        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

    private List<RgbwPixel> prepareLedData(final RgbwColorGroups groups) {

        final List<RgbwPixel> pixels = new ArrayList<>(groups.getGroups().size() * 2);

        for (RgbwColorGroup group : groups.getGroups()) {
            Color rgb = group.getColor();
            for (int i = 0; i < group.getGroupSize(); i++) {

                final RgbwPixel pixel = new RgbwPixel(
                    rgb.getRed(),
                    rgb.getGreen(),
                    rgb.getBlue(),
                    group.getLuminance());

                pixels.add(pixel);
            }
        }
        return pixels;
    }
}
