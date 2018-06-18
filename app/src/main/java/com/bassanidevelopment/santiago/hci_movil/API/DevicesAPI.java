package com.bassanidevelopment.santiago.hci_movil.API;
import android.content.Context;
import android.os.AsyncTask;
import android.os.PatternMatcher;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.bassanidevelopment.santiago.hci_movil.Model.Device;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DevicesAPI  {

    private static String BASE_URL = SingletonAPI.BASE_URL;
    private Context context;


    /**
     * Retrieve all devices
     */
    public static void getAllDevices(final Callback callbackAction, Context context) {
        callbackAction.showSpinner();
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + "devices",
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("getAllDevices", response.toString());
                        try {

                        callbackAction.handleResponse(response);
                        callbackAction.hideSpinner();
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

        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectReq, "getAllDevices");

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
            jsonObject.accumulate("meta", "{count:1}");
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
    public static void deleteDevice(Context context, String deviceId, final Callback callback) {

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.DELETE,
                BASE_URL + "devices/" + deviceId,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.handleResponse(response);
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
     *
     */
    public static void updateDevice(Context context, JSONObject dev) throws JSONException {



        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.PUT,
                BASE_URL + "devices/" + dev.getString("id"),
                dev,
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


    public static void deviceAction(Context context, final Callback callback, String devId, String action, final Map<String,String> params){

        final JSONObject param = new JSONObject();
        JSONArray array = new JSONArray();
        for(String key : params.keySet()){
            try {
                param.put(key, params.get(key));
                array.put(params.get(key));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        StringRequest stringRequest = new StringRequest(Request.Method.PUT,
                BASE_URL + "devices/" + devId + "/" + action,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject resp = new JSONObject();
                        JsonParser parser = new JsonParser();
                        JsonObject result =  (JsonObject) parser.parse(response);
                        try {
                            resp.put("result", result);
                            callback.handleResponse(resp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                 new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;

            }
        };


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.PUT,
                BASE_URL + "devices/" + devId + "/" + action,
                array,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject resp = new JSONObject();
                        try {
                            resp.put("result", resp);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        callback.handleResponse(resp);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error Device Action", "Event couldn't be reached");
                        String response = error.getMessage();
                        Pattern pattern = Pattern.compile(" \\{.*\\}");
                        Matcher matcher = pattern.matcher(response);
                        if(matcher.find()){
                            try {
                                JSONObject jsonObject = new JSONObject(matcher.group(0));
                                callback.handleResponse(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        System.out.println("that was an error");
                    }
                });

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT,
                BASE_URL + "devices/" + devId+ "/"+ action,
                param,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error Device Action","Event couldn't be reached");
                    }
                });

        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest, "deviceAction");

    }

    public static void checkDevicestatus(Context context,String id,  final  Callback callback ) throws JSONException {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + "devices/" + id + "/events",
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Event:", response.toString());
                        callback.handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error","Event couldn't be reached");
                    }
                });

        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest, "deviceEvents");
    }

    public static void getEvents(Context context, final Callback callback)throws JSONException{
        final StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + "devices/events",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);
                        JSONArray events = extractEvents(response);
                        JSONObject eventResponse = new JSONObject();
                        try {
                            eventResponse.put("events",events);
                            callback.handleResponse(eventResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error","Event couldn't be reached");
                    }
                });
        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(request, "deviceEvents");
    }

    public static void getDeviceEvents(Context context,String devId, final Callback callback)throws JSONException{
        final StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + "devices/"+devId+"events",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response",response);
                        JSONArray events = extractEvents(response);
                        JSONObject eventResponse = new JSONObject();
                        try {
                            eventResponse.put("events",events);
                            callback.handleResponse(eventResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error","Event couldn't be reached");
                    }
                });
        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(request, "deviceEvents");
    }

    public static JSONArray extractEvents(String responseStream){


        JSONArray arrayOfEvents = new JSONArray();
        List<String> responseLines = Arrays.asList(responseStream.split("\n"));
        int counter = 0;
        for(String line : responseLines){
            if(line.matches(".* \"event\": .*")){
                JsonObject event = new JsonObject();

                event.addProperty("event", line.substring(line.indexOf("\"event\"")));
                arrayOfEvents.put(event);
            }

        }
        System.out.println(responseStream.replaceAll("data:",""));
        return arrayOfEvents;

    }


    /**
     * Runs action in a specific device
     * @param deviceId The device id
     * @param actionName The action name to perform
     * @param body The action content, null if it's not required
     */
    public static void runActionInDevice(Context context, String deviceId, String actionName, String body, final Callback callback) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("body", body == null ? "[]" : body);
            jsonObject.accumulate("meta", "{}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.PUT,
                BASE_URL + "devices/" + deviceId + "/" + actionName,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("runActionInDevice", response.toString());
                        callback.handleResponse(response);
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
