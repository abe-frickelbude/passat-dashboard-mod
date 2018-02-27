package de.fb.arduino_sandbox.view;

public class SerialPortParams {

    private String portName;
    private int baudRate;

    public String getPortName() {
        return portName;
    }

    public void setPortName(final String portName) {
        this.portName = portName;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(final int baudRate) {
        this.baudRate = baudRate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("SerialPortParams [");
        if (portName != null) {
            builder.append("portName = ");
            builder.append(portName);
            builder.append(", ");
        }
        builder.append("baudRate = ");
        builder.append(baudRate);
        builder.append("]");
        return builder.toString();
    }
}
