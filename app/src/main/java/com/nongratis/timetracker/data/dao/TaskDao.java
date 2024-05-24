package com.nongratis.timetracker.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.nongratis.timetracker.data.entities.Task;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Query("SELECT * FROM tasks")
    List<Task> getAllTasks();
}
