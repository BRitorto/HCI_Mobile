package com.bassanidevelopment.santiago.hci_movil.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.RoomsAPI;
import com.bassanidevelopment.santiago.hci_movil.MainActivity;
import com.bassanidevelopment.santiago.hci_movil.Model.Room;
import com.bassanidevelopment.santiago.hci_movil.Model.SimpleList;
import com.bassanidevelopment.santiago.hci_movil.Model.SimpleListAdapter;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RoomsList extends ListFragment {
    ArrayList<SimpleList> rooms = new ArrayList();
    SimpleListAdapter adapter;
    @Override

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        retrieveRooms();
        //setupRooms();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                Toast.makeText(getActivity(), rooms.get(pos).getName(), Toast.LENGTH_SHORT).show();
            }
        });
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
        for(Room room: roomsList){
            rooms.add(new SimpleList(room.getName()));
        }

        adapter = new SimpleListAdapter(getActivity(), R.layout.fragment_rooms, rooms);
        setListAdapter(adapter);

    }

    

}