package com.nongratis.timetracker.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.data.repository.ITaskRepository;

import java.util.List;

/**
 * ViewModel class for managing Task data.
 */
public class TaskViewModel extends AndroidViewModel {

    private final ITaskRepository taskRepository;
    private final LiveData<List<Task>> allTasks;

    public TaskViewModel(@NonNull Application application, ITaskRepository taskRepository) {
        super(application);
        this.taskRepository = taskRepository;
        this.allTasks = taskRepository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insert(Task task) {
        taskRepository.insertTask(task);
    }

    public void update(Task task) {
        taskRepository.updateTask(task);
    }

    public void delete(Task task) {
        taskRepository.deleteTask(task);
    }

    public LiveData<List<Task>> getTasksByPeriod(String period) {
        return taskRepository.getTasksByPeriod(period);
    }
}