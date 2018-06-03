package com.bassanidevelopment.santiago.hci_movil.API;


import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonAPI {
    /**
     * To use in the Android Studio emulator use: http://10.0.2.2:8080/api/
     * To use in the Genymotion emulator use: http://10.0.2.3:8080/api/
     * To use in a real device, get your pc IP: http://<local-pc-ip>:8080/api/
     */
    public static String BASE_URL = "http://192.168.1.41:8080/api/";
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
}
