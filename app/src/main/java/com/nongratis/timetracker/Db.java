package com.nongratis.timetracker;

import android.app.Application;
import androidx.room.Room;
import com.nongratis.timetracker.data.database.AppDatabase;

public class Db extends Application {
    private static AppDatabase database;
    private static Db instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initializeDatabase();
    }

    public static void initializeDatabase() {
        if (instance == null) {
            throw new IllegalStateException("Db instance must be initialized before calling initializeDatabase");
        }
        if (database == null) {
            database = Room.databaseBuilder(instance.getApplicationContext(),
                    AppDatabase.class, "timetracker-db").build();
        }
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}