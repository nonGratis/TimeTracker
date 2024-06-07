package com.nongratis.timetracker.managers;

import android.content.Context;

import com.nongratis.timetracker.utils.NotificationHelper;

public class NotificationManager {
    private final NotificationHelper notificationHelper;

    public NotificationManager(Context context) {
        this.notificationHelper = new NotificationHelper(context);
    }

    public void updateNotification(String elapsedTime, boolean isPaused) {
        notificationHelper.updateNotification(elapsedTime, isPaused);
    }
}