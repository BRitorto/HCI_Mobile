package com.bassanidevelopment.santiago.hci_movil.Fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.RoomDevicesAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.APIObject;
import com.bassanidevelopment.santiago.hci_movil.Model.APIObjectAdapter;
import com.bassanidevelopment.santiago.hci_movil.Model.Device;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceType;
import com.bassanidevelopment.santiago.hci_movil.Model.Room;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeviceList  extends android.support.v4.app.ListFragment {
    ArrayList<APIObject> roomDevices = new ArrayList();
    APIObjectAdapter adapter;
    @Override

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        retrieveRoomDevices();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                Toast.makeText(getActivity(), roomDevices.get(pos).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void retrieveRoomDevices(){
        Callback callback = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                try {
                    String typeid = DeviceType.getCurrentType(getActivity(), getString(R.string.preference_file_key));
                    for (int i = 0; i < response.getJSONArray("devices").length(); i++) {

                        JSONObject jsonDevice = response.getJSONArray("devices").getJSONObject(i);

                        Device dev = new Device(jsonDevice);
                        if(typeid.equals(dev.getTypeId())) {
                            roomDevices.add(dev);
                        }
                    }

                    setupDevices(roomDevices);
                    return  true;

                }catch (Exception e){
                    e.printStackTrace();
                }
                return  false;
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

        RoomDevicesAPI.getRoomDevices(getContext(), Room.getCurrentRoom(getActivity(),
                getString(R.string.preference_file_key)),callback);
    }

    public void setupDevices(List<APIObject> devices){
        roomDevices = new ArrayList<>();
        for(APIObject device: devices){
            roomDevices.add(device);
        }
        adapter = new APIObjectAdapter(getActivity(), R.layout.fragment_list_devices, devices);
        setListAdapter(adapter);
    }

}
