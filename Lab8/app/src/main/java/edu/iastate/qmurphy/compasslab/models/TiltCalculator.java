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

public class TiltCalculator implements SensorEventListener {
    private static final String TAG = "TiltCalculator";

    private SensorManager mSensorManager;
    private Sensor mAcc;
    private SensorUpdateCallback mCallback;

    public TiltCalculator(Context context, SensorUpdateCallback callback) throws Exception {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mCallback = callback;

        if (mAcc == null) {
            Log.e(TAG, "Cannot find a magnetic sensor");
            throw new Exception("Cannot find a magnetic sensor");
        }
    }

    public void start() {
        mSensorManager.registerListener(this, mAcc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float orientation = 0.0f;
        double pitch = 0; // TODO Determine pitch from accelerometer
        double roll = 0; // TODO Determine roll from accelerometer
        orientation = 0; // TODO Determine orientation from pitch and roll

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            double sqrtx2y2 = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
            if (sqrtx2y2 != 0.0d) {
                pitch = Math.atan(y / sqrtx2y2);
            }
            if (z != 0) {
                roll = Math.atan(-x / z);
            }
            orientation = (float) (Math.atan2(pitch, roll) * 180 / Math.PI) + 90.0f;
        }
        mCallback.update(orientation);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
