package com.bassanidevelopment.santiago.hci_movil.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bassanidevelopment.santiago.hci_movil.API.APIController;
import com.bassanidevelopment.santiago.hci_movil.API.APIResponseHandler;
import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.DevicesAPI;
import com.bassanidevelopment.santiago.hci_movil.API.SingletonAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.Device;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MostUsedFragment extends Fragment {
    private static final String TAG = "Most Used Fragment";

    private Button mostused1,mostused2,mostused3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mostused1= view.findViewById(R.id.mostUsed1);
        mostused2= view.findViewById(R.id.mostUsed2);
        mostused3= view.findViewById(R.id.mostUsed3);
        Log.d(TAG,"creating ...");

//        firts attempt
        final APIResponseHandler handler = new APIResponseHandler();
        DevicesAPI devicesAPI = new DevicesAPI(getContext(),handler );

        Object resp = devicesAPI.getAllDevices(new Callback() {
            @Override
            public boolean storeResponse(Object repsonse) {
                List<Device> devs = (List<Device>) repsonse;
                setupDevs(devs);
                return true;
            }
        });

        System.out.println("and the result was");
        System.out.println(resp);
//        second way
//        APIController api = new APIController(SingletonAPI.BASE_URL,
//                "\"content-type\": \"application/json; charset=utf-8\"",getContext());
//        System.out.println(Thread.currentThread().getId());
//        AsyncTask<String,Void, JSONObject> task =  api.execute("devices");
//        try {
//            System.out.println(task.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        return view;
    }


    private void setupDevs(List<Device> devices){
        for(Device device : devices){
            System.out.println(device.getName());
        }
        mostused1.setText(devices.get(0).getName());
        mostused2.setText(devices.get(1).getName());
        mostused3.setText(devices.get(2).getName());

        mostused1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }



}
