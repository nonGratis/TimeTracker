package com.nongratis.timetracker.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nongratis.timetracker.data.repository.ITaskRepository;

public class DailyTimelineViewModelFactory implements ViewModelProvider.Factory {
    private final ITaskRepository taskRepository;

    public DailyTimelineViewModelFactory(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DailyTimelineViewModel.class)) {
            return (T) new DailyTimelineViewModel(taskRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}