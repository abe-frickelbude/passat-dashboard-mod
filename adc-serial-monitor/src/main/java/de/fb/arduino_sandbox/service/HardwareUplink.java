package de.fb.arduino_sandbox.service;

import java.util.List;
import java.util.function.Consumer;
import de.fb.arduino_sandbox.model.RgbwPixel;
import de.fb.arduino_sandbox.view.SerialPortParams;

public interface HardwareUplink {

    List<String> getAvailablePorts();

    void connect(SerialPortParams params);

    void disconnect();

    void setAdcSampleConsumer(Integer pin, Consumer<Integer> consumer);

    void removeAdcSampleConsumer(Integer pin);

    void startListening(Integer pin);

    void stopListening(Integer pin);

    void stopAll();

    void sendRgbwPixels(List<RgbwPixel> pixels);

}