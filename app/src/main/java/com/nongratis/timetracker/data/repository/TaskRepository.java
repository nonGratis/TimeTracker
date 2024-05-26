package com.nongratis.timetracker.data.repository;

import androidx.lifecycle.LiveData;

import com.nongratis.timetracker.data.dao.TaskDao;
import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.data.executor.DatabaseExecutor;

import java.util.List;

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
        // TODO: Implement the method to fetch tasks within the specified time range
        return null;
    }
}