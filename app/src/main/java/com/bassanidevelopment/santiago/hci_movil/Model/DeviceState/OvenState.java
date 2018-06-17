package com.bassanidevelopment.santiago.hci_movil.Model.DeviceState;

public class OvenState {

    private int temperature;
    private boolean status;
    private Heat heat;
    private Grill grill;
    private Convection convection;

    public OvenState(int temperature, boolean status, Heat heat, Grill grill, Convection convection) {
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




}
