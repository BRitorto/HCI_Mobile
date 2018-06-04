package com.bassanidevelopment.santiago.hci_movil.Model;


import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class Device {
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

    public void setTypeId(String typeId) {
        this.typeId = typeId;
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
}
