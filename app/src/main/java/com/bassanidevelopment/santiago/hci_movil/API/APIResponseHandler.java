package com.bassanidevelopment.santiago.hci_movil.API;

import android.util.Log;

import org.json.JSONObject;

import java.util.concurrent.Semaphore;

public class APIResponseHandler {
    private Object response;

    public APIResponseHandler() {

    }

    public Object getResponse(){
         return response;
    }

    public Boolean setResponse(Object response){
        this.response = response;
        Log.d("Handler","the response was left ");
        //this.mutex.release();
        return  true;

    }
}
