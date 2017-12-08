package cpre388.jmay.finalproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.jboss.com.sun.net.httpserver.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread t = new Thread(new Network());
        t.start();
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
            String response = "Hello world!";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.close();
        }
    }
}
