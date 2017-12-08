package cpre388.jmay.finalproject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by jmay on 2017-12-08.
 */

public class HueBridge {
    private static final String HUE_USERNAME = "vDgK5rxfSRxEgYv07PbAXDLbcrdikbixluFP-nGT";
    private static final String HUE_URL = MainActivity.FORWARD_SERVER;
    private static final MediaType JSON_MEDIA_TYPE
            = MediaType.parse("application/json; charset=utf-8");
    private OkHttpClient client = new OkHttpClient();

    public void setOn(int light, boolean on) {
        String jsonBody = String.format(Locale.getDefault(), "{\"on\": %s}", Boolean.toString(on));
        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, jsonBody);
        final Request request = new Request.Builder()
                .url(String.format(Locale.getDefault(), "%s/api/%s/lights/%d/state",
                        HUE_URL, HUE_USERNAME, light))
                .put(body)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
