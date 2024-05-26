package com.nongratis.timetracker.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.nongratis.timetracker.data.dao.TaskDao;
import com.nongratis.timetracker.data.entities.Task;
/**
 * AppDatabase is an abstract class that extends RoomDatabase.
 * It serves as the main database holder and serves as the main access point for the underlying connection to your app's persisted data.
 * The class is annotated with @Database, lists the entities within the database and the version number.
 * It includes an abstract method for each @Dao that is associated with that Database.
 */
@Database(entities = {Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    /**
     * Abstract method with no parameters.
     * It returns an instance of TaskDao which serves as an access point to the Task table in the database.
     *
     * @return an instance of TaskDao.
     */
    public abstract TaskDao taskDao();
}
