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
import com.bassanidevelopment.santiago.hci_movil.Model.APIObject;
import com.bassanidevelopment.santiago.hci_movil.Model.Routine;
import com.bassanidevelopment.santiago.hci_movil.Model.APIObjectAdapter;
import com.bassanidevelopment.santiago.hci_movil.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class RoutinesList extends ListFragment {
    ArrayList<APIObject> routines = new ArrayList();
    APIObjectAdapter adapter;
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
                Toast.makeText(getActivity(), routines.get(pos).getName() + " executed", Toast.LENGTH_SHORT).show();
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
                    setupRoutines(routines);

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


    public void setupRoutines(List<Routine> routineList){
        routines = new ArrayList<>();
        for(Routine r : routineList){
            routines.add(r);
        }


        adapter = new APIObjectAdapter(getActivity(), R.layout.fragment_routines, routines, 1);
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