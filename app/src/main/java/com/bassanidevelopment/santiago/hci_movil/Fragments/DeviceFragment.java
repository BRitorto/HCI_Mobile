package com.bassanidevelopment.santiago.hci_movil.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bassanidevelopment.santiago.hci_movil.Model.Device;
import com.bassanidevelopment.santiago.hci_movil.R;

import java.util.HashMap;

public class DeviceFragment extends Fragment{
    private HashMap<String, Integer> types = new HashMap<>();
    private View view;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        types.put("eu0v2xgprrhhg41g", R.layout.fragment_blind);
        /*types.put("go46xmbqeomjrsjr", "Lamps");
        types.put("im77xxyulpegfmv8", "Ovens");
        types.put("li6cbv5sdlatti0j", "AC");
        types.put("lsf78ly0eqrjbz91", "Doors");
        types.put("mxztsyjzsrq7iaqc", "Alarm");
        types.put("ofglvd9gqX8yfl3l", "Timers");
        types.put("rnizejqr2di0okho", "Refrigerator");*/
        System.out.println("aca llegue");

        this.view = inflater.inflate(R.layout.fragment_blind, container, false);
        return view;
    }


    public void displayDeviceSettings(Device dev){
        Integer type = types.get(dev.getTypeId());
    }
}
