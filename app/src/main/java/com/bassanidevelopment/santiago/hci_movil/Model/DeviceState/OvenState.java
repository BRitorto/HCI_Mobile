package com.bassanidevelopment.santiago.hci_movil.Model.DeviceState;

public class OvenState {

    private int temperature;
    private boolean status;
    private String heat;
    private String grill;
    private String convection;

    public OvenState(int temperature, boolean status, String heat, String grill, String convection) {
        this.temperature = temperature;
        this.status = status;
        this.heat = heat;
        this.grill = grill;
        this.convection = convection;
    }

    public enum Heat {conventional,top,bottom}
    public enum Grill {large, eco, off}
    public enum Convection {normal, eco, off}

    public static Heat convertChoiceToHeat(int choice){
        switch (choice){
            case 0:
                return Heat.conventional;
            case 1:
                return Heat.top;

            default:
                return  Heat.bottom;
        }
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getHeat() {
        return heat;
    }

    public void setHeat(String heat) {
        this.heat = heat;
    }

    public String getGrill() {
        return grill;
    }

    public void setGrill(String grill) {
        this.grill = grill;
    }

    public String getConvection() {
        return convection;
    }

    public void setConvection(String convection) {
        this.convection = convection;
    }
}
