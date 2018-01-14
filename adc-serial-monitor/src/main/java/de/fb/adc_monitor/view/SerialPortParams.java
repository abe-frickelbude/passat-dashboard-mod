package de.fb.adc_monitor.view;

public class SerialPortParams {

    private int baudRate;
    private int dataBits;
    private int stopBits;
    private int parity;

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(final int baudRate) {
        this.baudRate = baudRate;
    }

    public int getDataBits() {
        return dataBits;
    }

    public void setDataBits(final int dataBits) {
        this.dataBits = dataBits;
    }

    public int getStopBits() {
        return stopBits;
    }

    public void setStopBits(final int stopBits) {
        this.stopBits = stopBits;
    }

    public int getParity() {
        return parity;
    }

    public void setParity(final int parity) {
        this.parity = parity;
    }
}
