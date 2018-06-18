package com.bassanidevelopment.santiago.hci_movil.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bassanidevelopment.santiago.hci_movil.DevicesView.AirConditionerView;
import com.bassanidevelopment.santiago.hci_movil.DevicesView.BlindView;
import com.bassanidevelopment.santiago.hci_movil.DevicesView.LampView;
import com.bassanidevelopment.santiago.hci_movil.DevicesView.OvenView;
import com.bassanidevelopment.santiago.hci_movil.DevicesView.RefrigeratorView;
import com.bassanidevelopment.santiago.hci_movil.Model.Device;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceType;
import com.bassanidevelopment.santiago.hci_movil.R;

import java.util.HashMap;
import java.util.Map;

public class DeviceFragment extends Fragment{
    private Map<String, Integer> types = new HashMap<>();
    private View view;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        types.put("eu0v2xgprrhhg41g", R.layout.fragment_blind);
        types.put("go46xmbqeomjrsjr", R.layout.fragment_lamp);
        types.put("im77xxyulpegfmv8", R.layout.fragment_oven);
        types.put("li6cbv5sdlatti0j", R.layout.fragment_ac);
        types.put("lsf78ly0eqrjbz91", R.layout.fragment_door);
        //types.put("mxztsyjzsrq7iaqc", "Alarm");
        //types.put("ofglvd9gqX8yfl3l", "Timers");
        types.put("rnizejqr2di0okho", R.layout.fragment_refri);
        System.out.println("aca llegue");

        // esta es la que va a funcionar dinamicamente
        this.view = inflater.inflate(types.get(DeviceType.getCurrentType(getActivity(),getString(R.string.preference_file_key)))
               , container, false);
        // now we populate the layout with it's corresponding device state

        populateDev(DeviceType.getCurrentType(getActivity(),getString(R.string.preference_file_key)), view);

        //this.view = inflater.inflate(R.layout.fragment_blind, container, false);
        return view;
    }



    private  void populateDev(String typeId, View view){
        switch (typeId){
            case "eu0v2xgprrhhg41g":
                new BlindView(view, Device.getCurrentDev(getActivity(), Device.DEV_PREF), getContext());
                break;
            case "go46xmbqeomjrsjr":
                new LampView(view, Device.getCurrentDev(getActivity(), Device.DEV_PREF), getContext());
                break;
            case "im77xxyulpegfmv8":
                new OvenView(view, Device.getCurrentDev(getActivity(), Device.DEV_PREF), getContext());
                break;
            case "li6cbv5sdlatti0j":
                new AirConditionerView(view, Device.getCurrentDev(getActivity(), Device.DEV_PREF), getContext());
                break;
            case "rnizejqr2di0okho":
                new RefrigeratorView(view, Device.getCurrentDev(getActivity(), Device.DEV_PREF), getContext());
                break;
        }
    }


    public void displayDeviceSettings(Device dev){
        Integer type = types.get(dev.getTypeId());
    }


}
