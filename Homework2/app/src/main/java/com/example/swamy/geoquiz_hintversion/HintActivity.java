package com.example.swamy.geoquiz_hintversion;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class HintActivity extends AppCompatActivity {

    private int hIndex1 = 0;
    private int hIndex2 = 0;
    private int MaxQ = 3;
    private TextView hintText;
    private ImageView hintPic;
    //whether hint is useful or not ?
    private int childHints = 0;
    private int childUsefulness = 0;
    private int usefulness = 0;
    private boolean hintUsed = false;
    private long remainingTime = 5000;
    private Handler handler = new Handler();

    private Button useful;
    private Button notuseful;

    private Button NextHint;

    private final static int HINT_ACTIVITY = 1;

    private String hintList[][] = {
            {"Location of White House", "Picture", "President lives here"},
            {"Northern part of India", "Picture", "New Delhi is one of Delhi city's 11 districts."},
            {"Greek goddess", "Olympia is also a city in Washington state", "Greece is ancient"},
            {"No Hint Found"}};
    private Integer picHintList[][] = {
            {null, R.drawable.hint_wh, null},
            {null, R.drawable.hint_delhi, null},
            {null, null, null},
            {null}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        //restore saved state if we got killed earlier
        if(savedInstanceState != null)
        {
            hintUsed = savedInstanceState.getBoolean("hintUsed", false);
            remainingTime = savedInstanceState.getLong("remainingTime", remainingTime);
        }

        NextHint = (Button) findViewById(R.id.next_hint_button);

        useful = (Button) findViewById(R.id.button5);
        useful.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                usefulness = 1;
                //sendResult();
            }

        });

        notuseful = (Button) findViewById(R.id.button6);
        notuseful.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                usefulness = 0;
                //sendResult();
            }

        });

        hintPic = (ImageView) findViewById(R.id.picHint);
        hintText = (TextView) findViewById(R.id.mytext) ;
        hIndex1 = getIntent().getIntExtra("QUEST_INDEX", MaxQ);
        hIndex2 = getIntent().getIntExtra("HINT_INDEX", 0);

        updateUI();
        if (!hintUsed) {
            handler.postDelayed(forceNextHint, remainingTime);
        }
    }

    public void nextHintClick(View v) {
        nextHint();
    }

    private void nextHint() {
        handler.removeCallbacksAndMessages(null);
        if (isLastHint()) {
            return;
        }
        Intent nextHint = new Intent(this, HintActivity.class);
        nextHint.putExtra("QUEST_INDEX", hIndex1);
        nextHint.putExtra("HINT_INDEX", hIndex2 + 1);
        startActivityForResult(nextHint, HINT_ACTIVITY);
    }

    private void updateUI() {
        if (isLastHint()) {
            NextHint.setVisibility(View.INVISIBLE);
        }
        if (hintUsed) {
            hintText.setText(R.string.hint_used);
            hintPic.setVisibility(View.INVISIBLE);
        } else {
            hintText.setText(hintList[hIndex1][hIndex2]);
            if (picHintList[hIndex1][hIndex2] != null) {
                hintPic.setImageResource(picHintList[hIndex1][hIndex2]);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case HINT_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    childUsefulness = data.getIntExtra("USEFULNESS", 0);
                    childHints = data.getIntExtra("HINTS", 0);
                } else {
                    childUsefulness = 0;
                }
                break;
            default:
                // do nothing
        }
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacksAndMessages(null);
        sendResult();
        super.onBackPressed();
    }

    void sendResult()
    {
        Intent intent2Main = new Intent();

        //useful or not useful
        intent2Main.putExtra("USEFULNESS",usefulness + childUsefulness);
        intent2Main.putExtra("HINTS",childHints + 1);
        setResult(RESULT_OK, intent2Main);
    }

    private boolean isLastHint() {
        return hIndex2 >= hintList[hIndex1].length - 1;
    }

    private Runnable forceNextHint = new Runnable() {
        @Override
        public void run() {
            hintUsed = true;
            updateUI();
            nextHint();
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        //android framework will do its share of the job
        super.onSaveInstanceState(savedInstanceState);
        handler.removeCallbacksAndMessages(null);
        savedInstanceState.putBoolean("hintUsed", hintUsed);
        if (handler.hasMessages(0)) {
            savedInstanceState.putLong("remainingTime", handler.obtainMessage(0).getWhen());
        }
    }
}
