package com.bassanidevelopment.santiago.hci_movil.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.RoomsAPI;
import com.bassanidevelopment.santiago.hci_movil.API.SingletonAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.APIObject;
import com.bassanidevelopment.santiago.hci_movil.Model.DeviceType;
import com.bassanidevelopment.santiago.hci_movil.Model.GridAdapter;
import com.bassanidevelopment.santiago.hci_movil.Model.Room;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bassanidevelopment.santiago.hci_movil.API.RoomsAPI.addRoom;

public class DeviceTypeFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ArrayList<APIObject> types = new ArrayList();
    private GridView gridView;
    private View view;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.grid_view, container, false);
        this.gridView = (GridView) view.findViewById(R.id.gridview);
        retrieveTypes();
        gridView.setOnItemClickListener(this);

        return view;
    }


    public void retrieveTypes(){
        Callback callback = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                try {
                    List<DeviceType> types = new ArrayList<>();
                    for (int i = 0; i < response.getJSONArray("devices").length(); i++) {

                        JSONObject jsonType = response.getJSONArray("devices").getJSONObject(i);

                        DeviceType type = new DeviceType(jsonType);
                        types.add(type);
                    }

                    setupTypes(types);
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
        RoomsAPI.getAllRooms(getContext(), callback);
    }

    public void setupTypes(List<DeviceType> devTypesList){
        types = new ArrayList<>();
        for(DeviceType type: devTypesList){
            types.add(type);

        }
        gridView.setAdapter(new GridAdapter(getActivity(), types));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast t = Toast.makeText(getContext(), types.get(i).getName(), Toast.LENGTH_SHORT);
        t.show();
    }


}

