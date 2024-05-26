package com.nongratis.timetracker.managers;

import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.viewmodel.TaskViewModel;

public class TaskManager {
    private final TaskViewModel taskViewModel;

    public TaskManager(TaskViewModel taskViewModel) {
        this.taskViewModel = taskViewModel;
    }

    public void saveTask(String workflowName, String projectName, String description, long startTime, long endTime) {
        Task task = new Task();
        task.setWorkflowName(workflowName);
        task.setProjectName(projectName);
        task.setDescription(description);
        task.setStartTime(startTime);
        task.setEndTime(endTime);
        taskViewModel.saveTask(workflowName, projectName, description, startTime, endTime);
    }
}