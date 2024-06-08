package com.nongratis.timetracker.viewmodel;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nongratis.timetracker.data.repository.TaskRepository;
import com.nongratis.timetracker.managers.TaskManager;
import com.nongratis.timetracker.managers.TimerManager;
import com.nongratis.timetracker.utils.NotificationHelper;

public class TimerViewModel extends AndroidViewModel {

    private final TimerManager timerManager;
    private final TaskManager taskManager;
    private final MutableLiveData<String> elapsedTime = new MutableLiveData<>();
    private final NotificationHelper notificationHelper;

    public TimerViewModel(@NonNull Application application) {
        super(application);
        timerManager = new TimerManager();
        taskManager = new TaskManager(new TaskViewModel(application, new TaskRepository(application)));
        notificationHelper = new NotificationHelper(application);
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
        startUpdatingElapsedTime();
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
        startUpdatingElapsedTime();
    }

    public void updateElapsedTime() {
        String time = timerManager.getElapsedTime();
        elapsedTime.postValue(time);
        Log.d("TimerViewModel", "Elapsed time updated: " + time);
        updateNotification();
    }

    private void startUpdatingElapsedTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (timerManager.isRunning()) {
                    updateElapsedTime();
                    handler.postDelayed(this, 1000);
                }
            }
        }, 1000);
    }

    public void saveTimer(String workflowName, String projectName, String description) {
        taskManager.saveTask(workflowName, projectName, description, timerManager.getStartTime(), System.currentTimeMillis());
        Log.d("TimerViewModel", "Timer saved");
    }

    public void updateNotification() {
        String elapsedTime = getElapsedTime().getValue();
        boolean isPaused = isPaused();
        notificationHelper.updateNotification(elapsedTime, isPaused);
    }
}