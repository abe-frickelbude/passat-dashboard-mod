package de.fb.arduino_sandbox.view.component.color;

import java.awt.Color;
import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * Jackson serializer for java.awt.Color
 * 
 *
 */
public class ColorSerializer extends StdSerializer<Color> {

    private static final String RED = "red";
    private static final String GREEN = "green";
    private static final String BLUE = "blue";
    private static final String ALPHA = "alpha";

    public ColorSerializer() {
        super(Color.class);
    }

    @Override
    public void serialize(final Color color, final JsonGenerator gen, final SerializerProvider serializers)
        throws IOException, JsonProcessingException {

        gen.writeStartObject();
        gen.writeNumberField(RED, color.getRed());
        gen.writeNumberField(GREEN, color.getGreen());
        gen.writeNumberField(BLUE, color.getBlue());
        gen.writeNumberField(ALPHA, color.getAlpha());
        gen.writeEndObject();
    }
}
