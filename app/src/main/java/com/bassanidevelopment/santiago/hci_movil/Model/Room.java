package com.bassanidevelopment.santiago.hci_movil.Model;

import com.bassanidevelopment.santiago.hci_movil.API.APIController;

import org.json.JSONException;
import org.json.JSONObject;

public class Room {
    private String name;
    private String id;
    private JSONObject meta;
    private APIController api;
    private String APIEndpoint;

    public Room(JSONObject data) throws JSONException
    {
        this.id = data.getString("id");
        this.name = data.getString("name");
        try {
            this.meta = data.getJSONObject("meta");
        }catch (JSONException e){
            this.meta = new JSONObject();
        }
    }

    public Room(String name){
        this.name = name;
    }

    public JSONObject convertToJSON() throws JSONException
    {
        JSONObject encodedJSON =  new JSONObject();
        encodedJSON.put("id", this.id);
        encodedJSON.put("name", this.name);
        encodedJSON.put("meta", this.meta);
        return encodedJSON;
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
