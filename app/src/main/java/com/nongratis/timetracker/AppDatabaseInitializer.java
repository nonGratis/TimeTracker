package com.nongratis.timetracker;

import android.app.Application;

import androidx.room.Room;

import com.nongratis.timetracker.data.database.AppDatabase;
import com.nongratis.timetracker.data.executor.DatabaseExecutor;

public class AppDatabaseInitializer extends Application {
    private static AppDatabase database;
    private static AppDatabaseInitializer instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initializeDatabase();
    }

    public static void initializeDatabase() {
        if (instance == null) {
            throw new IllegalStateException("AppDatabaseInitializer instance must be initialized before calling initializeDatabase");
        }
        if (database == null) {
            DatabaseExecutor.getExecutor().execute(() -> {
                database = Room.databaseBuilder(instance.getApplicationContext(),
                        AppDatabase.class, "timetracker-db").build();
            });
        }
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}