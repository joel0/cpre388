package com.example.swamy.geoquiz;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//Modified GeoQuiz
//author - swamy d ponpandi
//date - aug 25 2017
//class - cpre 388
//demo activity, buttons, textview, attributes, listeners, toast

public class QuizActivity extends AppCompatActivity {

    //android naming convention for member instance variables
    //demo import android.widget.Button;, try Alt+Enter

    private TextView mTextDisplay;
    private Button mNextButton;
    private LinearLayout mAnswerButtonLayout;
    private Snackbar mTryAgainSnackbar;

    private int qIndex = 0;
    private QuizQuestion[] questions = QuizQuestion.getSampleQuizSet();
    private int incorrectCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);

        mTryAgainSnackbar = Snackbar.make(this.findViewById(R.id.mainLayout),
                "Incorrect", Snackbar.LENGTH_INDEFINITE);
        mAnswerButtonLayout = (LinearLayout) findViewById(R.id.answersBox);
        mNextButton = (Button) findViewById(R.id.button3);

        mTextDisplay = (TextView) findViewById(R.id.mytext);
        mTextDisplay.setTextColor(Color.BLUE);
        showQuestion();

        mNextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nextQuestion();
            }

        });
    }

    private void nextQuestion() {
        qIndex = (qIndex + 1) % questions.length;
        showQuestion();
    }

    private void showQuestion() {
        incorrectCount = 0;
        mTryAgainSnackbar.dismiss();
        mAnswerButtonLayout.removeAllViews();

        mTextDisplay.setText(questions[qIndex].getQuestion());
        for (int i = 0; i < questions[qIndex].getAnswers().length; i++) {
            String answer = questions[qIndex].getAnswers()[i];
            Button newButton = new Button(this);
            AnswerOnClick newHandler = new AnswerOnClick(i);
            newButton.setText(answer);
            newButton.setOnClickListener(newHandler);
            mAnswerButtonLayout.addView(newButton);
        }
        mAnswerButtonLayout.setVisibility(View.VISIBLE);
    }

    private void showTryAgain() {
        mAnswerButtonLayout.setVisibility(View.INVISIBLE);
        mTryAgainSnackbar.setText("Incorrect");
        mTryAgainSnackbar.setAction("Try again", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAnswerButtonLayout.setVisibility(View.VISIBLE);
                mTryAgainSnackbar.dismiss();
            }
        });
        mTryAgainSnackbar.show();
    }

    private void showFailure() {
        mAnswerButtonLayout.setVisibility(View.INVISIBLE);
        mTryAgainSnackbar.setAction("Next", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
            }
        });
        mTryAgainSnackbar.setText("Incorrect. It is " + questions[qIndex].getCorrectAnswer());
        mTryAgainSnackbar.show();
    }

    private void userChooseAnswer(int index) {
        if (index == questions[qIndex].getCorrectAnswerIndex()) {
            correctAnswerChosen();
        } else {
            wrongAnswerChosen();
        }
    }

    private void correctAnswerChosen() {
        Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
        mAnswerButtonLayout.setVisibility(View.INVISIBLE);
    }

    private void wrongAnswerChosen() {
        incorrectCount++;
        if (incorrectCount < 2) {
            showTryAgain();
        } else {
            showFailure();
        }
    }

    private class AnswerOnClick implements View.OnClickListener {
        private int answerIndex;

        AnswerOnClick(int answerIndex) {
            this.answerIndex = answerIndex;
        }

        @Override
        public void onClick(View view) {
            userChooseAnswer(answerIndex);
        }
    }
}
