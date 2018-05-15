package de.fb.arduino_sandbox.view.component.color;

import java.awt.Color;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Jackson deserializer for java.awt.Color
 * 
 *
 */
public class ColorDeserializer extends StdDeserializer<Color> {

    private static final String RED = "red";
    private static final String GREEN = "green";
    private static final String BLUE = "blue";
    private static final String ALPHA = "alpha";

    public ColorDeserializer() {
        super(Color.class);
    }

    @Override
    public Color deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

        Color result = null;
        final JsonNode node = jp.getCodec().readTree(jp);
        if (node != null) {

            final int red = node.get(RED).numberValue().intValue();
            final int green = node.get(GREEN).numberValue().intValue();
            final int blue = node.get(BLUE).numberValue().intValue();

            int alpha = 255;
            if (node.get(ALPHA) != null) {
                alpha = node.get(ALPHA).numberValue().intValue();
            }
            result = new Color(red, green, blue, alpha);
        }
        return result;
    }
}
