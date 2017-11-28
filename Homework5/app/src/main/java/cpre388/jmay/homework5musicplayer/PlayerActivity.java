package cpre388.jmay.homework5musicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class PlayerActivity extends AppCompatActivity {
    private static final String PACKAGE_PLAYER = "cpre388.jmay.homework5_2";
    
    private static final String ACTION_PLAY = "cpre388.jmay.homework5_2.action.PLAY";
    private static final String ACTION_PAUSE = "cpre388.jmay.homework5_2.action.PAUSE";
    private static final String ACTION_STOP = "cpre388.jmay.homework5_2.action.STOP";
    private static final String ACTION_REQUEST_TRACK_INFO = "cpre388.jmay.homework5_2.action.REQUEST_TRACK_INFO";

    private static final String BROADCAST_TRACK_INFO = "cpre388.jmay.homework5_2.broadcast.TRACK_INFO";

    private static final String EXTRA_SONG_TITLE = "cpre388.jmay.homework5_2.extra.SONG_TITLE";

    private BroadcastReceiver mBroadcastReceiver;
    private IntentFilter mBroadcastFilter;
    private Handler mSongInfoHandler;
    private TextView mCurrentSongTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mSongInfoHandler = new Handler(getMainLooper());
        mBroadcastReceiver = new TrackInfoBroadcastReceiver(this, mSongInfoHandler);
        mBroadcastFilter = new IntentFilter(BROADCAST_TRACK_INFO);
        mCurrentSongTextView = (TextView) findViewById(R.id.currentSongTextView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(mBroadcastReceiver, mBroadcastFilter);
        requestTrackInfo();
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(mBroadcastReceiver);
    }

    private class TrackInfoBroadcastReceiver extends BroadcastReceiver {
        private Context mContext;
        private Handler mHandler;

        public TrackInfoBroadcastReceiver(Context activityContext, Handler songInfoCallback) {
            mContext = activityContext;
            mHandler = songInfoCallback;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO
            final String songTitle = intent.getStringExtra(EXTRA_SONG_TITLE);
            // Toast.makeText(mContext, "Received track info: " + songTitle, Toast.LENGTH_SHORT).show();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mCurrentSongTextView.setText(songTitle);
                }
            });
        }
    }

    public void onPlayClick(View v) {
        sendIntentToPlayer(ACTION_PLAY);
    }

    public void onPauseClick(View v) {
        sendIntentToPlayer(ACTION_PAUSE);
    }

    public void onStopClick(View v) {
        sendIntentToPlayer(ACTION_STOP);
    }

    private void requestTrackInfo() {
        sendIntentToPlayer(ACTION_REQUEST_TRACK_INFO);
    }

    private void sendIntentToPlayer(String action) {
        Intent intent = new Intent(action);
        intent.setPackage(PACKAGE_PLAYER);
        startService(intent);
    }
}
