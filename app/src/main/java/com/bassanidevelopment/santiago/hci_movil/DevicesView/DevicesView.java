package com.bassanidevelopment.santiago.hci_movil.DevicesView;

import android.content.Context;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.DevicesAPI;

import org.json.JSONObject;

import java.util.Map;

public abstract class DevicesView {
    // common to all
    protected  String devId;
    protected Context context;

    protected   int getCurrentOption(String [] arr, String currentOption){
        int option = -1;
        System.out.println(currentOption);
        for(int i =0; i < arr.length; i++){
            if(arr[i].equals(currentOption))
                option = i;
        }
        return  option;
    }

    protected   void updateStatus(String action,Map<String,String> param){
        Callback callback = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                //setState(devId);
                return  true;
            }

            @Override
            public void showSpinner() {

            }

            @Override
            public void hideSpinner() {

            }
        };

        DevicesAPI.deviceAction(this.context,callback,devId, action, param);

    }
}
