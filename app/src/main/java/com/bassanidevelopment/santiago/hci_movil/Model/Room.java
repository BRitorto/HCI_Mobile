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

    public Room(APIController api, String name) throws JSONException {
        this.APIEndpoint = "rooms/";
        this.api = api;
        this.name = name;
        this.meta =  new JSONObject();
        JSONObject encodedJSON = new JSONObject();
        encodedJSON.put("name", this.name);
        encodedJSON.put("meta", this.meta.toString());
        JSONObject response = api.postRequest(this.APIEndpoint, encodedJSON);
        if(response == null)
        {
            System.out.println("CAUTION unknow error in the server!");
        }
        this.id = response.getJSONObject("room").getString("id");
    }


    public Room(JSONObject data, APIController api) throws JSONException
    {
        this.APIEndpoint = "rooms/";
        this.id = data.getString("id");
        this.name = data.getString("name");
        this.meta = data.getJSONObject("meta");
        this.api = api;
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


    public void update() throws JSONException {
        this.api.putRequest(this.APIEndpoint,convertToJSON());
        JSONObject response = this.api.getRequest(this.APIEndpoint+this.id);
        this.name = response.getJSONObject("room").getString("name");
        this.meta = response.getJSONObject("room").getJSONObject("meta");
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
