package com.nongratis.timetracker.viewmodel;

import androidx.lifecycle.ViewModel;
import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.data.repository.TaskRepository;

public class TaskViewModel extends ViewModel {
    private final TaskRepository taskRepository;

    public TaskViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void saveTask(String workflowName, String projectName, String description, long startTime, long endTime) {
        Task task = new Task();
        task.setWorkflowName(workflowName);
        task.setProjectName(projectName);
        task.setDescription(description);
        task.setStartTime(startTime);
        task.setEndTime(endTime);
        new Thread(() -> taskRepository.insertTask(task)).start();
    }
}