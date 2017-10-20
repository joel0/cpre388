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

public class BetterCompass implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mMagField;
    private Sensor mAcc;
    private SensorUpdateCallback mCallback;

    private float[] mAccelerometerReading = new float[3];
    private float[] mMagnetometerReading = new float[3];

    public BetterCompass(Context context, SensorUpdateCallback callback) {
        mSensorManager = null; // TODO Get the Sensor Service using the application context
        mMagField = null; // TODO Get a magnetic field sensor
        mAcc = null; // TODO Get an accelerometer
        mCallback = callback;
    }

    public void start() {
        // TODO Register listeners
    }

    public void stop() {
        // TODO Unregister listeners
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            // TODO Store magnetic field data in mMagnetometerReading
        }
        else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // TODO Store accelerometer data in mAccelerometerReading
        }

        // TODO Get orientation from magnetometer and accelerometer

        float orientation = 0.0f;

        mCallback.update(orientation);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }
}
