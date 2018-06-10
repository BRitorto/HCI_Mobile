package com.bassanidevelopment.santiago.hci_movil.Model;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceType implements APIObject {

    private String id;
    private String name;
    private JSONArray actions;

    public static final String TYPE_PREF = "current_type";

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


    public void setAsCurrenttype(Activity currentActivity, String  file_key){
        Context context = currentActivity;
        SharedPreferences sharedPref = context.getSharedPreferences(
                file_key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(TYPE_PREF, this.id);
        editor.commit();
    }

    public static String getCurrentType(Activity currentActivity, String file_key){
        String currentType;
        Context context = currentActivity;
        SharedPreferences sharedPref = context.getSharedPreferences(
                file_key, Context.MODE_PRIVATE);
        currentType = sharedPref.getString(TYPE_PREF, "none");
        return  currentType;
    }

    public JSONArray getActions() {
        return actions;
    }

    public void setActions(JSONArray actions) {
        this.actions = actions;
    }
}
