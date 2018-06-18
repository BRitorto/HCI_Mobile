package com.bassanidevelopment.santiago.hci_movil.Model.DeviceState;

public class LampState {
    private boolean status;
    private String color;
    private  int brightness;

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public boolean isStatus() {

        return status;
    }

    public String getColor() {
        return color;
    }

    public int getBrightness() {
        return brightness;
    }

    public LampState(boolean status, String color, int brightness) {

        this.status = status;
        this.color = color;
        this.brightness = brightness;
    }

    @Override
    public String toString() {
        return "color:" + getColor().toString() + " brightness" + getBrightness() + "status: "+ isStatus();
    }
}
