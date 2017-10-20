package edu.iastate.qmurphy.compasslab.models;

import android.app.Service;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import edu.iastate.qmurphy.compasslab.interfaces.SensorUpdateCallback;

/**
 * Created by Quinn on 10/23/2016.
 */

public class FlatCompass implements SensorEventListener {
    private static final String TAG = "FlatCompass";

    private SensorManager mSensorManager;
    private Sensor mMagField;
    private SensorUpdateCallback mCallback;

    public FlatCompass(Context context, SensorUpdateCallback callback) throws Exception {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mMagField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mCallback = callback;

        if (mMagField == null) {
            Log.e(TAG, "Cannot find a magnetic sensor");
            throw new Exception("Cannot find a magnetic sensor");
        }
    }

    public void start() {
        mSensorManager.registerListener(this, mMagField, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float orientation = 0.0f;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            float x = event.values[0];
            float y = event.values[1];
            if (y != 0) {
                orientation = (float) (Math.atan2(x, y) * 180 / Math.PI);
            } else {
                orientation = x;
            }
        }
        mCallback.update(orientation);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
