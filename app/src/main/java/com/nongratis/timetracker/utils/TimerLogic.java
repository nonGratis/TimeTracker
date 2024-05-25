package com.nongratis.timetracker.utils;

public class TimerLogic {

    private long startTime = 0L;
    private long pauseTime = 0L;
    private boolean isRunning = false;

    public void startTimer() {
        isRunning = true;
        if (pauseTime == 0L) {
            startTime = System.currentTimeMillis();
        } else {
            startTime = System.currentTimeMillis() - pauseTime;
        }
    }

    public void stopTimer() {
        isRunning = false;
        pauseTime = 0L;
        startTime = 0L;

    }

    public void pauseTimer() {
        isRunning = false;
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
}