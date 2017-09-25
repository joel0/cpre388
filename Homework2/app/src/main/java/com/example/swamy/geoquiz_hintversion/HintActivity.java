package com.example.swamy.geoquiz_hintversion;

import android.app.Activity;
import android.content.Intent;
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
    private int childUsefulness = 0;
    private int usefulness = 0;

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
        if (isLastHint()) {
            NextHint.setVisibility(View.INVISIBLE);
        }
        hintText.setText(hintList[hIndex1][hIndex2]);
        if (picHintList[hIndex1][hIndex2] != null) {
            hintPic.setImageResource(picHintList[hIndex1][hIndex2]);
        }
    }

    public void nextHintClick(View v) {
        Intent nextHint = new Intent(this, HintActivity.class);
        nextHint.putExtra("QUEST_INDEX", hIndex1);
        nextHint.putExtra("HINT_INDEX", hIndex2 + 1);
        startActivityForResult(nextHint, HINT_ACTIVITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case HINT_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    childUsefulness = data.getIntExtra("USEFULNESS", 0);
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
        sendResult();
        super.onBackPressed();
    }

    void sendResult()
    {
        Intent intent2Main = new Intent();

        //useful or not useful
        intent2Main.putExtra("USEFULNESS",usefulness + childUsefulness);
        setResult(RESULT_OK, intent2Main);
    }

    private boolean isLastHint() {
        return hIndex2 >= hintList[hIndex1].length - 1;
    }

}
