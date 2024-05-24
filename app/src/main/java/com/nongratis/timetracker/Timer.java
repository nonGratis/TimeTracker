package com.nongratis.timetracker;

import android.app.Application;
import androidx.room.Room;
import com.nongratis.timetracker.data.database.AppDatabase;

public class Timer extends Application {
    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "timetracker-db").build();
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
