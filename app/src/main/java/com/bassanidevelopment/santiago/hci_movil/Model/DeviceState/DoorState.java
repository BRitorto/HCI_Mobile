package com.bassanidevelopment.santiago.hci_movil.Model.DeviceState;

public class DoorState {
    private  boolean status;
    private  String lock;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public DoorState(boolean status, String lock) {

        this.status = status;
        this.lock = lock;
    }
}
