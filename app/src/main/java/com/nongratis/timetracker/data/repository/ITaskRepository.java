package com.nongratis.timetracker.data.repository;

import androidx.lifecycle.LiveData;

import com.nongratis.timetracker.data.entities.Task;

import java.util.List;

public interface ITaskRepository {
    void insertTask(Task task);

    void updateTask(Task task);

    void deleteTask(Task task);

    LiveData<List<Task>> getAllTasks();

    LiveData<List<Task>> getTasksByPeriod(String period);
}