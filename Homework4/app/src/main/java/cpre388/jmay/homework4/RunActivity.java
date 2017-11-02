package cpre388.jmay.homework4;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RunActivity extends AppCompatActivity {
    private static final int TASK_COMPLETE = 1;
    private int mInitPoolSize;
    private int mMaxPoolSize;
    private int mKeepAlive;
    private TimeUnit mKeepAliveTimeUnit;
    private int mTotalTasks;

    private BlockingQueue<Runnable> mRunnableQueue = new LinkedBlockingQueue<>();
    private ThreadPoolExecutor mThreadPoolExecutor;
    private Handler mHandler;

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
        mInitPoolSize = Integer.parseInt(
                sharedPreferences.getString(SettingsActivity.KEY_INITIAL_POOL_SIZE, "5"));
        mMaxPoolSize = Integer.parseInt(
                sharedPreferences.getString(SettingsActivity.KEY_MAX_POOL_SIZE, "5"));
        mKeepAlive = Integer.parseInt(
                sharedPreferences.getString(SettingsActivity.KEY_KEEPALIVE, "1"));
        mKeepAliveTimeUnit = TimeUnit.valueOf(
                sharedPreferences.getString(SettingsActivity.KEY_KEEPALIVE_TIMEUNIT, "Seconds")
                    .toUpperCase());
        mTotalTasks = Integer.parseInt(
                sharedPreferences.getString(SettingsActivity.KEY_NUM_TASKS, "200"));

        createThreadPool();
        updateUI();
    }

    private void createThreadPool() {
        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == TASK_COMPLETE) {
                    updateUI();
                } else {
                    super.handleMessage(msg);
                }
            }
        };

        mThreadPoolExecutor = new ThreadPoolExecutor(
                mInitPoolSize,
                mMaxPoolSize,
                mKeepAlive,
                mKeepAliveTimeUnit,
                mRunnableQueue);
        for (int i = 0; i < mTotalTasks; i++) {
            mThreadPoolExecutor.execute(new BusyRunnable(this));
        }
    }

    private void updateUI() {
        mQueueSizeTextView.setText(Integer.toString(mRunnableQueue.size()));
        mWorkerCountTextView.setText(String.format("%d/%d/%d",
                mThreadPoolExecutor.getActiveCount(),
                mThreadPoolExecutor.getPoolSize(),
                mMaxPoolSize));
        mCompleteCountTextView.setText(Long.toString(mThreadPoolExecutor.getCompletedTaskCount()));
    }

    public void taskEnded() {
        Message completeMessage = mHandler.obtainMessage(TASK_COMPLETE);
        completeMessage.sendToTarget();
    }

    private void stopAll() {
        BusyRunnable[] runnables = new BusyRunnable[mRunnableQueue.size()];
        mRunnableQueue.toArray(runnables);
        synchronized (this) {
            for (int i = 0; i < runnables.length; i++) {
                Thread t = runnables[i].mThread;
                if (t != null) {
                    t.interrupt();
                }
            }
        }
    }

    public void stopClicked(View v) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        stopAll();
        super.onBackPressed();
    }
}
