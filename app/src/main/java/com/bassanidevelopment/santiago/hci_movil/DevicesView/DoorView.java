package com.bassanidevelopment.santiago.hci_movil.DevicesView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.DevicesAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceState.AirConditionerState;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceState.DoorState;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DoorView extends DevicesView{

    //layout components

    private ToggleButton toggleDoor;
    private ToggleButton toggleLock;

    private DoorState state;

    public DoorView(View view, String devId, Context context) {
        this.devId = devId;
        this.context = context;

        // attach components

        toggleDoor = view.findViewById(R.id.toggle_door);
        toggleLock = view.findViewById(R.id.toggle_lock_door);

        setState(devId);
        setupListeners();
    }

    private void setState(String devId){

        Callback callback = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                try {

                    state = processStatus(response.getJSONObject("result"));
                    setLayoutDisplay();
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

        DevicesAPI.deviceAction(context, callback, devId, "getState",new HashMap<String, String>() );
    }


    private DoorState processStatus(JSONObject object){
        boolean status = false;
        String lock = "unlocked";

        try {
            lock = object.getString("lock");
            status = (object.getString("status").equals("opened"))? true : false;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new DoorState(status, lock);

    }



    public void setLayoutDisplay(){
        toggleDoor.setChecked(state.isStatus());
        toggleLock.setChecked(state.getLock().equals("locked"));
    }

    private void setupListeners(){
        toggleDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleDoor.setChecked(!state.isStatus());
                state.setStatus(!state.isStatus());
                String action = (state.isStatus())? "open" : "close";
                updateStatus(action, new HashMap<String, String>());
            }
        });

        toggleLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleLock.setChecked(!toggleLock.isChecked());
                state.setLock((toggleLock.isChecked())?"locked":"unlocked");
                String action = (toggleLock.isChecked())? "lock": "unlock";
                updateStatus(action, new HashMap<String, String>());
            }
        });
    }


}


