package com.bassanidevelopment.santiago.hci_movil.Model;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Device  implements APIObject{
    private String name;
    private String typeId;
    private String id;
    private JSONObject meta;

    public Device(JSONObject object) throws JSONException {
        Gson gson = new Gson();
        this.name = object.getString("name");
        this.id = object.getString("id");
        this.typeId = object.getString("typeId");
        String metaString = object.getString("meta");
        this.meta =  gson.fromJson(metaString,JSONObject.class);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JSONObject getMeta() {
        return meta;
    }

    public void setMeta(JSONObject meta) {
        this.meta = meta;
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, typeId, id, meta);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;
        Device device = (Device) o;
        return Objects.equals(getName(), device.getName()) &&
                Objects.equals(getTypeId(), device.getTypeId()) &&
                Objects.equals(getId(), device.getId()) &&
                getMeta().equals(device.getMeta());
    }


    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("name", getName());
        object.put("id",getId());
        object.put("meta",getMeta());
        object.put("typeId", getTypeId());
        return object;
    }
}
