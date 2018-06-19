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
import android.widget.TextView;

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
import java.util.Map;

public class MostUsedFragment extends Fragment {
    private static final String TAG = "Most Used Fragment";

    private TextView mostused1,mostused2,mostused3;

    public int buttonIndex;

    private Toolbar myToolbar;

    private List<TextView>  mostUsedArr;
    private List<Device> mostUsedDev;

    Map<Device, Integer> mostUsedMap;
    private int setupIndex = 0;

    public String currentDevId ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        myToolbar = getActivity().findViewById(R.id.upper_toolbar);
        myToolbar.setTitle(R.string.home);
        myToolbar.setNavigationIcon(null);

        mostUsedMap = new HashMap<>();

        mostused1= view.findViewById(R.id.mostUsed1);
        mostused2= view.findViewById(R.id.mostUsed2);
        mostused3= view.findViewById(R.id.mostUsed3);

        mostUsedArr = new ArrayList<>();
        mostUsedArr.add(mostused1);
        mostUsedArr.add(mostused2);
        mostUsedArr.add(mostused3);


        mostUsedDev = new ArrayList<>();

        Log.d(TAG,"creating ...");


        setupLayout();


        return view;
    }


    private void setupHanlders(){
        final Callback callback = new Callback() {
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

    }

    private void setupLayout(){
        //        firts attempt
        for(TextView aSwitch: mostUsedArr){
            aSwitch.setVisibility(View.INVISIBLE);
        }

        mostUsedDev.clear();

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



        int counter = 0;
        for(Device dev : devices){
            if (counter < mostUsedArr.size()) {
                mostUsedArr.get(counter).setText(dev.getName());
                mostUsedArr.get(counter).setVisibility(View.VISIBLE);
                counter++;
            }else
                break;

        }

    }


    @Override
    public void onResume() {
        super.onResume();
        //
        // setupLayout();
        setupIndex = 0;
    }
}
