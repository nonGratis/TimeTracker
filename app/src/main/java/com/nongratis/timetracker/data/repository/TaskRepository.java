package com.nongratis.timetracker.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.nongratis.timetracker.data.dao.TaskDao;
import com.nongratis.timetracker.data.database.AppDatabase;
import com.nongratis.timetracker.data.entities.Task;

import java.util.Calendar;
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

    public void insertTask(Task task) {
        executorService.execute(() -> taskDao.insert(task));
    }

    public void updateTask(Task task) {
        executorService.execute(() -> taskDao.update(task));
    }

    public void deleteTask(Task task) {
        executorService.execute(() -> taskDao.delete(task));
    }

    public LiveData<List<Task>> getAllTasks() {
        return taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getTasksByPeriod(String period) {
        long startTime = 0, endTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();

        switch (period) {
            case "day":
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                startTime = calendar.getTimeInMillis();
                break;
            case "week":
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                startTime = calendar.getTimeInMillis();
                break;
            case "month":
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                startTime = calendar.getTimeInMillis();
                break;
        }

        return taskDao.getTasksByPeriod(startTime, endTime);
    }
}