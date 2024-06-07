package com.nongratis.timetracker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nongratis.timetracker.data.entities.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TimeAnalysisViewModel extends ViewModel {
    private final TaskViewModel taskViewModel;
    private final MutableLiveData<Map<String, Float>> workflowEntriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Map<String, Float>> projectEntriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Long> totalTimeLiveData = new MutableLiveData<>();

    public TimeAnalysisViewModel(TaskViewModel taskViewModel) {
        this.taskViewModel = taskViewModel;
    }

    public LiveData<Map<String, Float>> getWorkflowEntriesLiveData() {
        return workflowEntriesLiveData;
    }

    public LiveData<Map<String, Float>> getProjectEntriesLiveData() {
        return projectEntriesLiveData;
    }

    public LiveData<Long> getTotalTimeLiveData() {
        return totalTimeLiveData;
    }

    public void loadData(String period) {
        taskViewModel.getTasksByPeriod(period).observeForever(tasks -> {
            Map<String, Float> workflowEntriesMap = new HashMap<>();
            Map<String, Float> projectEntriesMap = new HashMap<>();
            long totalTime = 0;
            for (Task task : tasks) {
                String workflowLabel = task.getWorkflowName();
                String projectLabel = task.getProjectName();
                if (workflowLabel == null || workflowLabel.isEmpty()) {
                    workflowLabel = "Other";
                }
                if (projectLabel == null || projectLabel.isEmpty()) {
                    projectLabel = "Other";
                }
                float workflowAccumulatedDuration = workflowEntriesMap.getOrDefault(workflowLabel, 0f);
                float projectAccumulatedDuration = projectEntriesMap.getOrDefault(projectLabel, 0f);
                workflowEntriesMap.put(workflowLabel, workflowAccumulatedDuration + task.getDuration());
                projectEntriesMap.put(projectLabel, projectAccumulatedDuration + task.getDuration());
                totalTime += task.getDuration();
            }
            workflowEntriesLiveData.setValue(workflowEntriesMap);
            projectEntriesLiveData.setValue(projectEntriesMap);
            totalTimeLiveData.setValue(totalTime);
        });
    }

    public String formatDuration(long duration) {
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}