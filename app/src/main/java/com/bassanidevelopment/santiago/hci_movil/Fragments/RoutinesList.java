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


public class RoutinesList extends ListFragment {
    ArrayList<SimpleList> routines = new ArrayList();
    SimpleListAdapter adapter;
    @Override

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        routines.add(new SimpleList("routine1"));
        routines.add(new SimpleList("routine2"));
        routines.add(new SimpleList("routine3"));
        adapter = new SimpleListAdapter(getActivity(), R.layout.fragment_routines, routines);
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
                Toast.makeText(getActivity(), routines.get(pos).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}