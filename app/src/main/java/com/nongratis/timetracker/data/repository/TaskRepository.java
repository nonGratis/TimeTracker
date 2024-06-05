package com.nongratis.timetracker.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nongratis.timetracker.data.dao.TaskDao;
import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.data.executor.DatabaseExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskRepository implements ITaskRepository {
    private final TaskDao taskDao;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public void insertTask(Task task) throws Exception {
        try {
            DatabaseExecutor.getExecutor().execute(() -> {
                taskDao.insert(task);
            });
        } catch (Exception e) {
            throw new Exception("Error inserting task", e);
        }
    }

    @Override
    public LiveData<List<Task>> getTasksBetween(long dayStart, long dayEnd) {
        // TODO: Implement the method to fetch tasks between the specified start and end times
        return new MutableLiveData<>();
    }

    @Override
    public LiveData<Long> getTotalTime(TimePeriod period) {
        // TODO: Implement the method to fetch total time spent on all tasks within the specified time period
        return new MutableLiveData<>();
    }

    @Override
    public LiveData<Map<String, Long>> getWorkflowData(TimePeriod period) {
        // TODO: Implement the method to fetch workflow data within the specified time period
        return new MutableLiveData<>(new HashMap<>());
    }

    @Override
    public LiveData<Map<String, Long>> getProjectData(TimePeriod period) {
        // TODO: Implement the method to fetch project data within the specified time period
        return new MutableLiveData<>(new HashMap<>());
    }
}