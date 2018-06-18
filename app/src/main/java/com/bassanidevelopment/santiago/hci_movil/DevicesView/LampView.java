package com.bassanidevelopment.santiago.hci_movil.DevicesView;
import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.DevicesAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceState.AirConditionerState;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceState.LampState;
import com.bassanidevelopment.santiago.hci_movil.R;
import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerView;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LampView extends DevicesView {


    // specific

    private Switch aSwitch;
    private SeekBar seekBar;
    private  View view;
    private ColorPickerView colorPickerView;
    private TextView chosenColor;
    // em queda uno que ni puta idea

    private LampState state;

    public LampView(View view ,String devId, Context context){
        this.context = context;
        this.devId = devId;
        this.view = view;
        // attach components
        aSwitch = view.findViewById(R.id.switch_lamp);
        seekBar = view.findViewById(R.id.seekBar_lamp);
        colorPickerView = view.findViewById(R.id.colorPicker_lamp);
        chosenColor = view.findViewById(R.id.chosen_color);
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





    private  void setLayoutDisplay() {
        aSwitch.setChecked(state.isStatus());
        seekBar.setProgress(state.getBrightness());
        String color = state.getColor();
        color = "#" + color;
        try {
            chosenColor.setBackgroundColor(Color.parseColor(color));

        }catch (Exception e){
            System.out.println(Color.parseColor(color));
            System.out.println("this color is not found");
        }
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


        colorPickerView.setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                chosenColor.setBackgroundColor(colorEnvelope.getColor());
                String action = "setColor";
                if(state != null) {
                    state.setColor(colorEnvelope.getColorHtml());
                    Map<String, String> param = new HashMap<>();
                    param.put("color", state.getColor());
                    updateStatus(action, param);
                }
            }
        });
    }
}
