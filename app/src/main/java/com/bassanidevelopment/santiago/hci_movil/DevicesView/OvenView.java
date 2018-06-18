package com.bassanidevelopment.santiago.hci_movil.DevicesView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.DevicesAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceState.OvenState;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceState.RefrigeratorState;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OvenView extends  DevicesView {

    private OvenState ovenState;
    private Switch aSwitch;
    private SeekBar seekBarTemperature;
    private Spinner spinnerHeat;
    private Spinner spinnerGrill;
    private  Spinner spinnerConvection;

    public OvenView(View view, String devId,Context context) {


        this.context = context;
        this.devId = devId;


        // attach dev
        aSwitch = view.findViewById(R.id.switch_oven);
        seekBarTemperature = view.findViewById(R.id.seekBar_oven);
        spinnerHeat = view.findViewById(R.id.choose_heat);
        spinnerConvection = view.findViewById(R.id.choose_convection);
        spinnerGrill = view.findViewById(R.id.choose_grill);

        setState(devId);


        // listners

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ovenState.setStatus(!ovenState.isStatus());
                Map<String, String> param = new HashMap<>();
                String action =  (ovenState.isStatus())?"turnOn": "turnOff";
                param.put("switch","");
                updateStatus(action, param);
            }
        });


    }
    private void setState(String devId){

        Callback callback = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                try {
                    ovenState = processStatus(response.getJSONObject("result"));
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

    private OvenState processStatus(JSONObject object){
        boolean status = false;
        int temp = 100;
        String heat = "conventional";
        String grill = "large";
        String convection = "normal";

        try {
            temp = object.getInt("temperature");
            heat = object.getString("heat");
            grill =  object.getString("grill");
            convection =  object.getString("convection");
            if(object.getString("status").equals("off"))
                status = false;
            else
                status = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return  new OvenState(temp, status, heat, grill, convection);
    }


    private  void setLayoutDisplay(){
    aSwitch.setChecked(ovenState.isStatus());
    seekBarTemperature.setProgress(ovenState.getTemperature());
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
}
