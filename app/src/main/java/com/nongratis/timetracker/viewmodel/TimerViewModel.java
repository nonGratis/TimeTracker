package com.nongratis.timetracker.viewmodel;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.nongratis.timetracker.managers.TimerManager;
import com.nongratis.timetracker.utils.ElapsedTimeUpdater;

public class TimerViewModel extends AndroidViewModel {

    private final TimerManager timerManager;
    private final ElapsedTimeUpdater elapsedTimeUpdater;

    private final MutableLiveData<String> elapsedTime = new MutableLiveData<>();
    private BroadcastReceiver receiver;

    public TimerViewModel(@NonNull Application application) {
        super(application);
        timerManager = new TimerManager();
        elapsedTimeUpdater = new ElapsedTimeUpdater(timerManager, elapsedTime);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if ("com.nongratis.timetracker.ACTION_STOP_TIMER".equals(intent.getAction())) {
                    stopTimer();
                } else if ("com.nongratis.timetracker.ACTION_PAUSE_TIMER".equals(intent.getAction())) {
                    pauseTimer();
                }
                else if ("com.nongratis.timetracker.ACTION_RESUME_TIMER".equals(intent.getAction())) {
                    resumeTimer();
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction("com.nongratis.timetracker.ACTION_STOP_TIMER");
        filter.addAction("com.nongratis.timetracker.ACTION_PAUSE_TIMER");
        filter.addAction("com.nongratis.timetracker.ACTION_RESUME_TIMER");

        LocalBroadcastManager.getInstance(application).registerReceiver(receiver, filter);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        LocalBroadcastManager.getInstance(getApplication()).unregisterReceiver(receiver);
    }

    public LiveData<String> getElapsedTime() {
        return elapsedTime;
    }

    public boolean isRunning() {
        return timerManager.isRunning();
    }

    public boolean isPaused() {
        return timerManager.isPaused();
    }

    public void startTimer() {
        timerManager.startTimer();
        Log.d("TimerViewModel", "Timer started");
        elapsedTimeUpdater.startUpdatingElapsedTime();
    }

    public void stopTimer() {
        timerManager.stopTimer();
        Log.d("TimerViewModel", "Timer stopped");
        updateElapsedTime();
    }

    public void pauseTimer() {
        timerManager.pauseTimer();
        Log.d("TimerViewModel", "Timer paused");
        updateElapsedTime();
    }

    public void resumeTimer() {
        timerManager.startTimer();
        Log.d("TimerViewModel", "Timer resumed");
        elapsedTimeUpdater.startUpdatingElapsedTime();
    }

    public void updateElapsedTime() {
        String time = timerManager.getElapsedTime();
        elapsedTime.postValue(time);
        Log.d("TimerViewModel", "Elapsed time updated: " + time);
    }

    public void saveTimer(String workflowName, String projectName, String description) {
        Log.d("TimerViewModel", "Timer saved");
    }
}