package com.bassanidevelopment.santiago.hci_movil.API;

import android.content.Context;

import java.util.concurrent.Semaphore;

public class SingletonResponse {


    private static SingletonResponse responseHandler;
    private APIResponse response;
    private Semaphore mutex;
    public static  Context context;
    private  SingletonResponse() throws InterruptedException {
        mutex = new Semaphore(1,true);
        mutex.acquire();
    }

    public static synchronized SingletonResponse getInstance() throws InterruptedException {
        if(responseHandler == null){
            responseHandler = new SingletonResponse();
        }

        return responseHandler;
    }

    public Object getResponse() throws InterruptedException {
        this.mutex.acquire();
        Object resp = this.response.parseResponse();
        this.response = null;
        return  resp;
    }

    public void setResponse(APIResponse object){
        this.mutex.release();
        this.response = object;
    }

    public void setMutexOn(){
        this.mutex.release();
    }

}
