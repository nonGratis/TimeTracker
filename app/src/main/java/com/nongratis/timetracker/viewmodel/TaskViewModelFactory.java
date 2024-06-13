package com.nongratis.timetracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.nongratis.timetracker.data.repository.TaskRepository;

import java.util.Objects;

/**
 * Factory class to create TaskViewModel instances.
 */
public class TaskViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final TaskRepository taskRepository;

    public TaskViewModelFactory(Application application, TaskRepository taskRepository) {
        this.application = application;
        this.taskRepository = taskRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return Objects.requireNonNull(modelClass.cast(new TaskViewModel(application, taskRepository)));
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}