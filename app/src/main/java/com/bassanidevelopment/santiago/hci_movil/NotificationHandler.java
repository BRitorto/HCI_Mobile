package com.bassanidevelopment.santiago.hci_movil;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.DevicesAPI;
import com.bassanidevelopment.santiago.hci_movil.API.SingletonAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NotificationHandler extends BroadcastReceiver{
    /**
     * this class will be in charge of updating every device to see if there where changes to
     *  any of them
     * */

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        System.out.println("ALEEERT");
        searchForChanges();

    }


    private void searchForChanges(){
        final Callback checkEachDev = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                try {
                    JSONArray deviceArray = response.getJSONArray("devices");
                    for(int i  = 0; i < deviceArray.length(); i++){
                        checkEvents(deviceArray.getJSONObject(i).getString("id"));

                    }
                    return  true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return false;
            }

            @Override
            public void showSpinner() {

            }

            @Override
            public void hideSpinner() {

            }
        };

        //DevicesAPI.getAllDevices(checkEachDev,this.context);
        Callback callback_2 = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                //TODO
                // create a notification

                try {
                    JSONArray events = response.getJSONArray("events");

                    if(events.length() > 0)
                        Toast.makeText(context, response.get("events").toString(),
                            Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void showSpinner() {

            }

            @Override
            public void hideSpinner() {

            }
        };
        try {
            DevicesAPI.getEvents(context,callback_2 );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public  void checkEvents(String id){
        Callback callNotification = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                Log.d("Response to events", response.toString());
                return true;
            }

            @Override
            public void showSpinner() {

            }

            @Override
            public void hideSpinner() {

            }
        };
        try {
            DevicesAPI.checkDevicestatus(context, id, callNotification );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
