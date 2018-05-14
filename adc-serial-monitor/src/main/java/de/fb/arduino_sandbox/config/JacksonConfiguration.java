package de.fb.arduino_sandbox.config;

import java.awt.Color;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import de.fb.arduino_sandbox.view.component.color.ColorDeserializer;
import de.fb.arduino_sandbox.view.component.color.ColorSerializer;

@Configuration
public class JacksonConfiguration {

    @Bean(name = "yamlMapper")
    public ObjectMapper yamlMapper() {

        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        final SimpleModule module = new SimpleModule("kustom");
        module.addSerializer(new ColorSerializer());
        module.addDeserializer(Color.class, new ColorDeserializer());

        mapper.registerModule(module);
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        return mapper;
    }
}
