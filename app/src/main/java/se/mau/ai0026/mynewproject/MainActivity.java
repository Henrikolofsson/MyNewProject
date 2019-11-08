package se.mau.ai0026.mynewproject;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import se.mau.ai0026.mynewproject.Controller.Controller;
import se.mau.ai0026.mynewproject.Interface.ChangeListener;
import se.mau.ai0026.mynewproject.Services.StepService;
import se.mau.ai0026.mynewproject.Services.StepServiceConnection;

public class MainActivity extends AppCompatActivity implements ChangeListener {
    private FragmentManager fragmentManager;
    private Controller controller;
    public StepService myService = new StepService();
    public boolean serviceBound = false;
    private SensorManager mSensorManager;
    private Sensor mSensor;
    public boolean isStepSensorPresent;
    private StepServiceConnection mServiceConnection;
    private int secondsPassed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpFragmentManager();
        setUpSensorManager();
        setUpController();
        setUpServiceConnection();
    }

    private void setUpFragmentManager() {
        fragmentManager = getSupportFragmentManager();
    }

    private void setUpSensorManager() {
        mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        if(mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isStepSensorPresent = true;
        } else {
            isStepSensorPresent = false;
        }
    }

    private void setUpController() {
        controller = new Controller(this);
    }

    private void setUpServiceConnection() {
        mServiceConnection = new StepServiceConnection(this);
    }

    public void setFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.main_container, fragment, tag);
        ft.commit();
    }

    public Fragment getFragment(String tag) {
        return fragmentManager.findFragmentByTag(tag);
    }

    public FragmentManager getFragmentManagerFromActivity() {
        return fragmentManager;
    }

    public int getCounter() {
        return myService.getCounter();
    }

    public void setCounter(int counter) {
        secondsPassed = counter;
        controller.setSecondsPassed(counter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
        serviceBound = false;

        if(isStepSensorPresent) {
            mSensorManager.unregisterListener(myService);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isStepSensorPresent) {
            mSensorManager.registerListener(myService, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, StepService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void startService(){
        Intent serviceIntent = new Intent(this, StepService.class);
        startService(serviceIntent);

        bindService();
        controller.checkIfDateTodayExists();
        myService.startServiceTasks();
    }

    public void bindService(){
        Intent serviceBindIntent = new Intent(this, StepService.class);
        bindService(serviceBindIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void update() {
        controller.incrementSteps();
        controller.getStepsFromDatabase();
    }

}
