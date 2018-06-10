package com.bassanidevelopment.santiago.hci_movil.Model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.bassanidevelopment.santiago.hci_movil.API.APIController;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Room implements APIObject {
    private String name;
    private String id;
    private JSONObject meta;
    private APIController api;
    private String APIEndpoint;
    public static final String ROOM_PREF = "current_room";

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



    public void setAsCurrentRoom(Activity currentActivity,String  file_key){
        Context context = currentActivity;
        SharedPreferences sharedPref = context.getSharedPreferences(
                file_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ROOM_PREF, this.id);
        editor.commit();
    }

    public static String getCurrentRoom(Activity currentActivity, String file_key){
        String currentRoom;
        Context context = currentActivity;
        SharedPreferences sharedPref = context.getSharedPreferences(
                file_key, Context.MODE_PRIVATE);
        currentRoom = sharedPref.getString(ROOM_PREF, "non");
        return  currentRoom;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id);
    }

}
