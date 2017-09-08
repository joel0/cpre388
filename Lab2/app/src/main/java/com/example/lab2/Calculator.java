package com.example.lab2;

import java.util.ArrayList;

/**
 * Created by jmay on 2017-09-08.
 */

class Calculator {
    private StringBuilder expression1 = new StringBuilder("");
    private StringBuilder expression2 = new StringBuilder("");
    private boolean clobberRHS = true;
    private String operator = null;
    private String previousOperator = null;
    private double previousOperand;
    private int maxLen;
    ArrayList<CalculatorObserver> observers = new ArrayList<>();

    Calculator(int maxLen) {
        this.maxLen = maxLen;
    }

    void clear() {
        expression1 = new StringBuilder("");
        expression2 = new StringBuilder("");
        clobberRHS = true;
        operator = null;
        previousOperator = null;
        notifyObservers();
    }

    void doOperator(String operator) {
        if (this.operator == null) {
            // There is no pending operator, just move the RHS to LHS and set the operator.
            expression1 = expression2;
            this.operator = operator;
            expression2 = new StringBuilder("");
        } else {
            // There is a pending operator already.
            if (expression2.length() == 0) {
                // RHS is empty, just change the operator.
                this.operator = operator;
            } else {
                // RHS has contents, calculate the result into RHS, then proceed.
                doEquals();
                doOperator(operator);
            }
        }
        notifyObservers();
    }

    void doNumber(String number) {
        if (clobberRHS) {
            expression2 = new StringBuilder("");
            clobberRHS = false;
        }
        expression2.append(number);
        notifyObservers();
    }

    void doDecimal() {
        if (clobberRHS) {
            doNumber("0.");
        } else {
            if (expression2.length() == 0) {
                doNumber("0.");
            }
            if (!expression2.toString().contains(".")) {
                doNumber(".");
            }
        }
    }

    double doEquals() {
        if (operator == null && previousOperator != null) {
            expression1 = expression2;
            expression2 = new StringBuilder(Double.toString(previousOperand));
            operator = previousOperator;
        }
        // Store operation for future reapplication
        previousOperator = this.operator;
        previousOperand = getRHS();
        // Do the operation
        expression2 = new StringBuilder(Double.toString(getResult()));
        this.operator = null;
        clobberRHS = true;
        notifyObservers();
        return getResult();
    }

    double getResult() {
        if (operator == null) {
            return getRHS();
        }
        if (operator.equals("+")) {
            return getLHS() + getRHS();
        }
        if (operator.equals("-")) {
            return getLHS() - getRHS();
        }
        if (operator.equals("*")) {
            return getLHS() * getRHS();
        }
        if (operator.equals("/")) {
            return getLHS() / getRHS();
        }
        throw new RuntimeException("Invalid operator");
    }

    public String toString() {
        String res;
        if (operator == null) {
            res = Double.toString(getRHS());
        } else {
            res = Double.toString(getLHS()) + operator + Double.toString(getRHS());
        }
        if (res.length() > maxLen) {
            return "Err";
        }
        return res;
    }

    private void notifyObservers() {
        String display = toString();
        for (CalculatorObserver observer : observers) {
            observer.displayUpdate(display);
        }
    }

    private double getLHS() {
        if (expression1.length() == 0) {
            return 0;
        }
        return Double.valueOf(expression1.toString());
    }

    private double getRHS() {
        if (expression2.length() == 0) {
            return 0;
        }
        return Double.valueOf(expression2.toString());
    }

    interface CalculatorObserver {
        abstract void displayUpdate(String display);
    }
}
