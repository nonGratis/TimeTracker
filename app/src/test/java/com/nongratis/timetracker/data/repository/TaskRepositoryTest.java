package com.nongratis.timetracker.data.repository;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.nongratis.timetracker.data.dao.TaskDao;
import com.nongratis.timetracker.data.database.AppDatabase;
import com.nongratis.timetracker.data.entities.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
@RunWith(AndroidJUnit4.class)
public class TaskRepositoryTest {
    private TaskRepository taskRepository;
    private TaskDao taskDao;
    private AppDatabase appDatabase;

    @Before
    public void setup() {
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        taskDao = appDatabase.taskDao();
        taskRepository = new TaskRepository(ApplicationProvider.getApplicationContext());
    }

    @After
    public void tearDown() {
        appDatabase.close();
    }

    @Test
    public void testInsertAndRetrieveTask() {
        Task task = new Task();
        task.setWorkflowName("Test Workflow");
        task.setProjectName("Test Project");
        task.setDescription("Test Description");
        task.setStartTime(System.currentTimeMillis());
        task.setEndTime(System.currentTimeMillis() + 1000);

        taskRepository.insert(task);

        LiveData<List<Task>> tasksLiveData = taskRepository.getAllTasks();
        tasksLiveData.observeForever(tasks -> {
            assertNotNull(tasks);
            assertEquals(1, tasks.size());
            assertEquals("Test Workflow", tasks.get(0).getWorkflowName());
        });
    }
}