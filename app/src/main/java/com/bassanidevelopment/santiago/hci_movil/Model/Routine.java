package com.bassanidevelopment.santiago.hci_movil.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Routine  implements APIObject{

    private String name;
    private String id;
    private JSONArray actions;

    public Routine(JSONObject object) throws JSONException {
        this.id = object.getString("id");
        this.name = object.getString("name");
        this.actions = object.getJSONArray("actions");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    @Override
    public JSONObject getMeta() {
        return null;
    }

    public void setId(String id) {
        this.id = id;
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
