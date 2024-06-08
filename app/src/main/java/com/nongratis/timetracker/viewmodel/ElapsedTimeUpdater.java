package com.nongratis.timetracker.viewmodel;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;

import com.nongratis.timetracker.managers.TimerManager;

public class ElapsedTimeUpdater {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final TimerManager timerManager;
    private final MutableLiveData<String> elapsedTime;

    public ElapsedTimeUpdater(TimerManager timerManager, MutableLiveData<String> elapsedTime) {
        this.timerManager = timerManager;
        this.elapsedTime = elapsedTime;
    }

    public void startUpdatingElapsedTime() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (timerManager.isRunning()) {
                    String time = timerManager.getElapsedTime();
                    elapsedTime.postValue(time);
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }
}