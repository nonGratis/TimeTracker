package com.nongratis.timetracker.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nongratis.timetracker.data.entities.Task;

import java.util.List;

/**
 * TaskDao is an interface that serves as the DAO (Data Access Object) for the Task entity.
 * It provides methods to perform database operations on the tasks table.
 * It is a part of the Room Persistence Library which provides an abstraction layer over SQLite.
 */
@Dao
public interface TaskDao {
    /**
     * TaskDao is an interface that serves as the DAO (Data Access Object) for the Task entity.
     * It provides methods to perform database operations on the tasks table.
     * It is a part of the Room Persistence Library which provides an abstraction layer over SQLite.
     */
    @Insert
    void insert(Task task);

    /**
     * Inserts a new task into the tasks table.
     *
     * @param task The task to be inserted.
     */
    @Update
    void update(Task task);

    /**
     * Deletes a task from the tasks table.
     *
     * @param task The task to be deleted.
     */
    @Delete
    void delete(Task task);

    /**
     * Retrieves all tasks from the tasks table.
     *
     * @return A list of all tasks in the tasks table.
     */
    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getAllTasks();

    /**
     * Retrieves tasks from the tasks table within a specified time period.
     *
     * @param startTime The start time of the period.
     * @param endTime   The end time of the period.
     * @return A LiveData list of tasks within the specified period.
     */
    @Query("SELECT * FROM tasks WHERE startTime >= :startTime AND endTime <= :endTime")
    LiveData<List<Task>> getTasksByPeriod(long startTime, long endTime);

}
