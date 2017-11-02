package cpre388.jmay.homework4;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class RunActivity extends AppCompatActivity {
    private int mInitPoolSize;
    private int mMaxPoolSize;
    private int mKeepAlive;
    private TimeUnit mKeepAliveTimeUnit;
    private int mTotalTasks;

    private int mCompletedTasks = 0;
    private BlockingQueue<Runnable> mRunnableQueue = new LinkedBlockingQueue<>();

    private TextView mQueueSizeTextView;
    private TextView mWorkerCountTextView;
    private TextView mCompleteCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);

        mQueueSizeTextView = (TextView) findViewById(R.id.queue_size);
        mWorkerCountTextView = (TextView) findViewById(R.id.worker_count);
        mCompleteCountTextView = (TextView) findViewById(R.id.complete_count);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mInitPoolSize = Integer.getInteger(
                sharedPreferences.getString(SettingsActivity.KEY_INITIAL_POOL_SIZE, null), 5);
        mMaxPoolSize = Integer.getInteger(
                sharedPreferences.getString(SettingsActivity.KEY_MAX_POOL_SIZE, null), 5);
        mKeepAlive = Integer.getInteger(
                sharedPreferences.getString(SettingsActivity.KEY_KEEPALIVE, null), 1);
        mKeepAliveTimeUnit = TimeUnit.valueOf(
                sharedPreferences.getString(SettingsActivity.KEY_KEEPALIVE_TIMEUNIT, "Seconds")
                    .toUpperCase());
        mTotalTasks = Integer.getInteger(
                sharedPreferences.getString(SettingsActivity.KEY_NUM_TASKS, null), 200);

        updateUI();
    }

    private void updateUI() {
        mQueueSizeTextView.setText(Integer.toString(mRunnableQueue.size()));
        mWorkerCountTextView.setText("TODO");
        mCompleteCountTextView.setText(Integer.toString(mCompletedTasks));
    }
}
