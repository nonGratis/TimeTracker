package com.nongratis.timetracker.utils;

import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class SyncScheduler {

    public static void schedulePeriodicSync() {
        PeriodicWorkRequest syncWorkRequest =
                new PeriodicWorkRequest.Builder(SyncWorker.class, 15, TimeUnit.MINUTES)
                        .build();
        WorkManager.getInstance().enqueue(syncWorkRequest);
    }
}
