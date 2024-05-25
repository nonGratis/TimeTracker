package com.nongratis.timetracker.utils;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.annotation.Nullable;

public class TimerService extends Service {

    private static final int NOTIFICATION_ID = 1;
    private NotificationHelper notificationHelper;
    private TimerLogic timerLogic;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationHelper = new NotificationHelper(this);
        timerLogic = new TimerLogic();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timerLogic.startTimer();
        startForegroundService();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timerLogic.stopTimer();
        stopForeground(true);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startForegroundService() {
        Notification notification = notificationHelper.getNotification("Timer Started", timerLogic.getElapsedTime());
        startForeground(NOTIFICATION_ID, notification);

        new Thread(() -> {
            while (true) {
                if (timerLogic.isRunning()) {
                    SystemClock.sleep(1000);
                    notificationHelper.notify(NOTIFICATION_ID, notificationHelper.getNotification("Timer Started", timerLogic.getElapsedTime()));
                }
            }
        }).start();
    }
}