package com.bassanidevelopment.santiago.hci_movil.API;
import android.content.Context;
import android.os.AsyncTask;
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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class DevicesAPI  {

    private static String BASE_URL = SingletonAPI.BASE_URL;
    private Context context;
    private  APIResponseHandler responseHanlder;

    public Object mlock = new Object();
    public final boolean[] finished = {false};

    public DevicesAPI(Context c, APIResponseHandler apiResponseHandler) {
        this.context = c;
        responseHanlder = apiResponseHandler;
    }

    /**
     * Retrieve all devices
     */
    public  void getAllDevices(final Callback callbackAction) {
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + "devices",
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("getAllDevices", response.toString());
                        try {
                            JSONArray devices = response.getJSONArray("devices");
                            final List<Device> deviceList = new ArrayList<>();
                            for(int i = 0; i < devices.length(); i++){
                                JSONObject device = devices.getJSONObject(i);
                                deviceList.add(new Device(device));
                            }
                            
                            if(callbackAction.storeResponse(deviceList)){
                                Log.d("API", "Reponse devices were retrieved");
                            }else{
                                Log.d("API", "there was a problem while executing callback action ");
                            }

                        }catch (Exception e){
                            Log.d("ERROR","something went wrong while retrieving all dev");
                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("getAllDevices", "Error: " + error.getMessage());
                    }
                });

        SingletonAPI.getInstance(this.context.getApplicationContext()).addToRequestQueue(jsonObjectReq, "getAllDevices");

    }


    public static Object getResponse() throws InterruptedException {
        SingletonResponse apiResponse = SingletonResponse.getInstance();
        return apiResponse.getResponse();
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
