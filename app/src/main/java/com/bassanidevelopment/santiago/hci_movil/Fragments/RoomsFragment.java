package com.bassanidevelopment.santiago.hci_movil.Fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.RoomsAPI;
import com.bassanidevelopment.santiago.hci_movil.API.SingletonAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.APIObject;
import com.bassanidevelopment.santiago.hci_movil.Model.RoomGridAdapter;
import com.bassanidevelopment.santiago.hci_movil.Model.Room;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.bassanidevelopment.santiago.hci_movil.API.RoomsAPI.addRoom;

public class RoomsFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ArrayList<APIObject> rooms = new ArrayList();
    private GridView gridView;
    private String newRoomName = null;
    private View view;
    private Toolbar myToolbar;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.grid_view, container, false);
        this.gridView = (GridView) view.findViewById(R.id.gridview);
        retrieveRooms();
        myToolbar = getActivity().findViewById(R.id.upper_toolbar);
        myToolbar.setTitle(R.string.rooms);
        myToolbar.setNavigationIcon(null);
        gridView.setOnItemClickListener(this);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRoomDialog();
            }
        });

        return view;
    }

    public void addRoomDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Room");
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.text_input, (ViewGroup) getView(), false);
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);
        builder.setPositiveButton(R.string.add_room, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                newRoomName = input.getText().toString();
                addNewRoom(newRoomName);
                //error si ya existe el nombre

            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void retrieveRooms(){
        Callback callback = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                try {
                    List<Room> rooms = new ArrayList<>();
                    for (int i = 0; i < response.getJSONArray("rooms").length(); i++) {

                        JSONObject jsonRoom = response.getJSONArray("rooms").getJSONObject(i);
                        if(jsonRoom.isNull("meta" )) {
                            JSONObject emptyobj = new JSONObject();
                            jsonRoom.put("meta",emptyobj);
                        }

                        Room room = new Room(jsonRoom);
                        rooms.add(room);
                    }

                    setupRooms(rooms);
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

    public void setupRooms(List<Room> roomsList){
        rooms = new ArrayList<>();
        for(Room room: roomsList){
            rooms.add(room);

        }
        gridView.setAdapter(new RoomGridAdapter(getActivity(), rooms));
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        myToolbar.setTitle(rooms.get(i).getName());
        Room room  = (Room) rooms.get(i);
        room.setAsCurrentRoom(getActivity(), getString(R.string.preference_file_key));
        
        FragmentManager f = getFragmentManager();
        FragmentTransaction ft = f.beginTransaction();
        ft.replace(R.id.fragment_place, new DeviceTypeFragment());
        ft.addToBackStack(null);
        ft.commit();
    }




    public void addNewRoom(final String newRoomName){

        addRoom(getActivity(), newRoomName, new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                if(! SingletonAPI.isError(response)){
                    retrieveRooms();
                    Snackbar.make(view, "Room " + newRoomName + " added successfully", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return  true;
                }


                return false;
            }

            @Override
            public void showSpinner() {

            }

            @Override
            public void hideSpinner() {

            }
        });
    }
}


