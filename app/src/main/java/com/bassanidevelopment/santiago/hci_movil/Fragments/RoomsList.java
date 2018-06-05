package com.bassanidevelopment.santiago.hci_movil.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bassanidevelopment.santiago.hci_movil.Model.SimpleList;
import com.bassanidevelopment.santiago.hci_movil.Model.SimpleListAdapter;
import com.bassanidevelopment.santiago.hci_movil.R;

import java.util.ArrayList;


public class RoomsList extends ListFragment {
    ArrayList<SimpleList> rooms = new ArrayList();
    SimpleListAdapter adapter;
    @Override

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rooms.add(new SimpleList("room1"));
        rooms.add(new SimpleList("room2"));
        rooms.add(new SimpleList("room3"));
        adapter = new SimpleListAdapter(getActivity(), R.layout.fragment_rooms, rooms);
        setListAdapter(adapter);
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
}