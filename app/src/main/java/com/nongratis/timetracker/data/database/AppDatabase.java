package com.nongratis.timetracker.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.nongratis.timetracker.data.dao.TaskDao;
import com.nongratis.timetracker.data.dao.TimeAnalysisDao; // Add this import
import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.data.entities.TimeAnalysisEntity; // Add this import

/**
 * AppDatabase is an abstract class that extends RoomDatabase.
 * It serves as the main database holder and serves as the main access point for the underlying connection to your app's persisted data.
 * The class is annotated with @Database, lists the entities within the database and the version number.
 * It includes an abstract method for each @Dao that is associated with that Database.
 */
@Database(entities = {Task.class, TimeAnalysisEntity.class}, version = 1) // Add TimeAnalysisEntity
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    /**
     * Abstract method with no parameters.
     * It returns an instance of TaskDao which serves as an access point to the Task table in the database.
     *
     * @return an instance of TaskDao.
     */
    public abstract TaskDao taskDao();
    public abstract TimeAnalysisDao timeAnalysisDao(); // Add this abstract method

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}