package cpre388.jmay.finalproject;

import android.util.JsonReader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by jmay on 2017-12-08.
 */

public class HueBridge {
    private static final String HUE_USERNAME = "vDgK5rxfSRxEgYv07PbAXDLbcrdikbixluFP-nGT";
    private static final String HUE_URL = MainActivity.FORWARD_SERVER;
    private static final String TAG = "HueBridge";
    private static final MediaType JSON_MEDIA_TYPE
            = MediaType.parse("application/json; charset=utf-8");
    public static final OkHttpClient client = new OkHttpClient();
    private ThreadPoolExecutor mThreadPoolExecutor;

    public HueBridge() {
        mThreadPoolExecutor = new ThreadPoolExecutor(1, 1, 100, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }

    public void setOn(int light, boolean on) {
        String jsonBody = String.format(Locale.getDefault(), "{\"on\": %s}", Boolean.toString(on));
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, jsonBody);
        final Request request = new Request.Builder()
                .url(String.format(Locale.getDefault(), "%s/api/%s/lights/%d/state",
                        HUE_URL, HUE_USERNAME, light))
                .put(body)
                .build();
        mThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Range: 0 <-> 254
    public void setBrightness(int light, int brightness) {
        Log.v(TAG, "Setting brightness: " + brightness);
        if (brightness < 0) { brightness = 0; }
        if (brightness > 254) { brightness = 254; }
        String jsonBody = String.format(Locale.getDefault(), "{\"bri\": %d}", brightness);
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, jsonBody);
        final Request request = new Request.Builder()
                .url(String.format(Locale.getDefault(), "%s/api/%s/lights/%d/state",
                        HUE_URL, HUE_USERNAME, light))
                .put(body)
                .build();
        mThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // Rage: 500 <-> 153
    public void setTemperature(int light, int temperature) {
        if (temperature < 153) { temperature = 153; }
        if (temperature > 500) { temperature = 500; }
        String jsonBody = String.format(Locale.getDefault(), "{\"ct\": %d}", temperature);
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, jsonBody);
        final Request request = new Request.Builder()
                .url(String.format(Locale.getDefault(), "%s/api/%s/lights/%d/state",
                        HUE_URL, HUE_USERNAME, light))
                .put(body)
                .build();
        mThreadPoolExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getState(int light, ILightStateCallback callback) {
        mThreadPoolExecutor.execute(new doGetState(light, callback));
    }

    private class doGetState implements Runnable {
        private ILightStateCallback mCallback;
        private int mLight;

        doGetState(int light, ILightStateCallback callback) {
            mCallback = callback;
            mLight = light;
        }

        @Override
        public void run() {
            Response response;
            ResponseBody bodyObj;
            JSONObject statusJson;
            LightState out;

            Request request = new Request.Builder()
                    .url(String.format(Locale.getDefault(), "%s/api/%s/lights/%d",
                            HUE_URL, HUE_USERNAME, mLight))
                    .build();
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            if (response.code() != 200) {
                Log.e(TAG, "Not 200 status on status request");
                return;
            }
            bodyObj = response.body();
            if (bodyObj == null) {
                Log.e(TAG, "No response body to status request");
                return;
            }
            try {
                statusJson = new JSONObject(bodyObj.string()).getJSONObject("state");
            } catch (JSONException | IOException e) {
                e.printStackTrace();
                return;
            }
            try {
                out = new LightState(
                        statusJson.getBoolean("on"),
                        statusJson.getInt("bri"),
                        statusJson.getInt("ct")
                );
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
            mCallback.receiveLightState(mLight, out);
        }
    }

    interface ILightStateCallback {
        void receiveLightState(int light, LightState lightState);
    }

    class LightState {
        boolean on;
        int brightness;
        int temperature;

        LightState(boolean on, int brightness, int temperature) {
            this.on = on;
            this.brightness = brightness;
            this.temperature = temperature;
        }
    }
}
