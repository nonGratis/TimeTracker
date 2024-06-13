package com.nongratis.timetracker.utils;

import android.util.Log;

public class TimerLogic {
    private static final String TAG = TimerLogic.class.getSimpleName();
    private long startTime = 0L;
    private long pauseTime = 0L;
    private boolean isRunning = false;
    private boolean isPaused = false;

    public void startTimer() {
        isRunning = true;
        if (isPaused) {
            startTime = System.currentTimeMillis() - pauseTime;
            isPaused = false;
        } else {
            startTime = System.currentTimeMillis();
        }
        Log.i(TAG, String.format("startTimer: startTime=%d, pauseTime=%d, isRunning=%b, isPaused=%b", startTime, pauseTime, isRunning, isPaused));
    }

    public void stopTimer() {
        Log.i(TAG, "stopTimer");
        isRunning = false;
        isPaused = false;
        pauseTime = 0L;
        startTime = 0L;
    }

    public void pauseTimer() {
        Log.i(TAG, "pauseTimer");
        isRunning = false;
        isPaused = true;
        pauseTime = System.currentTimeMillis() - startTime;
    }

    public String getElapsedTime() {
        long elapsedTime = getElapsedTimeMillis();
        int seconds = (int) (elapsedTime / 1000);
        int minutes = seconds / 60;
        int hours = minutes / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    protected long getElapsedTimeMillis() {
        if (isRunning) {
            return System.currentTimeMillis() - startTime;
        } else {
            return pauseTime;
        }
    }

    public long getStartTime() {
        return startTime;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isPaused() {
        return isPaused;
    }
}