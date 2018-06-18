package com.bassanidevelopment.santiago.hci_movil.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.bassanidevelopment.santiago.hci_movil.API.APIController;
import com.bassanidevelopment.santiago.hci_movil.API.APIResponseHandler;
import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.DevicesAPI;
import com.bassanidevelopment.santiago.hci_movil.API.SingletonAPI;
import com.bassanidevelopment.santiago.hci_movil.MainActivity;
import com.bassanidevelopment.santiago.hci_movil.Model.Device;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MostUsedFragment extends Fragment {
    private static final String TAG = "Most Used Fragment";

    private Switch mostused1,mostused2,mostused3;

    public int buttonIndex;

    private Toolbar myToolbar;

    private List<Switch>  mostUsedArr;

    private int setupIndex = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        myToolbar = getActivity().findViewById(R.id.upper_toolbar);
        myToolbar.setTitle(R.string.home);
        myToolbar.setNavigationIcon(null);

        mostused1= view.findViewById(R.id.mostUsed1);
        mostused2= view.findViewById(R.id.mostUsed2);
        mostused3= view.findViewById(R.id.mostUsed3);

        mostUsedArr = new ArrayList<>();
        mostUsedArr.add(mostused1);
        mostUsedArr.add(mostused2);
        mostUsedArr.add(mostused3);


        Log.d(TAG,"creating ...");

        setupLayout();


        return view;
    }


    private void setupLayout(){
        //        firts attempt
        for(Switch aSwitch: mostUsedArr){
            aSwitch.setVisibility(View.INVISIBLE);
        }

        Callback callback = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                List<Device> devices = new ArrayList<>();
                try {
                    for (int i = 0; i < response.getJSONArray("devices").length(); i++ ){
                        Device dev = new Device(response.getJSONArray("devices").getJSONObject(i));
                        devices.add(dev);
                    }
                    setupDevs(devices);
                    return true;
                }catch(Exception e){
                    e.printStackTrace();
                    return false;
                }

            }

            @Override
            public void showSpinner() {
                //MainActivity.spinner.setVisibility(View.VISIBLE);
            }

            @Override
            public void hideSpinner() {
                //MainActivity.spinner.setVisibility(View.INVISIBLE);
            }
        };

        DevicesAPI.getAllDevices(callback, getContext());
    }

    private void setupDevs(List<Device> devices){

        Callback callback = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {

                try {
                    JSONObject result =  response.getJSONObject("result");
                    String status = result.getString("status");
                    if(setupIndex < mostUsedArr.size()) {
                        if (status.equals("off") || status.equals("opened")) {
                            mostUsedArr.get(setupIndex).setChecked(false);
                        } else {
                            mostUsedArr.get(setupIndex).setChecked(true);
                        }
                        setupIndex++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public void showSpinner() {

            }

            @Override
            public void hideSpinner() {

            }
        };

//        if(devices.size() > 2) {
//            mostused1.setText(devices.get(0).getName());
//            mostused2.setText(devices.get(1).getName());
//            mostused3.setText(devices.get(2).getName());
//
//            for(int i= 0 ; i < 2; i++){
//                DevicesAPI.deviceAction(getContext(), callback, devices.get(i).getId(), "getState",new HashMap<String, String>() );
//            }
//        }




        int counter = 0;
        for(Device dev : devices){
            if (counter < mostUsedArr.size()-1) {
                mostUsedArr.get(counter).setText(dev.getName());
                mostUsedArr.get(counter).setVisibility(View.VISIBLE);
                DevicesAPI.deviceAction(getContext(), callback, devices.get(counter).getId(), "getState",new HashMap<String, String>() );
                counter++;
            }else
                break;

        }




    }

    @Override
    public void onResume() {
        super.onResume();
        setupLayout();
        setupIndex = 0;
    }
}
