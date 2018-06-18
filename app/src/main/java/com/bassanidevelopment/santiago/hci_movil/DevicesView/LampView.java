package com.bassanidevelopment.santiago.hci_movil.DevicesView;
import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.DevicesAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceState.AirConditionerState;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceState.LampState;
import com.bassanidevelopment.santiago.hci_movil.R;

import android.content.Context;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Switch;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LampView extends DevicesView {


    // specific

    private Switch aSwitch;
    private SeekBar seekBar;
    // em queda uno que ni puta idea

    private LampState state;

    public LampView(View view ,String devId, Context context){
        this.context = context;
        this.devId = devId;

        // attach components
        aSwitch = view.findViewById(R.id.switch_lamp);
        seekBar = view.findViewById(R.id.seekBar_lamp);


        setState(devId);

        setupHandlers();
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


    private LampState processStatus(JSONObject object){
        int brightness = 0;
        String color = "FFFFFF";

        boolean status = false;

        try {
            brightness =  object.getInt("brightness");
            color = object.getString("color");

            status = (object.getString("status").equals("on"))? true : false;


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new LampState(status,color,brightness);

    }





    private  void setLayoutDisplay(){
        aSwitch.setChecked(state.isStatus());
        seekBar.setProgress(state.getBrightness());
    }

    private  void setupHandlers(){
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                state.setBrightness(seekBar.getProgress());
                Map<String , String> param = new HashMap<>();
                param.put("birghtness", String.valueOf(state.getBrightness()));
                String action = "setBrightness";
                updateStatus(action, param);
            }
        });

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aSwitch.setChecked(!aSwitch.isChecked());
                state.setStatus(aSwitch.isChecked());
                String action = (state.isStatus())? "turnOff" : "turnOn";

                updateStatus(action, new HashMap<String, String>());
            }
        });
    }
}
