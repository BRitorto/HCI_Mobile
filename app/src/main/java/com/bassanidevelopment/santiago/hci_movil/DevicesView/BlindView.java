package com.bassanidevelopment.santiago.hci_movil.DevicesView;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.DevicesAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceState.BlindState;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class BlindView extends DevicesView{

    private ToggleButton toggle;
    private BlindState state;
    public BlindView(View view, String devId, Context context)
    {
        this.context = context;
        toggle = view.findViewById(R.id.toggle_blind);
        setState(devId);
        this.devId = devId;
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state.setState(!state.isState());
                toggle.setChecked(state.isState());
                String action = (state.isState())? "down" : "up";
                updateStatus(action, new HashMap<String, String>());

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
                    setupLayout();
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

    private void setupLayout(){
        toggle.setChecked(state.isState());
    }

}
