package com.bassanidevelopment.santiago.hci_movil.API;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bassanidevelopment.santiago.hci_movil.Model.Device;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DevicesAPI {

    private static String BASE_URL = SingletonAPI.BASE_URL;

    /**
     * Retrieve all devices
     */
    public static void getAllDevices(Context context) {

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + "devices",
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("getAllDevices", response.toString());
                        try {
                            JSONArray devices = response.getJSONArray("devices");
                            for(int i = 0; i < devices.length(); i++){
                                JSONObject device = devices.getJSONObject(i);
                                }
                        }catch (Exception e){
                            Log.d("ERROR","something went wrong");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("getAllDevices", "Error: " + error.getMessage());
                    }
                });

        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectReq, "getAllDevices");
    }

    /**
     * Creates a new device
     * @param typeId The devices type id
     * @param name The device name
     */
    public static void createDevice(Context context, String typeId, String name) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("typeId", typeId);
            jsonObject.accumulate("name", name);
            jsonObject.accumulate("meta", "{}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST,
                BASE_URL + "devices",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("createDevice", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("createDevice", "Error: " + error.getMessage());
                    }
                });

        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectReq, "createDevice");
    }

    /**
     * Delete an existing device
     * @param deviceId The device id
     */
    public static void deleteDevice(Context context, String deviceId) {

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.DELETE,
                BASE_URL + "devices/" + deviceId,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("deleteDevice", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("deleteDevice", "Error: " + error.getMessage());
                    }
                });

        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectReq, "deleteDevice");
    }

    /**
     * Updates an existing device
     * @param typeId The device type id
     * @param name The device new name
     */
    public static void updateDevice(Context context, String typeId, String name) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("typeId", typeId);
            jsonObject.accumulate("name", name);
            jsonObject.accumulate("meta", "{}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.PUT,
                BASE_URL + "devices/" + typeId,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("updateDevice", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("updateDevice", "Error: " + error.getMessage());
                    }
                });

        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectReq, "updateDevice");
    }

    /**
     * Runs action in a specific device
     * @param deviceId The device id
     * @param actionName The action name to perform
     * @param body The action content, null if it's not required
     */
    public static void runActionInDevice(Context context, String deviceId, String actionName, String body) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("body", body == null ? "{}" : body);
            jsonObject.accumulate("meta", "{}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.PUT,
                BASE_URL + "devices/" + deviceId + "/" + actionName,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("runActionInDevice", response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("runActionInDevice", "Error: " + error.getMessage());
                    }
                });

        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectReq, "runActionInDevice");
    }
}
