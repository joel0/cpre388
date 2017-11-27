package cpre388.jmay.homework5musicplayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PlayerActivity extends AppCompatActivity {
    private static final String PACKAGE_PLAYER = "cpre388.jmay.homework5_2";
    
    private static final String ACTION_PLAY = "cpre388.jmay.homework5_2.action.PLAY";
    private static final String ACTION_PAUSE = "cpre388.jmay.homework5_2.action.PAUSE";
    private static final String ACTION_STOP = "cpre388.jmay.homework5_2.action.STOP";

    private static final String BROADCAST_TRACK_INFO = "cpre388.jmay.homework5_2.broadcast.TRACK_INFO";

    private BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        mBroadcastReceiver = new TrackInfoBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter(BROADCAST_TRACK_INFO);
        registerReceiver(mBroadcastReceiver, filter);
    }

    private class TrackInfoBroadcastReceiver extends BroadcastReceiver {
        private Context mContext;

        public TrackInfoBroadcastReceiver(Context activityContext) {
            mContext = activityContext;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO
            Toast.makeText(mContext, "Received track info", Toast.LENGTH_SHORT).show();
        }
    }

    public void onPlayClick(View v) {
        Intent intent = new Intent(ACTION_PLAY);
        intent.setPackage(PACKAGE_PLAYER);
        startService(intent);
    }

    public void onPauseClick(View v) {
        Intent intent = new Intent(ACTION_PAUSE);
        intent.setPackage(PACKAGE_PLAYER);
        startService(intent);
    }

    public void onStopClick(View v) {
        Intent intent = new Intent(ACTION_STOP);
        intent.setPackage(PACKAGE_PLAYER);
        startService(intent);
    }
}
