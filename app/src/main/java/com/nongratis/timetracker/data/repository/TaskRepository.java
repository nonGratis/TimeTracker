package com.nongratis.timetracker.data.repository;

import com.nongratis.timetracker.data.dao.TaskDao;
import com.nongratis.timetracker.data.entities.Task;

public class TaskRepository {
    private final TaskDao taskDao;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void insertTask(Task task) throws Exception {
        try {
            taskDao.insert(task);
        } catch (Exception e) {
            throw new Exception("Error inserting task", e);
        }
    }
}