package com.nongratis.timetracker.data.repository;

import androidx.lifecycle.LiveData;

import com.nongratis.timetracker.data.entities.Task;

import java.util.List;

public interface ITaskRepository {
    void insertTask(Task task) throws Exception;

    LiveData<List<Task>> getTasksBetween(long dayStart, long dayEnd);
}