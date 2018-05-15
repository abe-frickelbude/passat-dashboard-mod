package de.fb.arduino_sandbox.view.component;

import static org.junit.Assert.*;
import java.awt.Color;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import de.fb.arduino_sandbox.view.component.color.ColorDeserializer;
import de.fb.arduino_sandbox.view.component.color.ColorSerializer;
import de.fb.arduino_sandbox.view.component.color.RgbwColorGroup;

public class ColorSerializationTest {

    private static final Logger log = LoggerFactory.getLogger(ColorSerializationTest.class);

    private ObjectMapper mapper;

    @Before
    public void setup() {

        mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule("kustom");
        module.addSerializer(new ColorSerializer());
        module.addDeserializer(Color.class, new ColorDeserializer());

        mapper.registerModule(module);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    @Test
    public void testCodecs() throws IOException {

        final RgbwColorGroup group = new RgbwColorGroup();
        group.setColor(new Color(123, 46, 98, 32));
        group.setLuminance(57);
        group.setGroupSize(17);

        String jsonData = mapper.writeValueAsString(group);
        log.info(jsonData);

        final RgbwColorGroup parsedGroup = mapper.readValue(jsonData, RgbwColorGroup.class);
        assertNotNull(parsedGroup);
        assertEquals(group.getColor(), parsedGroup.getColor());
        assertEquals(group.getLuminance(), parsedGroup.getLuminance());
        assertEquals(group.getGroupSize(), parsedGroup.getGroupSize());
    }
}
