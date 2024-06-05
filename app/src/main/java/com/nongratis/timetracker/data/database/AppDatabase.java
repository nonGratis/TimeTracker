// AppDatabase.java
package com.nongratis.timetracker.data.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.nongratis.timetracker.data.dao.TaskDao;
import com.nongratis.timetracker.data.dao.TimeAnalysisDao;
import com.nongratis.timetracker.data.entities.Task;
import com.nongratis.timetracker.data.entities.TimeAnalysisEntity;

@Database(entities = {Task.class, TimeAnalysisEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase INSTANCE;

    public abstract TaskDao taskDao();
    public abstract TimeAnalysisDao timeAnalysisDao();

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "app_database")
                            .addMigrations(MIGRATION_1_2) // Ensure this migration is added
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Perform necessary schema changes here
            database.execSQL("CREATE TABLE IF NOT EXISTS `time_analysis` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `projectName` TEXT, `workflowCategory` TEXT, `duration` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL)");
        }
    };
}