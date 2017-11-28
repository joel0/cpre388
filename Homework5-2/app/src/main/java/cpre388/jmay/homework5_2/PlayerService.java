package cpre388.jmay.homework5_2;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.widget.Toast;

import java.lang.annotation.Annotation;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PlayerService extends Service {
    private static final String ACTION_PLAY = "cpre388.jmay.homework5_2.action.PLAY";
    private static final String ACTION_PAUSE = "cpre388.jmay.homework5_2.action.PAUSE";
    private static final String ACTION_STOP = "cpre388.jmay.homework5_2.action.STOP";
    private static final String ACTION_REQUEST_TRACK_INFO = "cpre388.jmay.homework5_2.action.REQUEST_TRACK_INFO";

    private static final String BROADCAST_TRACK_INFO = "cpre388.jmay.homework5_2.broadcast.TRACK_INFO";

    // TODO: Rename parameters
    private static final String EXTRA_SONG_TITLE = "cpre388.jmay.homework5_2.extra.SONG_TITLE";

    private MediaPlayer mPlayer = null;
    private String mCurrentSong = "<none>";

    public PlayerService() {
    }

    /**
     * TODO Update description
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionPlay(Context context) {
        Intent intent = new Intent(context, PlayerService.class);
        intent.setAction(ACTION_PLAY);
        //intent.putExtra(EXTRA_PARAM1, param1);
        context.startService(intent);
    }

    /**
     * TODO: Update description
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionPause(Context context) {
        Intent intent = new Intent(context, PlayerService.class);
        intent.setAction(ACTION_PAUSE);
        context.startService(intent);
    }

    /**
     * TODO: Update description
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionStop(Context context) {
        Intent intent = new Intent(context, PlayerService.class);
        intent.setAction(ACTION_STOP);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onHandleIntent(intent);
        return START_STICKY;
    }

    public void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_PLAY.equals(action)) {
                //final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleActionPlay();
            } else if (ACTION_PAUSE.equals(action)) {
                handleActionPause();
            } else if (ACTION_STOP.equals(action)) {
                handleActionStop();
            } else if (ACTION_REQUEST_TRACK_INFO.equals(action)) {
                broadcastTrackInfo();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Toast.makeText(this, "Starting service", Toast.LENGTH_SHORT).show();
        mPlayer = MediaPlayer.create(this, R.raw.music);
    }

    /**
     * TODO: Update description
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionPlay() {
        mPlayer.start();
        mCurrentSong = "music";
        broadcastTrackInfo();
    }

    /**
     * TODO: Update description
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionPause() {
        mPlayer.pause();
        broadcastTrackInfo();
    }

    /**
     * TODO: Update description
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionStop() {
        mPlayer.stop();
        mCurrentSong = "<none>";
        broadcastTrackInfo();
        stopSelf();
    }

    private void broadcastTrackInfo() {
        Toast.makeText(this, "Requested track info", Toast.LENGTH_SHORT).show();
        // TODO
        Intent intent = new Intent(BROADCAST_TRACK_INFO);
        intent.putExtra(EXTRA_SONG_TITLE, mCurrentSong);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPlayer.stop();
        Toast.makeText(this, "Stopping service", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}