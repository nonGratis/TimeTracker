package com.nongratis.timetracker.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.data.repository.TaskRepository;

import java.util.List;

public class TaskViewModel extends ViewModel {
    private final TaskRepository taskRepository;

    public TaskViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public LiveData<List<Task>> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public void insertTask(Task task) {
        taskRepository.insertTask(task);
    }

    public LiveData<List<Task>> getTasksByPeriod(String period) {
        return taskRepository.getTasksByPeriod(period);
    }
}