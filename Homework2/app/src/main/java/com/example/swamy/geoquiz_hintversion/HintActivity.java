package com.example.swamy.geoquiz_hintversion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HintActivity extends AppCompatActivity {

    private int hIndex = 0;
    private int MaxQ = 3;
    private TextView hintText;
    //whether hint is useful or not ?
    private int usefulness = 0;

    private Button useful;
    private Button notuseful;

    private Button NextHint;

    private String hintList[] = {"Location of White House",
            "Northern part of India", "Greek goddess", "No Hint Found"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        useful = (Button) findViewById(R.id.button5);
        useful.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                usefulness = 1;
                sendResult();
            }

        });

        notuseful = (Button) findViewById(R.id.button6);
        notuseful.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                usefulness = 0;
                sendResult();
            }

        });

        hintText = (TextView) findViewById(R.id.mytext) ;
        hIndex = getIntent().getIntExtra("QUEST_INDEX", MaxQ);
        hintText.setText(hintList[hIndex]);
    }


    void sendResult()
    {
        Intent intent2Main = new Intent();

        //useful or not useful
        intent2Main.putExtra("USEFULNESS",usefulness);
        setResult(RESULT_OK, intent2Main);
    }

}
