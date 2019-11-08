package se.mau.ai0026.mynewproject.Services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import se.mau.ai0026.mynewproject.MainActivity;

public class StepService extends Service implements SensorEventListener {
    private static final String TAG = "StepService";
    private MainActivity activity;
    private final IBinder mBinder = new StepService.StepServiceBinder();
    public int counter = 0;
    public int steps = 0;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        activity.update();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void setListenerActivity(MainActivity activity) {
        this.activity = activity;
    }


    public class StepServiceBinder extends Binder {

        StepService getService() {
            return StepService.this;
        }
    }

    public void startServiceTasks() {
        Log.d(TAG, "TIMER");
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                printTime();
                counter++;
                activity.setCounter(counter);
            }
        }, 1000, 1000);

    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return counter;
    }

    public void printTime() {
        Log.d(TAG, "TIMER");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        stopSelf();
    }

}

