package com.example.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 *
 * Sample Stopwatch Android activity
 *
 */
public class StopWatchActivity extends Activity {

    /**
     * REFRESH_RATE defines how often we should update the timer to show how much time has elapsed.
     * refresh every 100 milliseconds
     */
    private final int REFRESH_RATE = 100;

    private WatchModel watch = new WatchModel();
    private TextView mTimerText;
    private TextView mTimerTextMs;
    private Button mButtonStart;
    private Button mButtonStop;
    private Button mButtonReset;
    
    
    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stopwatch);

        mTimerText = (TextView) findViewById(R.id.timerText);
        mTimerTextMs = (TextView) findViewById(R.id.timerTextMs);
        mButtonStart = (Button) findViewById(R.id.buttonStart);
        mButtonStop = (Button) findViewById(R.id.buttonStop);
        mButtonReset = (Button) findViewById(R.id.buttonReset);
        observer o = new observer();
        watch.addObserver(o);
    }



    /**
     * This method will start the current stopwatch clock
     *
     * @param view the current view
     */
    public void startClick(View view){
        watch.start();
    }
    /**
     * This method will reset the current stopwatch clock
     *
     * @param view the current view
     */
    public void resetClick(View view){
        watch.stop();
    }

    /**
     * This method will stop the current stopwatch.
     *
     * @param view the current view
     */
    public void stopClick(View view){
        watch.stop();
    }

    /**
     * This method will show the stop button when called by hiding the 
     * start and reset button and making the stop button visible.
     */
    private void showStopButton(){
        mButtonStop.setVisibility(View.VISIBLE);
        mButtonReset.setVisibility(View.GONE);
        mButtonStart.setVisibility(View.GONE);
    }

    /**
     * This method will show the start and reset buttons by hiding the 
     * stop button and making the start and reset buttons visible.
     */
    private void hideStopButton(){
        mButtonStop.setVisibility(View.GONE);
        mButtonReset.setVisibility(View.VISIBLE);
        mButtonStart.setVisibility(View.VISIBLE);
    }

    /**
     * Converts the elapsed given time and updates the display
     */
    private void updateTimer(){
        mTimerText.setText(watch.toString());
        mTimerTextMs.setText(watch.toStringMs());
    }

    private class observer implements WatchModel.WatchObserver {
        @Override
        public void stateChange(WatchModel.stateEnum newState) {
            switch (newState) {
                case running:
                    showStopButton();
                    break;
                case paused:
                    hideStopButton();
                    break;
                case stopped:
                    hideStopButton();
                    break;
            }
            updateTimer();
        }
    }

    /**
     * Create a Runnable startTimer that makes timer runnable.
     */
    private Runnable startTimer = new Runnable() {
        public void run() {

            //TODO

        }
    };

}
    