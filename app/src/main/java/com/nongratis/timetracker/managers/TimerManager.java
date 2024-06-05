package com.nongratis.timetracker.managers;

import com.nongratis.timetracker.utils.TimerLogic;

public class TimerManager {
    private final TimerLogic timerLogic = new TimerLogic();
    private TimerUpdateListener timerUpdateListener;

    public interface TimerUpdateListener {
        void onTimerUpdate(String elapsedTime);
    }

    public void setTimerUpdateListener(TimerUpdateListener listener) {
        this.timerUpdateListener = listener;
    }

    public void startTimer() {
        timerLogic.startTimer();
        if (timerUpdateListener != null) {
            timerUpdateListener.onTimerUpdate(getElapsedTime());
        }
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

    public boolean isPaused() {
        return timerLogic.isPaused();
    }
}