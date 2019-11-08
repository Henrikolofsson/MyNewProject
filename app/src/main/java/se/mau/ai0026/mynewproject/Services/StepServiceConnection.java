package se.mau.ai0026.mynewproject.Services;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import se.mau.ai0026.mynewproject.MainActivity;

public class StepServiceConnection implements ServiceConnection {
    private final MainActivity activity;

    public StepServiceConnection(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        StepService.StepServiceBinder binder = (StepService.StepServiceBinder) service;
        activity.myService = binder.getService();
        activity.serviceBound = true;
        activity.myService.setListenerActivity(activity);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        activity.serviceBound = false;
    }
}
