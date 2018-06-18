package com.bassanidevelopment.santiago.hci_movil.API;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONObject;

public class SingletonAPI {
    /**
     * To use in the Android Studio emulator use: http://10.0.2.2:8080/api/
     * To use in the Genymotion emulator use: http://10.0.2.3:8080/api/
     * To use in a real device, get your pc IP: http://<local-pc-ip>:8080/api/
     */
    public static String BASE_URL = "http://10.0.2.2:8080/api/";
    private static SingletonAPI mSingletonAPIInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private SingletonAPI(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized SingletonAPI getInstance(Context context) {
        if (mSingletonAPIInstance == null) {
            mSingletonAPIInstance = new SingletonAPI(context);
        }
        if(! haveNetworkConnection())
            Toast.makeText(getmContext(), getmContext().getString(R.string.networkError), Toast.LENGTH_LONG).show();
        return mSingletonAPIInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public static Context getmContext() {
        return mContext;
    }

    public static  boolean isError(JSONObject object){
        return  false;
    }


    private static boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getmContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
