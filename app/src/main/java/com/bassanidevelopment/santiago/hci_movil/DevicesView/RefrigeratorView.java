package com.bassanidevelopment.santiago.hci_movil.DevicesView;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.DevicesAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceState.RefrigeratorState;
import com.bassanidevelopment.santiago.hci_movil.R;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RefrigeratorView extends DevicesView {

    // layout components

    private SeekBar seekBarRefri;
    private  SeekBar seekBarFreezer;
    private Spinner spinnerMode;

    private RefrigeratorState refri;


    public  RefrigeratorView(View view, String devId,Context context){
        seekBarRefri = view.findViewById(R.id.seekBar_refri);
        seekBarFreezer = view.findViewById(R.id.seekBar_freezer);
        spinnerMode = view.findViewById(R.id.choose_fridge);

        this.context = context;
        setupSeekBar();


        this. devId = devId;


        setState(devId);

        seekBarFreezer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                refri.setFreezerTemperature(seekBar.getProgress());
                Map<String , String> param = new HashMap<>();
                param.put("temperature", String.valueOf(refri.getFreezerTemperature()));
                updateStatus("setFreezerTemperature",param);
            }
        });

        seekBarRefri.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                refri.setTemperature(seekBar.getProgress());
                Map<String , String> param = new HashMap<>();
                param.put("temperature", String.valueOf(refri.getTemperature()));
                updateStatus("setTemperature",param);
            }
        });
    }

    private void setState(String devId){

        Callback callback = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                try {

                    refri = processStatus(response.getJSONObject("result"));
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


    private  RefrigeratorState processStatus(JSONObject object){
        int temp = 2;
        int freezerTemp = -10 ;
        RefrigeratorState.Mode mode = RefrigeratorState.Mode.def;

        try {
            temp = object.getInt("temperature");
            freezerTemp = object.getInt("freezerTemperature");
            mode = RefrigeratorState.getModeFromString(
                    object.getString("mode")
            );

            Log.d("REFRI", "Yo, i'm here bro");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new RefrigeratorState(temp, freezerTemp, mode);

    }

    public void setLayoutDisplay(){
        seekBarRefri.setProgress(refri.getTemperature());
        seekBarFreezer.setProgress(refri.getFreezerTemperature());
        spinnerMode.setSelection(refri.getMode());
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


    private void setupSeekBar(){
        seekBarRefri.setMax(8);
        seekBarFreezer.setMax(20);
    }


}
