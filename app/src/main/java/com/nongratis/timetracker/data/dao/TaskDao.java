package com.nongratis.timetracker.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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
     * Retrieves all tasks from the tasks table.
     *
     * @return A list of all tasks in the tasks table.
     */
    @Query("SELECT * FROM tasks")
    LiveData<List<Task>> getAllTasks();

    /**
     * Retrieves all tasks from the tasks table that have a timestamp within the specified period.
     *
     * @param startTime The start of the period.
     * @param endTime The end of the period.
     * @return A list of all tasks in the tasks table that have a timestamp within the specified period.
     */
    @Query("SELECT * FROM tasks WHERE startTime >= :startTime AND endTime <= :endTime")
    LiveData<List<Task>> getTasksByPeriod(long startTime, long endTime);

}
