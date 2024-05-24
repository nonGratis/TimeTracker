package com.nongratis.timetracker.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.nongratis.timetracker.data.dao.TaskDao;
import com.nongratis.timetracker.data.entities.Task;

@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
