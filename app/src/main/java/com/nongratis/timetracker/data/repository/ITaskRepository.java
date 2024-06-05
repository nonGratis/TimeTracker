package com.nongratis.timetracker.data.repository;

import androidx.lifecycle.LiveData;

import com.nongratis.timetracker.data.entities.Task;

import java.util.List;
import java.util.Map;

public interface ITaskRepository {
    void insertTask(Task task) throws Exception;

    LiveData<List<Task>> getTasksBetween(long dayStart, long dayEnd);
    LiveData<Long> getTotalTime(TimePeriod period);
    LiveData<Map<String, Long>> getWorkflowData(TimePeriod period);
    LiveData<Map<String, Long>> getProjectData(TimePeriod period);
}