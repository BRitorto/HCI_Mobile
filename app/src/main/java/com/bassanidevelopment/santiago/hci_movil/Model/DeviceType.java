package com.bassanidevelopment.santiago.hci_movil.Model;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceType implements APIObject {

    private String id;
    private String name;
    private JSONArray actions;

    public DeviceType(JSONObject object) {

        try {
            this.id = object.getString("id");
            this.name = object.getString("name");
            this.actions = object.getJSONArray("actions");
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }


    @Override
    public JSONObject getMeta() {
        return null;
    }

    @Override
    public void setMeta(JSONObject meta) {

    }

    public JSONArray getActions() {
        return actions;
    }

    public void setActions(JSONArray actions) {
        this.actions = actions;
    }
}
