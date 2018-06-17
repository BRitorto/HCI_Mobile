package com.bassanidevelopment.santiago.hci_movil.Model.DeviceState;

public class BlindState {

    private boolean state;
    private int degreeofHieght;

    public BlindState(boolean state, int degreeofHieght){
        this.state = state;
        this.degreeofHieght = degreeofHieght;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getDegreeofHieght() {
        return degreeofHieght;
    }

    public void setDegreeofHieght(int degreeofHieght) {
        this.degreeofHieght = degreeofHieght;
    }
}
