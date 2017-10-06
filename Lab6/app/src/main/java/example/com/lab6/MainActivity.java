package example.com.lab6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements DownloadWebpageTask.ResultHandler{
    private static final int RESULT_LIMIT = 20;
    private static final String TAG = "MainActivity";
    private EditText mUsername;
    private ItunesAdapter adapter;
    //private ArrayList<ItunesRecord> data = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsername = (EditText) findViewById(R.id.username);

        adapter = new ItunesAdapter(this, R.layout.layout_song, new ArrayList<ItunesRecord>());
        setListAdapter(adapter);

        Button search = (Button)findViewById(R.id.search);
        search.setOnClickListener(new OnClickListener() {

            /* (non-Javadoc)
             * @see android.view.View.OnClickListener#onClick(android.view.View)
             */
            public void onClick(View v) {

                //TODO get the username to search for from the activity_main.xml view
                String artist = mUsername.getText().toString();
                Log.i(TAG, "Artist Searched: " + artist);

                //TODO execute a new DownloadWebpageTask
                AsyncTask<String,Integer,String> task = new DownloadWebpageTask(MainActivity.this);
                String url = Uri.parse("https://itunes.apple.com/search")
                        .buildUpon()
                        .appendQueryParameter("term", mUsername.getText().toString())
                        .appendQueryParameter("entity", "song")
                        .appendQueryParameter("limit", Integer.toString(RESULT_LIMIT))
                        .build().toString();
                task.execute(url);
            }
        });
    }


    @Override
    public void handleResult(String result) {
        //TODO Handle the Result of a Network Call
        Log.i(TAG, "Handling result: " + result);
        JSONObject resultObj;
        adapter.clear();
        try {
            resultObj = new JSONObject(result);
            int resultCount = resultObj.getInt("resultCount");
            for (int i = 0; i < resultCount; i++) {
                JSONObject songJSON = resultObj.getJSONArray("results").getJSONObject(i);
                String albumTitle = songJSON.getString("collectionName");
                String songTitle = songJSON.getString("trackName");
                adapter.add(new ItunesRecord(albumTitle, songTitle));
            }
            Toast.makeText(this, resultCount + " results", Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
