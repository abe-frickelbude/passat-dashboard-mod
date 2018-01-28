package de.fb.adc_monitor.view.ansi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.Consumer;

/**
 * Small adapter class that automatically appends its content to the specified consumer
 * 
 */
class MessageOutputStream extends ByteArrayOutputStream {

    private final Consumer<String> consumer;

    public MessageOutputStream(final int initialSize, final Consumer<String> consumer) {
        super(initialSize);
        this.consumer = consumer;
    }

    @Override
    public void flush() throws IOException {
        this.consumer.accept((super.toString()));
        super.reset();
    }
}