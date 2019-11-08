package se.mau.ai0026.mynewproject.Fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;
import se.mau.ai0026.mynewproject.Adapters.EntriesAdapter;
import se.mau.ai0026.mynewproject.Controller.Controller;
import se.mau.ai0026.mynewproject.Entities.DataStepModel;
import se.mau.ai0026.mynewproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepFragment extends Fragment {
    private RecyclerView rvStepEntries;
    private EntriesAdapter entriesAdapter;
    private TextView tvTotalSteps;
    private TextView tvStepsPerSecond;
    private Button btnResetSteps;
    private int stepsTotal;
    private int secondsPassed;
    private Controller controller;
    private List<DataStepModel> listOfEntries;

    public StepFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step, container, false);
        initializeComponents(view);
        setListOfEntries(controller.getEntries());
        return view;
    }

    private void initializeComponents(View view) {
        //List of entries could be null if there is no entries
        entriesAdapter = new EntriesAdapter(getActivity(), listOfEntries);
        rvStepEntries = (RecyclerView) view.findViewById(R.id.rv_entries);
        rvStepEntries.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvStepEntries.setAdapter(entriesAdapter);

        tvTotalSteps = (TextView) view.findViewById(R.id.tv_total_steps);
        tvStepsPerSecond = (TextView) view.findViewById(R.id.tv_steps_second);
        btnResetSteps = (Button) view.findViewById(R.id.btn_reset);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setListOfEntries(List<DataStepModel> listOfEntries) {
        if(listOfEntries != null) {
            this.listOfEntries = listOfEntries;
            entriesAdapter.setContent(listOfEntries);
            entriesAdapter.notifyDataSetChanged();
        }
    }

    public void setTvTotalSteps(int stepsTotal) {
        this.stepsTotal = stepsTotal;
        tvTotalSteps.setText(String.valueOf(stepsTotal));
    }

    public int getTotalSteps() {
        return stepsTotal;
    }

    public void setTvStepsPerSecond(int secondsPassed) {
        this.secondsPassed = secondsPassed;
        Log.d("SECONDS PASSED:", String.valueOf(secondsPassed));
        //tvStepsPerSecond.setText(calculateStepsPerSecond());
        tvStepsPerSecond.setText(String.valueOf(secondsPassed));
    }

    public int getSecondsPassed() {
        return secondsPassed;
    }

    public int calculateStepsPerSecond() {
        return (stepsTotal / secondsPassed);
    }

    private void populate() {
        listOfEntries = controller.getEntries();
        entriesAdapter.notifyDataSetChanged();
    }

}
