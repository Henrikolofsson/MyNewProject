package se.mau.ai0026.mynewproject.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.mau.ai0026.mynewproject.Controller.Controller;
import se.mau.ai0026.mynewproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompassFragment extends Fragment {
    private Controller controller;

    public CompassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_compass, container, false);
        return view;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
