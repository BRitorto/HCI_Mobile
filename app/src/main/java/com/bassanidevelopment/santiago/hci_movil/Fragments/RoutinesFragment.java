package com.bassanidevelopment.santiago.hci_movil.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bassanidevelopment.santiago.hci_movil.R;

public class RoutinesFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_routines, container, false);
        Toolbar myToolbar;
        myToolbar = getActivity().findViewById(R.id.upper_toolbar);
        myToolbar.setTitle(R.string.routines);
        myToolbar.setNavigationIcon(null);


        return view;
    }


}
