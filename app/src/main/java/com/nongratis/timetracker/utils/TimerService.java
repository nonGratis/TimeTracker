package com.nongratis.timetracker.utils;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;

import com.nongratis.timetracker.utils.NotificationHelper;

public class TimerService extends Service {

    private static final int NOTIFICATION_ID = 1;
    private NotificationHelper notificationHelper;
    private long startTime;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationHelper = new NotificationHelper(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startTime = System.currentTimeMillis();
        startForegroundService();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startForegroundService() {
        Notification notification = notificationHelper.getNotification("Timer Started", getElapsedTime());
        startForeground(NOTIFICATION_ID, notification);

        new Thread(() -> {
            while (true) {
                SystemClock.sleep(1000);
                notificationHelper.notify(NOTIFICATION_ID, notificationHelper.getNotification("Timer Started", getElapsedTime()));
            }
        }).start();
    }

    private String getElapsedTime() {
        long elapsedTime = System.currentTimeMillis() - startTime;
        int seconds = (int) (elapsedTime / 1000);
        int minutes = seconds / 60;
        int hours = minutes / 60;
        seconds = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}