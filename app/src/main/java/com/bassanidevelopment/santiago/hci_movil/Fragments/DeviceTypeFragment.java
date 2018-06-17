package com.bassanidevelopment.santiago.hci_movil.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.DevicesTypesAPI;
import com.bassanidevelopment.santiago.hci_movil.API.RoomsAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.APIObject;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceType;
import com.bassanidevelopment.santiago.hci_movil.Model.Room;
import com.bassanidevelopment.santiago.hci_movil.Model.RoomGridAdapter;
import com.bassanidevelopment.santiago.hci_movil.Model.TypesGridAdapter;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DeviceTypeFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ArrayList<DeviceType> types = new ArrayList();
    private GridView gridView;
    private View view;
    private Toolbar myToolbar;
    private FragmentManager manager;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.grid_view_types, container, false);
        this.gridView = (GridView) view.findViewById(R.id.gridview);

        retrieveTypes();
        manager = getFragmentManager();
        gridView.setOnItemClickListener(this);
        myToolbar = getActivity().findViewById(R.id.upper_toolbar);
        myToolbar.setNavigationIcon(R.drawable.ic_back);
        myToolbar.setNavigationOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                manager.popBackStack();
            }
        });

        return view;
    }



    public void retrieveTypes(){
        Callback callback = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                try {
                    types = new ArrayList<>();
                    for (int i = 0; i < response.getJSONArray("devices").length(); i++) {

                        JSONObject jsonType = response.getJSONArray("devices").getJSONObject(i);

                        DeviceType type = new DeviceType(jsonType);
                        types.add(type);
                    }

                    setupTypes();
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
        DevicesTypesAPI.getAllDeviceTypes(getContext(), callback);
    }

    public void setupTypes(){
        gridView.setAdapter(new TypesGridAdapter(getActivity(), types));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast t = Toast.makeText(getContext(), types.get(i).getName(), Toast.LENGTH_SHORT);
        t.show();
        DeviceType type = types.get(i);
        type.setAsCurrenttype(getActivity(),getString(R.string.preference_file_key));

        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.fragment_place, new DeviceListFragment());
        ft.addToBackStack(null);
        ft.commit();
    }


}

