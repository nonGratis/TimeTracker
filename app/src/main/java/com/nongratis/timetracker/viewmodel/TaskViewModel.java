package com.nongratis.timetracker.viewmodel;

import androidx.lifecycle.ViewModel;
import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.data.repository.ITaskRepository;

public class TaskViewModel extends ViewModel {
    private final ITaskRepository taskRepository;

    public TaskViewModel(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void saveTask(String workflowName, String projectName, String description, long startTime, long endTime) {
        Task task = new Task();
        task.setWorkflowName(workflowName);
        task.setProjectName(projectName);
        task.setDescription(description);
        task.setStartTime(startTime);
        task.setEndTime(endTime);
        new Thread(() -> {
            try {
                taskRepository.insertTask(task);
            } catch (Exception e) {
                // Handle or log the exception
                e.printStackTrace();
            }
        }).start();
    }
}