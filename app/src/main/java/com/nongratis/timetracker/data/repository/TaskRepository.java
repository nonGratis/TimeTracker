package com.nongratis.timetracker.data.repository;

import com.nongratis.timetracker.data.dao.TaskDao;
import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.data.executor.DatabaseExecutor;

public class TaskRepository {
    private final TaskDao taskDao;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void insertTask(Task task) throws Exception {
        try {
            DatabaseExecutor.getExecutor().execute(() -> {
                taskDao.insert(task);
            });
        } catch (Exception e) {
            throw new Exception("Error inserting task", e);
        }
    }
}