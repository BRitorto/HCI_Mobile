package com.bassanidevelopment.santiago.hci_movil.Model.DeviceState;


public class RefrigeratorState   {

    private int temperature;
    private int freezerTemperature;
    private String mode;
    public enum Mode {vacation, party, def}


    public RefrigeratorState(int temperature, int freezerTemperature, String mode) {
        this.temperature = temperature;
        this.freezerTemperature = freezerTemperature;
        this.mode = mode;
    }



    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getFreezerTemperature() {
        return freezerTemperature;
    }

    public void setFreezerTemperature(int freezerTemperature) {
        this.freezerTemperature = freezerTemperature;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }


}