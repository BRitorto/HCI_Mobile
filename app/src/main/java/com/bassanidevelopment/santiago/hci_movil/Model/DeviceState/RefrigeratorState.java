package com.bassanidevelopment.santiago.hci_movil.Model.DeviceState;


public class RefrigeratorState   {

    private int temperature;
    private int freezerTemperature;
    private int mode;
    public enum Mode {vacation, party, def}


    public RefrigeratorState(int temperature, int freezerTemperature, Mode mode) {
        this.temperature = temperature;
        this.freezerTemperature = freezerTemperature;
        this.mode = getModeNumber(mode);
    }


    public  static Mode getModeFromString(String mode){

        switch (mode){
            case "vacation":
                return Mode.vacation;
            case "party":
                return Mode.party;
            default:
                return Mode.def;
        }
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

    public int getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = getModeNumber(mode);
    }

    private  int getModeNumber(Mode mode){
        switch (mode){
            case vacation:
                return 1;
            case party:
                return 2;
            default:
                return 3;
        }
    }
}