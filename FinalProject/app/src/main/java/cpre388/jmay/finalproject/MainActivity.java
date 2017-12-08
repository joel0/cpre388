package cpre388.jmay.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.*;

public class MainActivity extends AppCompatActivity implements HueBridge.ILightStateCallback, SensorEventListener {
    static final String FORWARD_SERVER = "https://hue.jmay.us";
    private static final String TAG = "MainActivity";
    private final float PROXIMITY_MAX = 5.0f;
    private final float BRIGHTNESS_MAX = 255.0f;

    public static final OkHttpClient client = new OkHttpClient();
    private HueBridge mBridge = new HueBridge();
    private Handler mHandler;
    private TextView mStatusTextView;
    private DrawerLayout mDrawerLayout;
    private ListView mNavList;
    private ActionBarDrawerToggle mDrawerToggle;
    private SharedPreferences mSharedPreferences;

    private SensorManager mSensorManager;
    private Sensor mLight;
    private Sensor mProximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        mNavList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mStatusTextView = (TextView) findViewById(R.id.statusTextView);
        mHandler = new Handler(getMainLooper());

        mNavList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new String[] {"Settings"}));
        mNavList.setOnItemClickListener(new DrawerClickHandler());
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        fetchLightStatus();
    }

    private void updatePreferences() {
        boolean startWithOs = mSharedPreferences.getBoolean("start_with_os", false);
        if (startWithOs) {
            Toast.makeText(this, "Starting relay service", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, RelayService.class);
            startService(intent);
        }
    }

    public void iptablesHandle(View view) throws IOException, InterruptedException {
        Process su = Runtime.getRuntime().exec("su");
        DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

        outputStream.writeBytes("iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080\n");
        outputStream.flush();
        outputStream.writeBytes("iptables -t nat -I OUTPUT -p tcp -d 127.0.0.1 --dport 80 -j REDIRECT --to-ports 8080\n");
        outputStream.flush();
        /*Runtime.getRuntime().exec(new String[] {
                "su -c \"iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080\"",
                "su -c \"iptables -t nat -I OUTPUT -p tcp -d 127.0.0.1 --dport 80 -j REDIRECT --to-ports 8080\""
        });*/
    }

    private class DrawerClickHandler implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                // Settings
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mProximity, SensorManager.SENSOR_DELAY_NORMAL);
        updatePreferences();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onHandle(View v) {
        mBridge.setOn(2, true);
        fetchLightStatus();
    }

    public void offHandle(View v) {
        mBridge.setOn(2, false);
        fetchLightStatus();
    }

    private void fetchLightStatus() {
        mBridge.getState(2, this);
    }

    @Override
    public void receiveLightState(int light, final HueBridge.LightState lightState) {
        Log.v(TAG, "Got status of light " + light);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mStatusTextView.setText(String.format(Locale.getDefault(),
                        "on %s\nbrightness %d\ncolor %d",
                        Boolean.toString(lightState.on),
                        lightState.brightness, lightState.temperature));
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float v;
        switch (event.sensor.getType()) {
            case Sensor.TYPE_LIGHT:
                Log.v(TAG, "Light sensor: " + event.values[0]);
                v = Math.min(event.values[0], BRIGHTNESS_MAX);
                mBridge.setBrightness(2, (int) (v / BRIGHTNESS_MAX * 254));
                break;
            case Sensor.TYPE_PROXIMITY:
                Log.v(TAG, "Proximity sensor: " + event.values[0]);
                v = Math.min(event.values[0], PROXIMITY_MAX);
                mBridge.setTemperature(2, (int) (v / PROXIMITY_MAX * 347.0f + 153.0f));
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing
    }
}
