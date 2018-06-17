package com.bassanidevelopment.santiago.hci_movil.DevicesView;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.DevicesAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceState.BlindState;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class BlindView {

    private Button toggle;
    private String devId;
    private BlindState state;
    private Context context;
    private  boolean first;
    public BlindView(View view, String devId, Context context)
    {
        first = true;
        this.context = context;
        toggle = view.findViewById(R.id.toggle_blind);
        setState(devId);
        this.devId = devId;
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state.setState(!state.isState());
                updateState();
                if(toggle.getText().equals("up"))
                    toggle.setText("down");
                else
                    toggle.setText("up");
            }
        });
    }

    private void setState(String devId){

        Callback callback = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                try {
                    JSONObject status =  response.getJSONObject("result");
                    state = processStatus(status);
                    if(first){
                        setToggle();
                        first = false;
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


            DevicesAPI.deviceAction(context, callback, devId, "getState", new HashMap<String, String>());

    }

    private void setToggle(){
        if(state.isState()){
            toggle.setText("Down");
        }else
            toggle.setText("Up");
    }


    private BlindState processStatus(JSONObject status){
        boolean state = false;
        int height =  0;
        try {
            height = status.getInt("level");
            if(!status.getString("status").equals("closed"))
                state =  true;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new BlindState(state, height);

    }

    private  void updateState(){
        Callback callback = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                setState(devId);
                return  true;
            }

            @Override
            public void showSpinner() {

            }

            @Override
            public void hideSpinner() {

            }
        };
        String action;
        if(state.isState())
            action = "up";
        else
            action = "down";
        DevicesAPI.deviceAction(this.context, callback, devId, action, new HashMap<String, String>());

    }
}
