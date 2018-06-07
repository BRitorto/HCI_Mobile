package com.bassanidevelopment.santiago.hci_movil.Model;

public class SimpleList {
    private String name;
    private String id;
    public SimpleList(String n, String id){
        name = n;
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
