package com.bassanidevelopment.santiago.hci_movil.API;


import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bassanidevelopment.santiago.hci_movil.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class RoomsAPI {

    private static String BASE_URL = SingletonAPI.BASE_URL;

    /**
     * Retrieve all rooms
     */
    public static void getAllRooms(final Context context, final Callback callback) {
        callback.showSpinner();
        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + "rooms",
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("getAllRooms", response.toString());
                        Gson gson;
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        gson = gsonBuilder.create();

//                        RoomsState roomsState = gson.fromJson(response.toString(), RoomsState.class);
//
//                        Log.i("RoomsState", roomsState.toString());
//                        for (RoomsState.Room eachRoom : roomsState.getRooms()) {
//                            Log.i("Room", eachRoom.getName());
//                        }
                        System.out.println("hide spinner");
                        callback.hideSpinner();
                        callback.handleResponse(response);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("getAllRooms", "Error: " + error.getMessage());
                    }
                });

        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectReq, "getAllRooms");
    }

    /**
     * Creates a new room
     * @param name The new room name
     */
    public static void addRoom(final Context context, final String name, final Callback callback) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("name", name);
            jsonObject.accumulate("meta", "{}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.POST,
                BASE_URL + "rooms",
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("addRoom", response.toString());
                        callback.handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("addRoom", "Error: " + error.getMessage());
                        handleError(error, context);

                    }
                });

        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectReq, "addRoom");
    }

    /**
     * Delete an existing room
     * @param roomId The room id
     */
    public static void deleteRoom(Context context, String roomId, final Callback callback) {

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.DELETE,
                BASE_URL + "rooms/" + roomId,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("deleteRoom", response.toString());
                        callback.handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("deleteRoom", "Error: " + error.getMessage());
                    }
                });

        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectReq, "deleteRoom");
    }

    /**
     * Retrieve a specific room
     * @param roomId The room id
     */
    public static void getRoom(Context context, String roomId, final Callback callback) {

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.GET,
                BASE_URL + "rooms/" + roomId,
                new JSONObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("getRoom", response.toString());
                        callback.handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("getRoom", "Error: " + error.getMessage());
                    }
                });

        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectReq, "getRoom");
    }

    /**
     * Updates an existing room
     * @param roomId The room id
     * @param name The room new name
     */
    public static void updateRoom(Context context, String roomId, String name, final Callback callback) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("name", name);
            jsonObject.accumulate("meta", "{}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectReq = new JsonObjectRequest(Request.Method.PUT,
                BASE_URL + "rooms/" + roomId,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("updateRoom", response.toString());
                        callback.handleResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d("updateRoom", "Error: " + error.getMessage());
                    }
                });

        SingletonAPI.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectReq, "updateRoom");
    }


    private static void handleError(VolleyError error, Context context) {
        Error response = null;

        NetworkResponse networkResponse = error.networkResponse;
        if ((networkResponse != null) && (error.networkResponse.data != null)) {
            try {
                String json = new String(
                        error.networkResponse.data,
                        HttpHeaderParser.parseCharset(networkResponse.headers));

                JSONObject jsonObject = new JSONObject(json);
                json = jsonObject.getJSONObject("error").toString();

                Gson gson = new Gson();
                response = gson.fromJson(json, Error.class);
            } catch (JSONException e) {
            } catch (UnsupportedEncodingException e) {
            }
        }


        String text = context.getString(R.string.errorAddRoom);
        if (response != null)
            text += " " + response.getDescription().get(0);

        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
