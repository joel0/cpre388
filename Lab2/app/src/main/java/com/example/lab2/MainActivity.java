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
    private Calculator calculator = new Calculator(10);

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
        main.setText(calculator.toString());
        calculator.observers.add(new Calculator.CalculatorObserver() {
            @Override
            public void displayUpdate(String display) {
                main.setText(calculator.toString());
            }
        });

		/*
		 * Set up all key listener events
		 */
        zero.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doNumber("0");
            }

        });

        one.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doNumber("1");
            }

        });

        two.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doNumber("2");
            }

        });

        three.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doNumber("3");
            }

        });

        four.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doNumber("4");
            }

        });

        five.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doNumber("5");
            }

        });

        six.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doNumber("6");
            }

        });

        seven.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doNumber("7");
            }

        });

        eight.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doNumber("8");
            }

        });

        nine.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doNumber("9");
            }

        });

        times.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doOperator("*");
            }

        });

        clear.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.clear();
            }

        });

        equal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doEquals();
            }

        });

        decimal.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doDecimal();
            }

        });

        divide.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doOperator("/");
            }

        });

        add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doOperator("+");
            }

        });

        subtract.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                calculator.doOperator("-");
            }

        });
    }

}