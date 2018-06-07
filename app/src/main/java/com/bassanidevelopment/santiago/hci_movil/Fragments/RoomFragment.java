package com.bassanidevelopment.santiago.hci_movil.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.RoomDevicesAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.Device;
import com.bassanidevelopment.santiago.hci_movil.Model.Room;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RoomFragment extends Fragment {

    private ImageButton button1, button2, button3, button4, button5, button6;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_room, container, false);

        button1 = view.findViewById(R.id.imageButton);
        button2 = view.findViewById(R.id.imageButton2);
        button3 = view.findViewById(R.id.imageButton3);
        button4 = view.findViewById(R.id.imageButton4);
        button5 = view.findViewById(R.id.imageButton5);
        button6 = view.findViewById(R.id.imageButton6);

        List<ImageButton> devicesTypes = new ArrayList<>();

        devicesTypes.add(button1);
        devicesTypes.add(button2);
        devicesTypes.add(button3);
        devicesTypes.add(button4);
        devicesTypes.add(button5);
        devicesTypes.add(button6);
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        retrieveDevices(sharedPreferences.getString(Room.ROOM_PREF, "0"));
        return view;
    }


    public  void retrieveDevices(String roomId){

        Callback callback = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                List<Device> devices = new ArrayList<>();
                try {
                    JSONArray deviceArray = response.getJSONArray("devices");
                    for(int i =0 ; i < deviceArray.length(); i++){
                        Device dev = new Device(deviceArray.getJSONObject(i));
                        devices.add(dev);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }

                setupDevices(devices);

                return  true;
            }

            @Override
            public void showSpinner() {

            }

            @Override
            public void hideSpinner() {

            }
        };


        RoomDevicesAPI.getRoomDevices(getContext(), roomId, callback);
    }


    public void setupDevices(List<Device> devices){


    }

}