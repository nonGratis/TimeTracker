package com.nongratis.timetracker.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.nongratis.timetracker.R;

public class NotificationHelper {

    private static final String CHANNEL_ID = "TIMER_CHANNEL";
    private static final String CHANNEL_NAME = "Timer Notifications";
    private static final String CHANNEL_DESC = "Notifications for timer events";

    private Context context;
    private NotificationManager notificationManager;

    public NotificationHelper(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public Notification getNotification(String title, String elapsedTime) {
        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(elapsedTime)
                .setSmallIcon(R.drawable.ic_start)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT) // set the priority to high
                .build();
    }

    public void notify(int id, Notification notification) {
        notificationManager.notify(id, notification);
    }
}