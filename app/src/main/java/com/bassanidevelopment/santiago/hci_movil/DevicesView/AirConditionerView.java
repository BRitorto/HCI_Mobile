package com.bassanidevelopment.santiago.hci_movil.DevicesView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.DevicesAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceState.AirConditionerState;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceState.RefrigeratorState;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AirConditionerView extends DevicesView {

    //layout components
    private Switch aSwitch;
    private SeekBar seekBarTemperature;
    private Spinner spinnerMode;
    private Spinner spinnerVertical;
    private Spinner spinnerHorizontal;
    private Spinner spinnerSpeed;

    // state
    private AirConditionerState state;

    public AirConditionerView(View view, String devId, Context context) {
        this.devId = devId;
        this.context = context;

        // attach components

        aSwitch = view.findViewById(R.id.switch_ac);
        seekBarTemperature = view.findViewById(R.id.seekBar_ac);
        //spinnerMode = view.findViewById(R.id.);
//        spinnerVertical = view.findViewById(R.id.choose_vertical);
//        spinnerHorizontal = view.findViewById(R.id.choose_horizontal);
//        spinnerSpeed = view.findViewById(R.id.choose_speed);

        // set state
        setState(devId);


        setListeners();
    }

    private void setListeners() {
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aSwitch.setChecked(!aSwitch.isChecked());
                state.setStatus(aSwitch.isChecked());
                String action = (state.isStatus())?"turnOff": "turnOn";
                updateStatus(action, new HashMap<String, String>());
            }
        });

        seekBarTemperature.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                state.setTemperature(seekBar.getProgress());
                Map<String, String> params = new HashMap<>();
                params.put("temp", String.valueOf(state.getTemperature()));
                updateStatus("setTemperature",params);

            }
        });
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


    private AirConditionerState processStatus(JSONObject object){
        int temp = 2;
        String mode = "cool";
        String vertical = "auto";
        String horizontal = "auto";
        String speed = "auto";

        boolean status = false;

        try {
            temp = object.getInt("temperature");
            mode = object.getString("mode");
            vertical = object.getString("verticalSwing");
            horizontal = object.getString("horizontalSwing");
            speed = object.getString("fanSpeed");

            status = (object.getString("status").equals("on"))? true : false;


            Log.d("REFRI", "Yo, i'm here bro");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new AirConditionerState(status,temp,mode,vertical,horizontal,speed);

    }


    private  void updateStatus(String action,Map<String,String> param){
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

    public void setLayoutDisplay(){
        seekBarTemperature.setProgress(state.getTemperature());
        aSwitch.setChecked(state.isStatus());
    }

}
