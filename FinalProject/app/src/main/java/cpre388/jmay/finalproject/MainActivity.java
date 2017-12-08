package cpre388.jmay.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.jboss.com.sun.net.httpserver.*;
import org.jboss.com.sun.net.httpserver.Headers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.*;

public class MainActivity extends AppCompatActivity {
    static final String FORWARD_SERVER = "http://hue.jmay.us";
    private static final String TAG = "MainActivity";

    private OkHttpClient client = new OkHttpClient();
    private HueBridge mBridge = new HueBridge();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread t = new Thread(new Network());
        t.start();
    }

    public void onHandle(View v) {
        mBridge.setOn(2, true);
    }

    public void offHandle(View v) {
        mBridge.setOn(2, false);
    }

    private class Network implements Runnable {

        @Override
        public void run() {
            try {
                HttpServer server = HttpServer.create();
                server.bind(new InetSocketAddress(8080), 0);
                server.createContext("/", new MyHandler());
                server.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            try {
                Request.Builder requestBuilder;
                byte[] responseBytes = "no response".getBytes();
                int status = 200;
                Response response = null;

                Log.v(TAG, "Path: " + getPathAndQuery(exchange.getRequestURI()));
                Log.v(TAG, "Verb: " + exchange.getRequestMethod());
                logHeaders(exchange.getRequestHeaders());

                requestBuilder = new Request.Builder()
                        .url(FORWARD_SERVER + getPathAndQuery(exchange.getRequestURI()));
                copyHeaders(exchange.getRequestHeaders(), requestBuilder);

                // Copy request body
                RequestBody requestBodyObj = null;
                if (exchange.getRequestHeaders().containsKey("Content-Length") &&
                        exchange.getRequestHeaders().containsKey("Content-Type")) {
                    MediaType contentType = MediaType.parse(
                            exchange.getRequestHeaders().getFirst("Content-Type"));
                    int contentLen = Integer.parseInt(
                            exchange.getRequestHeaders().getFirst("Content-Length"));
                    byte[] requestBody = new byte[contentLen];

                    if (exchange.getRequestBody().read(requestBody, 0, contentLen) != contentLen) {
                        Log.w(TAG, "Did not read enough content in POST data");
                    }
                    requestBodyObj = RequestBody.create(contentType, requestBody);
                }

                // Set the verb and send the request body, if needed.
                switch (exchange.getRequestMethod()) {
                    case "DELETE":
                        if (requestBodyObj == null) {
                            requestBuilder.delete();
                        } else {
                            requestBuilder.delete(requestBodyObj);
                        }
                        break;
                    case "PUT":
                        if (requestBodyObj == null) {
                            Log.e(TAG, "Request body was empty on a PUT.");
                            return;
                        }
                        requestBuilder.put(requestBodyObj);
                        break;
                    case "POST":
                        if (requestBodyObj == null) {
                            Log.e(TAG, "Request body was empty on a POST.");
                            return;
                        }
                        requestBuilder.post(requestBodyObj);
                        break;
                    case "GET":
                        break;
                    default:
                        responseBytes = "Invalid verb".getBytes();
                        exchange.sendResponseHeaders(status, responseBytes.length);
                        exchange.getResponseBody().write(responseBytes);
                        exchange.close();
                        return;
                }

                // Execute request to Bridge.
                response = client.newCall(requestBuilder.build()).execute();
                Log.v(TAG, "Relayed status: " + response.code());
                status = response.code();
                logHeaders(response.headers());
                copyHeaders(response.headers(), exchange.getResponseHeaders());
                ResponseBody body = response.body();
                if (body != null) {
                    byte[] tempBody = body.bytes();
                    Log.v(TAG, "Relay response size: " + tempBody.length);
                    responseBytes = tempBody;
                } else {
                    Log.v(TAG, "Relay response is empty");
                }

                // Send data to phone.
                exchange.sendResponseHeaders(status, responseBytes.length);
                exchange.getResponseBody().write(responseBytes);
                exchange.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void logHeaders(okhttp3.Headers headers) {
        Log.v(TAG, "Response headers:");
        for (String key : headers.names()) {
            for (String value : headers.values(key)) {
                Log.v(TAG, String.format(Locale.getDefault(), "%s: %s", key, value));
            }
        }
    }

    private static void logHeaders(Headers headers) {
        Log.v(TAG, "Request headers:");
        for (Map.Entry<String, List<String>> header : headers.entrySet()) {
            for (String value : header.getValue()) {
                Log.v(TAG, String.format(Locale.getDefault(), "%s: %s", header.getKey(), value));
            }
        }
    }

    private static String getPathAndQuery(URI uri) {
        if (uri.getQuery() != null) {
            return String.format(Locale.getDefault(), "%s?%s", uri.getPath(), uri.getQuery());
        }
        return uri.getPath();
    }

    private static void copyHeaders(Headers src, Request.Builder dst) {
        for (Map.Entry<String, List<String>> header : src.entrySet()) {
            // The host header should not be copied.
            switch (header.getKey().toLowerCase()) {
                default:
                    for (String value : header.getValue()) {
                        dst.addHeader(header.getKey(), value);
                    }
                    break;
                case "host":
            }
        }
    }

    private static void copyHeaders(okhttp3.Headers src, Headers dst) {
        for (String key : src.names()) {
            for (String value : src.values(key)) {
                dst.add(key, value);
            }
        }
    }
}
