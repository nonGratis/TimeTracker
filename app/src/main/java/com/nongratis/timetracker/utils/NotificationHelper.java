package com.nongratis.timetracker.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.nongratis.timetracker.R;

public class NotificationHelper {
    private static final String CHANNEL_ID = "TIMER_CHANNEL";
    private static final String CHANNEL_NAME = "Timer Channel";
    private static final String CHANNEL_DESC = "Channel for timer notifications";
    private static final int NOTIFICATION_ID = 1;

    private final Context context;
    private final NotificationManager notificationManager;

    public NotificationHelper(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void startNotification(String timerDuration, boolean isPaused) {
        Intent pauseIntent = new Intent("PAUSE_TIMER");
        PendingIntent pausePendingIntent = PendingIntent.getBroadcast(context, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent resumeIntent = new Intent("RESUME_TIMER");
        PendingIntent resumePendingIntent = PendingIntent.getBroadcast(context, 0, resumeIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent stopIntent = new Intent("STOP_TIMER");
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(context, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_start)
                .setContentTitle(context.getString(R.string.notificationTitle))
                .setContentText(timerDuration)
                .addAction(R.drawable.ic_stop, context.getString(R.string.stop), stopPendingIntent);

        if (isPaused) {
            builder.addAction(R.drawable.ic_start, context.getString(R.string.resume), resumePendingIntent);
        } else {
            builder.addAction(R.drawable.ic_pause, context.getString(R.string.pause), pausePendingIntent);
        }

        Notification notification = builder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }

    public void updateNotification(String timerDuration, boolean isPaused) {
        startNotification(timerDuration, isPaused);
    }
}