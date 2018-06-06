package com.bassanidevelopment.santiago.hci_movil.API;

import android.util.Log;

import org.json.JSONObject;

import java.util.concurrent.Semaphore;

public class APIResponseHandler {
    private Object response;
    private Semaphore sem;
    public APIResponseHandler() {
        // TODO:
        //      should throw the exception up
        sem = new Semaphore(1, true);
        try {
            sem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Object getResponse(){
        try {
            sem.acquire();
            System.out.println("arquire sem");
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean setResponse(Object response){
        this.response = response;
        Log.d("Handler","the response was left ");
        sem.release();
        return  true;

    }
}
