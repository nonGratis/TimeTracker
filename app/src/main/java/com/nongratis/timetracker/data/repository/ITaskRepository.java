package com.nongratis.timetracker.data.repository;

import com.nongratis.timetracker.data.entities.Task;

public interface ITaskRepository {
    void insertTask(Task task) throws Exception;
}