package com.nongratis.timetracker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.data.repository.ITaskRepository;

import java.util.List;

public class DailyTimelineViewModel extends ViewModel {
    private final ITaskRepository taskRepository;

    public DailyTimelineViewModel(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public LiveData<List<Task>> getTasksForDay(long dayStart, long dayEnd) {
        return taskRepository.getTasksBetween(dayStart, dayEnd);
    }
}