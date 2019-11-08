package se.mau.ai0026.mynewproject.Fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.mau.ai0026.mynewproject.Controller.Controller;
import se.mau.ai0026.mynewproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private Controller controller;
    private TabLayout tablayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private StepFragment stepFragment;
    private CompassFragment compassFragment;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initializeComponents(view);
        addFragmentsToAdapter();
        setViewPagerAdapter();
        controller.startService();
        return view;
    }

    private void initializeComponents(View view) {
        tablayout = (TabLayout) view.findViewById(R.id.tab_layout);
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(controller.getFragmentManager());
    }

    private void initializeStepFragment() {
        stepFragment = (StepFragment) controller.getFragment("StepFragment");
        if(stepFragment == null) {
            stepFragment = new StepFragment();
            stepFragment.setController(controller);
        }
    }

    private void initializeCompassFragment() {
        compassFragment = (CompassFragment) controller.getFragment("CompassFragment");
        if(compassFragment == null) {
            compassFragment = new CompassFragment();
            compassFragment.setController(controller);
        }
    }

    private void addFragmentsToAdapter() {
        initializeStepFragment();
        initializeCompassFragment();
        adapter.addFragment(stepFragment, "StepFragment");
        adapter.addFragment(compassFragment, "CompassFragment");
    }

    private void setViewPagerAdapter() {
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }

    public void setStepsTotal(int stepsTotal) {
        stepFragment.setTvTotalSteps(stepsTotal);
    }

    public void setSecondsPassed(int secondsPassed) {
        stepFragment.setTvStepsPerSecond(secondsPassed);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
