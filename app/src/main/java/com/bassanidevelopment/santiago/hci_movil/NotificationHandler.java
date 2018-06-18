package com.bassanidevelopment.santiago.hci_movil;
import android.app.PendingIntent;
import android.support.v4.app.TaskStackBuilder;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.app.Notification;
import android.app.NotificationManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.DevicesAPI;
import com.bassanidevelopment.santiago.hci_movil.API.SingletonAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.ProcessNotification;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NotificationHandler extends BroadcastReceiver{
    /**
     * this class will be in charge of updating every device to see if there where changes to
     *  any of them
     * */

    private Context context;
    public static  final String CHANNEL_ID = "notificationBar";
    private static  int MY_NOTIFICATION_ID = 1;
    public static List<String> allowedTypes = new ArrayList();
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        populateNotification();


        searchForChanges();

    }


    private void searchForChanges(){
        final Callback checkEachDev = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                try {
                    JSONArray deviceArray = response.getJSONArray("devices");
                    for(int i  = 0; i < deviceArray.length(); i++){
                        if( allowedTypes.contains(deviceArray.getJSONObject(i).getString("typeId")))
                        checkEvents(deviceArray.getJSONObject(i).getString("id"),
                                deviceArray.getJSONObject(i).getString("name") );

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

        DevicesAPI.getAllDevices(checkEachDev,this.context);
        Callback callback_2 = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                //TODO
                // create a notification

                try {
                    JSONArray events = response.getJSONArray("events");

                    if(events.length() > 0)
                        notifyUser();
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
//        try {
//            DevicesAPI.getEvents(context,callback_2 );
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void notifyUser() {
        // Create the intent to start Activity when notification in action bar is
        // clicked.
        Intent notificationIntent = new Intent(this.context, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this.context);
        stackBuilder.addNextIntent(notificationIntent);
        // Create the pending intent granting the Operating System to launch activity
        // when notification in action bar is clicked.
        final PendingIntent contentIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


        Notification notification = new Notification.Builder(this.context)
                .setContentTitle(devName)
                .setContentText(notificationText)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setSmallIcon(android.R.drawable.stat_sys_download_done)
                .setContentIntent(contentIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Ignore deprecated warning. In newer devices SDK 16+ should use build() method.
        // getNotification() method internally calls build() method.
        notificationManager.notify(MY_NOTIFICATION_ID, notification);
        MY_NOTIFICATION_ID++;
    }

    public  void checkEvents(String id, final String name){
        Callback callNotification = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                Log.d("Response to events", response.toString());
                try {
                    if(response.getInt("events") > 0) {
                        devName = name;
                        notificationText = context.getString(R.string.notificationText);
                        notifyUser();
                    }
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
            DevicesAPI.getDeviceEvents(context, id, callNotification );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private  String getNotificationtext(String event){
        String text = "";
        Pattern  pattern = Pattern.compile("\"args\": \"\\{.*\\}");
        Matcher matcher = pattern.matcher(event);
        if(matcher.find())
            text =  matcher.group(0);

        return text;

    }

    private void populateNotification(){
        notificationMap = new HashMap<>();
        notificationMap.put("statusChanged", new ProcessNotification() {
            @Override
            public String process(Object object) {

                return null;
            }
        });
    }

    private Map<String, ProcessNotification> notificationMap;
    private  String notificationText = "";
    private String devName = "";
}
