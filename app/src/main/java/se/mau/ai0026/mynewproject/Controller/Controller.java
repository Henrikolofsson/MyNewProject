package se.mau.ai0026.mynewproject.Controller;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import se.mau.ai0026.mynewproject.Entities.DataStepModel;
import se.mau.ai0026.mynewproject.Fragments.CompassFragment;
import se.mau.ai0026.mynewproject.Fragments.CreateUser;
import se.mau.ai0026.mynewproject.Fragments.StartFragment;
import se.mau.ai0026.mynewproject.Fragments.MainFragment;
import se.mau.ai0026.mynewproject.MainActivity;

public class Controller {
    private MainActivity activity;
    private DBAccess dbAccess;
    private CreateUser createUserFragment;
    private StartFragment startFragment;
    private MainFragment mainFragment;
    private CompassFragment compassFragment;
    private String username;
    private int userId;
    private int steps;
    private List<DataStepModel> dataStepModelList = new ArrayList<>();
    public String dateToday;
    public boolean dateTodayExist;

    public Controller(MainActivity activity) {
        this.activity = activity;
        initializeDatabase();
        initializeStartFragment();
    }

    private void initializeDatabase() {
        dbAccess = new DBAccess(activity, this);
    }

    private void initializeStartFragment() {
        startFragment = (StartFragment) activity.getFragment("StartFragment");
        if(startFragment == null) {
            startFragment = new StartFragment();
            startFragment.setController(this);
            activity.setFragment(startFragment, "StartFragment");
        }
    }

    private void initializeCreateUserFragment() {
        createUserFragment = (CreateUser) activity.getFragment("CreateUser");
        if(createUserFragment == null) {
            createUserFragment = new CreateUser();
            createUserFragment.setController(this);
            activity.setFragment(createUserFragment, "CreateUser");
        }
    }

    private void initializeMainFragment() {
        mainFragment = (MainFragment) activity.getFragment("MainFragment");
        if(mainFragment == null) {
            mainFragment = new MainFragment();
            mainFragment.setController(this);
        }
    }

    public FragmentManager getFragmentManager() {
        return activity.getFragmentManagerFromActivity();
    }

    public void setStartFragment() {
        activity.setFragment(startFragment, "StartFragment");
    }

    public void setMainFragment() {
        activity.setFragment(mainFragment, "MainFragment");
    }

    public void setCreateUserFragment() {
        initializeCreateUserFragment();
        activity.setFragment(createUserFragment, "CreateUser");
    }

    public void logIn(String username, String password) {
        QueryHandler queryHandler = new QueryHandler(this, dbAccess, username, password);
        queryHandler.state = QueryState.USER_LOGON_ACCEPTED;
        queryHandler.execute();
    }

    public void logInSuccess(String username) {
        this.username = username;
        retrieveDataStepModelFromDatabase();
        checkIfDateTodayExists();
        initializeMainFragment();
        setMainFragment();
    }

    public List<DataStepModel> getEntries() {
        return dataStepModelList;
    }

    public void setDataStepModelList(List<DataStepModel> dataStepModelList) {
        this.dataStepModelList = dataStepModelList;
    }

    public void retrieveDataStepModelFromDatabase() {
        QueryHandler queryHandler = new QueryHandler(this, dbAccess);
        queryHandler.state = QueryState.GET_USER_ENTRIES;
        queryHandler.execute();
    }

    public void checkIfDateTodayExists() {
        dateTodayExist = false;
        dateToday = getTodaysDate();

        if(dataStepModelList != null) {
            for(int i = 0; i < dataStepModelList.size(); i++) {
                if(dataStepModelList.get(i).getDate().equals(dateToday)) {
                    dateTodayExist = true;
                    setSteps(dataStepModelList.get(i).getSteps());
                    break;
                }
            }
        }

        Log.d("STEPSTODAY", String.valueOf(steps));

        if(!dateTodayExist) {
            QueryHandler queryHandler = new QueryHandler(this, dbAccess);
            queryHandler.state = QueryState.ADD_USER_ENTRY;
            queryHandler.execute();
        }

    }

    public void logInFailure() {
        Toast.makeText(activity, "There is no user with that name and password", Toast.LENGTH_LONG).show();
    }

    public void startService() {
        activity.startService();
    }

    public void userInsertedInDatabase(boolean userAdded) {
        if(userAdded) {
            Toast.makeText(activity, "User added", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity, "User already exist", Toast.LENGTH_LONG).show();
        }
    }

    public void insertUserInDatabase(String username, String password) {
        QueryHandler queryHandler = new QueryHandler(this, dbAccess, username, password);
        queryHandler.state = QueryState.ADD_IF_NOT_EXIST;
        queryHandler.execute();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public void incrementSteps() {
        QueryHandler queryHandler = new QueryHandler(this, dbAccess);
        queryHandler.state = QueryState.UPDATE_USER_STEPS;
        queryHandler.execute();
    }

    public void getStepsFromDatabase() {
        QueryHandler queryHandler = new QueryHandler(this, dbAccess);
        queryHandler.state = QueryState.GET_USER_STEPS;
        queryHandler.execute();
    }

    public String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public void setSecondsPassed(int secondsPassed) {
        mainFragment.setSecondsPassed(secondsPassed);
    }

    public void setTotalSteps(int totalSteps) {
        mainFragment.setStepsTotal(totalSteps);
    }

    public Fragment getFragment(String tag) {
        return activity.getFragment(tag);
    }

    public void generateFakeEntries() {
        Random rand = new Random();
        this.username = "Henrik";

        this.steps = rand.nextInt(+5000) + 450;
        QueryHandler queryHandler = new QueryHandler(this, dbAccess);
        queryHandler.state = QueryState.ADD_FAKE_ENTRIES;
        queryHandler.execute();

        this.steps = rand.nextInt(+5000) + 450;
        QueryHandler queryHandler2 = new QueryHandler(this, dbAccess);
        queryHandler2.state = QueryState.ADD_FAKE_ENTRIES2;
        queryHandler2.execute();

        this.steps = rand.nextInt(+5000) + 450;
        QueryHandler queryHandler3 = new QueryHandler(this, dbAccess);
        queryHandler3.state = QueryState.ADD_FAKE_ENTRIES3;
        queryHandler3.execute();

        this.steps = rand.nextInt(+5000) + 450;
        QueryHandler queryHandler4 = new QueryHandler(this, dbAccess);
        queryHandler4.state = QueryState.ADD_FAKE_ENTRIES4;
        queryHandler4.execute();

    }
}
