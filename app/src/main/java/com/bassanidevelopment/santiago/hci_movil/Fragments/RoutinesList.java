package com.bassanidevelopment.santiago.hci_movil.Fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.bassanidevelopment.santiago.hci_movil.API.Callback;
import com.bassanidevelopment.santiago.hci_movil.API.RoutinesAPI;
import com.bassanidevelopment.santiago.hci_movil.Model.Routine;
import com.bassanidevelopment.santiago.hci_movil.Model.SimpleList;
import com.bassanidevelopment.santiago.hci_movil.Model.SimpleListAdapter;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RoutinesList extends ListFragment {
    ArrayList<SimpleList> routines = new ArrayList();
    SimpleListAdapter adapter;
    @Override

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        retrieveRoutines();
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
                executeRoutine(routines.get(pos).getId());
            }
        });
    }


    public void retrieveRoutines(){
        Callback callback = new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("routines");
                    List<Routine> routines = new ArrayList<>();
                    for(int i = 0; i < array.length(); i++){
                        Routine routine = new Routine(array.getJSONObject(i));
                        routines.add(routine);
                    }
                    setupRotuines(routines);

                    return  true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public void showSpinner() {

            }

            @Override
            public void hideSpinner() {

            }
        };

        RoutinesAPI.getAllRoutines(getContext(), callback);
    }


    public void setupRotuines(List<Routine> routineList){

        for(Routine r : routineList){
            routines.add(new SimpleList(r.getName(), r.getId()));
        }


        adapter = new SimpleListAdapter(getActivity(), R.layout.fragment_routines, routines);
        setListAdapter(adapter);
    }


    public void executeRoutine(String routineId){
        RoutinesAPI.executeRoutine(getContext(), routineId, new Callback() {
            @Override
            public boolean handleResponse(JSONObject response) {
                return true;
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