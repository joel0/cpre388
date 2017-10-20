package edu.iastate.qmurphy.compasslab.models;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import edu.iastate.qmurphy.compasslab.interfaces.SensorUpdateCallback;

/**
 * Created by Quinn on 10/23/2016.
 */

public class TiltCalculator implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAcc;
    private SensorUpdateCallback mCallback;

    public TiltCalculator(Context context, SensorUpdateCallback callback) {
        mSensorManager = null; // TODO Get the Sensor Service using the application context
        mAcc = null; // TODO Get an accelerometer
        mCallback = callback;
    }

    public void start() {
        // TODO Register listener
    }

    public void stop() {
        // TODO Unregister listener
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float orientation = 0.0f;
        double pitch = 0; // TODO Determine pitch from accelerometer
        double roll = 0; // TODO Determine roll from accelerometer
        orientation = 0; // TODO Determine orientation from pitch and roll
        mCallback.update(orientation);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
