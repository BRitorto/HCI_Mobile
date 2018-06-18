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
    private Button buttonHeat;
    private Button buttonGrill;
    private Button buttonConvection;

    private View view;
    public OvenView(View view, String devId,Context context) {


        this.context = context;
        this.devId = devId;
        this.view = view;

        // attach dev
        aSwitch = view.findViewById(R.id.switch_oven);
        seekBarTemperature = view.findViewById(R.id.seekBar_oven);
        buttonHeat = view.findViewById(R.id.choose_heat);
        buttonConvection = view.findViewById(R.id.choose_convection);
        buttonGrill = view.findViewById(R.id.choose_grill);

        setState(devId);


        setupListners();

    }

    private  void setupListners(){
        // listners

        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ovenState.setStatus(!ovenState.isStatus());
                String action =  (ovenState.isStatus())?"turnOn": "turnOff";
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
                ovenState.setTemperature(seekBar.getProgress());
                Map<String, String> param = new HashMap<>();
                String action =  "setTemperature";
                param.put("temp",String.valueOf(ovenState.getTemperature()));
                updateStatus(action, param);
            }
        });

        buttonHeat.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                chooseHeat(view);
            }
        });

        buttonConvection.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                chooseConvection(view);
            }
        });

        buttonGrill.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                chooseGrill(view);
            }
        });
    }

    private void chooseHeat(View v){
        final String[] chooseHeat = v.getResources().getStringArray(R.array.choose_heat);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Choose an item");
        //en el -1 se pone la opcion que esta seleccionada
        builder.setSingleChoiceItems(chooseHeat, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                buttonHeat.setText(chooseHeat[i]);
                //aca hacer algo con la opcion elegida, mandarla a la api
                String action = "setHeat";
                Map<String ,String > param = new HashMap<>();
                param.put("heat", chooseHeat[i]);
                updateStatus(action, param);
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void chooseConvection(View v){
        final String[] chooseConvection = v.getResources().getStringArray(R.array.choose_convection);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Choose an item");
        //en el -1 se pone la opcion que esta seleccionada
        builder.setSingleChoiceItems(chooseConvection, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                buttonConvection.setText(chooseConvection[i]);
                //aca hacer algo con la opcion elegida, mandarla a la api
                String action = "setConvection";
                Map<String ,String > param = new HashMap<>();
                param.put("heat", chooseConvection[i]);
                updateStatus(action, param);

                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void chooseGrill(View v){
        final String[] chooseGrill = v.getResources().getStringArray(R.array.choose_grill);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setTitle("Choose an item");
        //en el -1 se pone la opcion que esta seleccionada
        builder.setSingleChoiceItems(chooseGrill, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                buttonGrill.setText(chooseGrill[i]);
                //aca hacer algo con la opcion elegida, mandarla a la api
                String action = "setGrill";
                Map<String ,String > param = new HashMap<>();
                param.put("heat", chooseGrill[i]);
                updateStatus(action, param);

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

    String[] arr = view.getResources().getStringArray(R.array.choose_heat);
    int option = getCurrentOption(arr, ovenState.getHeat());

    buttonHeat.setText(arr[option]);

    arr = view.getResources().getStringArray(R.array.choose_grill);
    option =  getCurrentOption(arr, ovenState.getGrill());

    buttonGrill.setText(arr[option]);

    arr = view.getResources().getStringArray(R.array.choose_convection);
    option = getCurrentOption(arr, ovenState.getConvection());

    buttonConvection.setText(arr[option]);

    }


}
