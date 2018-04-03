package de.fb.arduino_sandbox.view.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.bulenkov.darcula.DarculaLaf;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.fb.arduino_sandbox.view.component.color.ColorDeserializer;
import de.fb.arduino_sandbox.view.component.color.ColorSerializer;
import de.fb.arduino_sandbox.view.component.color.RgbwColorGroups;
import de.fb.arduino_sandbox.view.component.color.RgbwLedGroupController;

public class LedGroupColorSwatchTest {

    private static final Logger log = LoggerFactory.getLogger(LedGroupColorSwatchTest.class);

    private static JButton saveButton;
    private static JButton loadButton;
    private static RgbwLedGroupController ledGroupSwitch;

    public static void main(final String[] args) {

        try {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(new DarculaLaf());
        } catch (Exception ex) {}

        final JFrame mainWindow = new JFrame();
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainWindow.setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout());

        final JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        saveButton = new JButton("Save");
        buttonPanel.add(saveButton);

        loadButton = new JButton("Load");
        buttonPanel.add(loadButton);

        connectEvents();

        ledGroupSwitch = new RgbwLedGroupController();

        final JScrollPane scrollPane = new JScrollPane(ledGroupSwitch);

        // contentPane.add(ledGroupSwitch, BorderLayout.CENTER);
        contentPane.add(scrollPane, BorderLayout.CENTER);

        mainWindow.setBounds(100, 100, 1024, 299);
        mainWindow.setVisible(true);
    }

    private static void connectEvents() {

        saveButton.addActionListener(event -> {
            saveConfiguration();
        });

        loadButton.addActionListener(event -> {
            loadConfiguration();
        });
    }

    private static void loadConfiguration() {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        SimpleModule module = new SimpleModule("kustom");
        module.addSerializer(new ColorSerializer());
        module.addDeserializer(Color.class, new ColorDeserializer());

        mapper.registerModule(module);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        try {

            final File file = new File("g:/temp/test.yml");
            final RgbwColorGroups data = mapper.readValue(file, RgbwColorGroups.class);
            log.info(data.toString());
            ledGroupSwitch.setColorGroups(data);

        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }

    }

    private static void saveConfiguration() {

        RgbwColorGroups colorGroups = ledGroupSwitch.getColorGroups();

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        SimpleModule module = new SimpleModule("kustom");
        module.addSerializer(new ColorSerializer());
        module.addDeserializer(Color.class, new ColorDeserializer());

        mapper.registerModule(module);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        try {
            String data = mapper.writeValueAsString(colorGroups);
            log.info(data);

            final File file = new File("g:/temp/test.yml");
            FileUtils.writeStringToFile(file, data, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        }
    }

}
