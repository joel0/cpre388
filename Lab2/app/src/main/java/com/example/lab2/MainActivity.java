package com.example.lab2;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author jamiekujawa
 *
 */
public class MainActivity extends Activity {

    /**
     * A string builder to represent the first number entered in the series of entries
     */
    private StringBuilder expression1;

    /**
     * A string builder to represent the second number entered in the series of entries
     */
    private StringBuilder expression2;

    /**
     * A string to represent the last operator performed
     */
    private String oldOperator;

    /* (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // declare all buttons used within the layout
        Button zero = (Button) findViewById(R.id.button0);
        Button one = (Button) findViewById(R.id.button1);
        Button two = (Button) findViewById(R.id.button2);
        Button three = (Button) findViewById(R.id.button3);
        Button four = (Button) findViewById(R.id.button4);
        Button five = (Button) findViewById(R.id.button5);
        Button six = (Button) findViewById(R.id.button6);
        Button seven = (Button) findViewById(R.id.button7);
        Button eight = (Button) findViewById(R.id.button8);
        Button nine = (Button) findViewById(R.id.button9);
        Button times = (Button) findViewById(R.id.buttontimes);
        Button clear = (Button) findViewById(R.id.buttonClear);
        Button equal = (Button) findViewById(R.id.buttonEqual);
        Button decimal = (Button) findViewById(R.id.buttonDecimal);
        Button divide = (Button) findViewById(R.id.buttondivide);
        Button add = (Button) findViewById(R.id.buttonplus);
        Button subtract = (Button) findViewById(R.id.buttonminus);

        // declare main text view
        final TextView main = (TextView) findViewById(R.id.CalculatorText);

        // Main Strings to represent the expressions
        expression1 = new StringBuilder();
        expression2 = new StringBuilder();
        main.setText(expression1.append("0.0"));

		/*
		 * Set up all key listener events
		 */
        zero.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        one.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        two.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        three.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        four.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        five.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        six.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        seven.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        eight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        nine.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        times.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        clear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        equal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        decimal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        divide.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });

        subtract.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //TODO
            }

        });
    }

    /**
     * This method will evaluate an operation using expression1 and expression 2
     *
     * @param buttonPressed or operation to be performed
     * @return the result of the operation
     */
    public String evaluate(String buttonPressed) {
        //TODO
        return null;
    }

}