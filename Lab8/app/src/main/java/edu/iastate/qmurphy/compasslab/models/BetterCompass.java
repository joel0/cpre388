package edu.iastate.qmurphy.compasslab.models;

import android.app.Service;
import android.app.admin.SystemUpdatePolicy;
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

    private static final int HISTORY_COUNT = 50;
    private float[] mRecentOrientations = new float[HISTORY_COUNT];
    private boolean mFirstOrientationReading = true;

    public BetterCompass(Context context, SensorUpdateCallback callback) throws Exception {
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mMagField = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mAcc = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mCallback = callback;

        if (mMagField == null) {
            throw new Exception("Cannot find magnetic sensor");
        }
        if (mAcc == null) {
            throw new Exception("Cannot find accelerometer");
        }
    }

    public void start() {
        mSensorManager.registerListener(this, mMagField, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mAcc, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mMagnetometerReading, 0, mMagnetometerReading.length);
        }
        else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mAccelerometerReading, 0, mAccelerometerReading.length);
        }

        float orientation;
        float[] rotationMatrix = new float[9];
        float[] orientationAngles = new float[3];
        SensorManager.getRotationMatrix(rotationMatrix, null,
                mAccelerometerReading, mMagnetometerReading);
        SensorManager.getOrientation(rotationMatrix, orientationAngles);
        orientation = -(float) Math.toDegrees(orientationAngles[0]);

        if (mFirstOrientationReading) {
            for (int i = 0; i < mRecentOrientations.length; i++) {
                mRecentOrientations[i] = orientation;
            }
        } else {
            // Left shift the array
            System.arraycopy(mRecentOrientations, 1, mRecentOrientations,
                    0, mRecentOrientations.length - 1);
            mRecentOrientations[mRecentOrientations.length + 1] = orientation;
        }
        orientation = 0;
        for (int i = 0; i < mRecentOrientations.length; i++) {
            orientation += mRecentOrientations[i];
        }
        orientation = orientation / mRecentOrientations.length;

        mCallback.update(orientation);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }
}
