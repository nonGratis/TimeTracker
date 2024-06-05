package com.nongratis.timetracker.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.nongratis.timetracker.data.dao.TimeAnalysisDao;
import com.nongratis.timetracker.data.entities.TimeAnalysisEntity;

@Database(entities = {TimeAnalysisEntity.class}, version = 1, exportSchema = false)
public abstract class TimeTrackerDatabase extends RoomDatabase {
    private static volatile TimeTrackerDatabase INSTANCE;

    public abstract TimeAnalysisDao timeAnalysisDao();

    public static TimeTrackerDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (TimeTrackerDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TimeTrackerDatabase.class, "time_tracker_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}