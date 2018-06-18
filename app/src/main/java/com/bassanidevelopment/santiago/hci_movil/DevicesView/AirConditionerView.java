package com.bassanidevelopment.santiago.hci_movil.DevicesView;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private Button buttonMode;
    private Button buttonVertical;
    private Button buttonHorizontal;
    private Button buttonSpeed;

    // state
    private AirConditionerState state;

    public AirConditionerView(View view, String devId, Context context) {
        this.devId = devId;
        this.context = context;

        // attach components

        aSwitch = view.findViewById(R.id.switch_ac);
        seekBarTemperature = view.findViewById(R.id.seekBar_ac);
        buttonMode = view.findViewById(R.id.choose_mode_ac);
        buttonVertical = view.findViewById(R.id.choose_vertical);
        buttonHorizontal = view.findViewById(R.id.choose_horizontal);
        buttonSpeed = view.findViewById(R.id.choose_speed);

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

        buttonMode.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                chooseMode(view);
            }
        });

        buttonVertical.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                chooseVertical(view);
            }
        });

        buttonHorizontal.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                chooseHorizontal(view);
            }
        });

        buttonSpeed.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                chooseSpeed(view);
            }
        });
    }

    private void chooseMode(View v){
        final String[] chooseMode = v.getResources().getStringArray(R.array.choose_mode_ac);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Choose an item");
        //en el -1 se pone la opcion que esta seleccionada
        builder.setSingleChoiceItems(chooseMode, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                buttonMode.setText(chooseMode[i]);
                //aca hacer algo con la opcion elegida, mandarla a la api
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void chooseVertical(View v){
        final String[] chooseVertical = v.getResources().getStringArray(R.array.choose_vertical);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Choose an item");
        //en el -1 se pone la opcion que esta seleccionada
        builder.setSingleChoiceItems(chooseVertical, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                buttonVertical.setText(chooseVertical[i]);
                //aca hacer algo con la opcion elegida, mandarla a la api
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void chooseHorizontal(View v){
        final String[] chooseHorizontal = v.getResources().getStringArray(R.array.choose_horizontal);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Choose an item");
        //en el -1 se pone la opcion que esta seleccionada
        builder.setSingleChoiceItems(chooseHorizontal, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                buttonHorizontal.setText(chooseHorizontal[i]);
                //aca hacer algo con la opcion elegida, mandarla a la api
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void chooseSpeed(View v){
        final String[] chooseSpeed = v.getResources().getStringArray(R.array.choose_speed);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Choose an item");
        //en el -1 se pone la opcion que esta seleccionada
        builder.setSingleChoiceItems(chooseSpeed, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                buttonSpeed.setText(chooseSpeed[i]);
                //aca hacer algo con la opcion elegida, mandarla a la api
                dialogInterface.dismiss();
            }
        });
        builder.show();
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
