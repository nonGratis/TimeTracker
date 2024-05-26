package com.nongratis.timetracker;

import android.app.Application;

import androidx.room.Room;

import com.nongratis.timetracker.data.database.AppDatabase;
import com.nongratis.timetracker.data.executor.DatabaseExecutor;
/**
 * AppDatabaseInitializer is a class that extends the Application class.
 * It initializes the AppDatabase and provides a global point of access to it.
 * It also ensures that the database is initialized when the application starts.
 */
public class AppDatabaseInitializer extends Application {
    /**
     * A static instance of AppDatabase which represents the database for the application.
     */
    private static AppDatabase database;

    /**
     * A static instance of AppDatabaseInitializer which represents the instance of this class.
     */
    private static AppDatabaseInitializer instance;

    /**
     * This method initializes the database for the application.
     * It throws an IllegalStateException if the AppDatabaseInitializer instance is not initialized.
     * It creates a new database if the database is not already initialized.
     */
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

    /**
     * This method returns the instance of the AppDatabase.
     *
     * @return the instance of AppDatabase.
     */
    public static AppDatabase getDatabase() {
        return database;
    }

    /**
     * This method is called when the application is starting, before any activity, service, or receiver objects (excluding content providers) have been created.
     * It initializes the instance of this class and the database.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initializeDatabase();
    }
}