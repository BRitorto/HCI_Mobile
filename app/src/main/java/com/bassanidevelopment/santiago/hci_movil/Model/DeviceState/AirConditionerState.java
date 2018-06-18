package com.bassanidevelopment.santiago.hci_movil.Model.DeviceState;

public class AirConditionerState {

    private boolean status;
    private int temperature;
    private String mode;
    private String verticalSwing;
    private String horizontalString;
    private String speed;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getVerticalSwing() {
        return verticalSwing;
    }

    public void setVerticalSwing(String verticalSwing) {
        this.verticalSwing = verticalSwing;
    }

    public String getHorizontalString() {
        return horizontalString;
    }

    public void setHorizontalString(String horizontalString) {
        this.horizontalString = horizontalString;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public AirConditionerState(boolean status, int temperature, String mode, String verticalSwing, String horizontalString, String speed) {

        this.status = status;
        this.temperature = temperature;
        this.mode = mode;
        this.verticalSwing = verticalSwing;
        this.horizontalString = horizontalString;
        this.speed = speed;
    }
}
