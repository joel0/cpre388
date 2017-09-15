package com.example.stopwatch;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by jmay on 2017-09-15.
 */

public class WatchModel {
    enum stateEnum {
        running,
        paused,
        stopped
    }

    private long startTime = 0;
    private long pauseDuration = 0;
    private stateEnum currentState = stateEnum.stopped;
    private ArrayList<WatchObserver> observers = new ArrayList<>();

    // === Getters ===

    public long getCurrentDuration() {
        switch (currentState) {
            case running:
                return System.currentTimeMillis() - startTime;
            case paused:
                return pauseDuration;
            default: // state "stopped"
                return 0;
        }
    }

    public int getMs() {
        return (int) (getCurrentDuration() % 1000);
    }

    public int getSec() {
        return (int) ((getCurrentDuration() / 1000) % 60);
    }

    public int getMin() {
        return (int) ((getCurrentDuration() / 1000 / 60) % 60);
    }

    public long getHr() {
        return getCurrentDuration() / 1000 / 60 / 60;
    }

    public String toString() {
        return String.format(Locale.getDefault(), "%2d:%2d:%2d", getHr(), getMin(), getSec());
    }

    public String toStringMs() {
        return String.format(Locale.getDefault(), ".%4d", getMs());
    }

    public stateEnum getCurrentState() {
        return currentState;
    }

    // === Actions ===

    public void start() {
        switch (currentState) {
            case stopped:
                startTime = System.currentTimeMillis();
                // Fall into "paused" case.
            case paused:
                startTime = System.currentTimeMillis() - pauseDuration;
                currentState = stateEnum.running;
                notifyObservers();
                break;
            default: // state "running"
                // Do nothing
        }
    }

    public void stop() {
        switch (currentState) {
            case running:
                pauseDuration = getCurrentDuration();
                currentState = stateEnum.paused;
                notifyObservers();
                break;
            case paused:
                currentState = stateEnum.stopped;
                notifyObservers();
                break;
            default: // state "stopped"
                // Do nothing
        }
    }

    // === Observer things ===

    public void addObserver(WatchObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (WatchObserver observer : observers) {
            observer.stateChange(currentState);
        }
    }

    interface WatchObserver {
        void stateChange(stateEnum newState);
    }
}
