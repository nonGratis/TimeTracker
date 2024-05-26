package com.nongratis.timetracker.managers;

import com.nongratis.timetracker.utils.TimerLogic;

public class TimerManager {
    private final TimerLogic timerLogic = new TimerLogic();

    public void startTimer() {
        timerLogic.startTimer();
    }

    public void stopTimer() {
        timerLogic.stopTimer();
    }

    public void pauseTimer() {
        timerLogic.pauseTimer();
    }

    public String getElapsedTime() {
        return timerLogic.getElapsedTime();
    }

    public long getStartTime() {
        return timerLogic.getStartTime();
    }

    public boolean isRunning() {
        return timerLogic.isRunning();
    }
}