package se.mau.ai0026.mynewproject.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.mau.ai0026.mynewproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndividualEntryFragment extends Fragment {


    public IndividualEntryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_individual_entry, container, false);
        initializeComponents(view);
        return view;
    }

    private void initializeComponents(View view) {

    }

}
