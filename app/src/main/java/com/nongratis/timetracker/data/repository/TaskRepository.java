package com.nongratis.timetracker.data.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.nongratis.timetracker.data.dao.TaskDao;
import com.nongratis.timetracker.data.database.AppDatabase;
import com.nongratis.timetracker.data.entities.Task;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository implements ITaskRepository {
    private final TaskDao taskDao;
    private final ExecutorService executorService;

    public TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        taskDao = db.taskDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void insertTask(Task task) {
        executorService.execute(() -> taskDao.insert(task));
    }

    @Override
    public LiveData<List<Task>> getAllTasks() {
        return taskDao.getAllTasks();
    }
}