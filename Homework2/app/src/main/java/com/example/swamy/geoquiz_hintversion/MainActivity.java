package com.example.swamy.geoquiz_hintversion;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//GeoQuiz Hint version
//update - add a show hint button
//update - show toast indicating usefulness of hint
//author - swamy d ponpandi
//date - sept 6 2017
//class - cpre 388
//demo startactivity, intents

public class MainActivity extends AppCompatActivity {

    //android naming convention for member instance variables
    //demo import android.widget.Button;, try Alt+Enter

    private Button mYesButton;
    private Button mNoButton;
    private TextView mTextDisplay;
    private Button mNextButton;
    private Button mHintButton;

    private int qIndex = 0;
    private int MaxQ = 3;
    private int usefulness = 0;
    private int usedHints = 0;
    private boolean questining = true;

    private String questList[] = {"Capital of USA is Washington DC",
            "Capital of India is New Delhi", "Capital of Greece is olympia"};
    private String ansList[] = {"Yes",
            "Yes", "No"};

    private static int HINT_ACTIVITY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //restore saved state if we got killed earlier
        if(savedInstanceState != null)
        {
            //0 is the default value incase index key was not defined before
            qIndex = savedInstanceState.getInt("index",0);
            usefulness = savedInstanceState.getInt("usefulness", 0);
            usedHints = savedInstanceState.getInt("usedHints", 0);
            questining = savedInstanceState.getBoolean("questioning", true);
        }

        mYesButton = (Button) findViewById(R.id.button);
        mNoButton = (Button) findViewById(R.id.button2);
        mHintButton = (Button) findViewById(R.id.button4);

        mTextDisplay = (TextView) findViewById(R.id.mytext);
        mTextDisplay.setTextColor(Color.BLUE);

        if (questining) {
            mYesButton.setVisibility(View.VISIBLE);
            mNoButton.setVisibility(View.VISIBLE);
            mHintButton.setVisibility(View.VISIBLE);
            mTextDisplay.setText(questList[qIndex]);
        } else {
            mYesButton.setVisibility(View.INVISIBLE);
            mNoButton.setVisibility(View.INVISIBLE);
            mHintButton.setVisibility(View.INVISIBLE);
            mTextDisplay.setText(usedHints + " hint(s) were used");
        }

        mYesButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ("Yes".equals(ansList[qIndex])) {
                    Toast.makeText(MainActivity.this, "You are correct !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect !", Toast.LENGTH_SHORT).show();
                }

                mYesButton.setVisibility(View.INVISIBLE);
                mNoButton.setVisibility(View.INVISIBLE);
                mHintButton.setVisibility(View.INVISIBLE);
                mTextDisplay.setText(usedHints + " hint(s) were used");
                usedHints = 0;
                questining = false;
            }

        });

        mNoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if ("No".equals(ansList[qIndex])) {
                    Toast.makeText(MainActivity.this, "You are correct !", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect !", Toast.LENGTH_SHORT).show();
                }
                mYesButton.setVisibility(View.INVISIBLE);
                mNoButton.setVisibility(View.INVISIBLE);
                mHintButton.setVisibility(View.INVISIBLE);
                mTextDisplay.setText(usedHints + " hint(s) were used");
                usedHints = 0;
                questining = false;
            }

        });

        //next button listner
        mNextButton = (Button) findViewById(R.id.button3);
        mNextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                qIndex = (qIndex + 1) % MaxQ;
                questining = true;
                mTextDisplay.setText(questList[qIndex]);
                mYesButton.setVisibility(View.VISIBLE);
                mNoButton.setVisibility(View.VISIBLE);
                mHintButton.setVisibility(View.VISIBLE);
            }

        });


        //hint button listner
        mHintButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //logic to start HintActivity
                Intent startHintActivity = new Intent(MainActivity.this, HintActivity.class);
                startHintActivity.putExtra("QUEST_INDEX", qIndex);
               //startActivity(startHintActivity);

               startActivityForResult(startHintActivity, HINT_ACTIVITY);
            }

        });
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState){
        //android framework will do its share of the job
        super.onSaveInstanceState(savedInstanceState);

        //save the current question index
        savedInstanceState.putInt("index",qIndex);
        savedInstanceState.putInt("usedfulness", usefulness);
        savedInstanceState.putInt("usedHints", usedHints);
        savedInstanceState.putBoolean("questioning", questining);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
         {
             usedHints = data.getIntExtra("HINTS", 0);
             usefulness = data.getIntExtra("USEFULNESS",0);
             Toast.makeText(MainActivity.this, usefulness + " hint(s) were useful.",Toast.LENGTH_LONG).show();

            /*if(usefulness == 1)
            {
                Toast.makeText(MainActivity.this, "Glad the hint was useful",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(MainActivity.this, "Sorry the hint was not useful",Toast.LENGTH_LONG).show();
            }*/
        }
        else {
            //handle this situation
        }
    }
}
