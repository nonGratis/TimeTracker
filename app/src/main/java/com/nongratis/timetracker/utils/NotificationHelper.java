package com.nongratis.timetracker.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.nongratis.timetracker.Constants;
import com.nongratis.timetracker.R;

public class NotificationHelper {
    private static final String CHANNEL_ID = "TIMER_CHANNEL";
    private static final String CHANNEL_NAME = "Timer Channel";
    private static final String CHANNEL_DESC = "Channel for timer notifications";
    private final Context context;
    private final NotificationManager notificationManager;
    private static final int NOTIFICATION_ID = 1;
    public static int getNotificationId() {
        return NOTIFICATION_ID;
    }
    public NotificationHelper(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(CHANNEL_DESC);
        notificationManager.createNotificationChannel(channel);
    }

    public void startNotification(String timerDuration, boolean isPaused) {
        Intent pauseIntent = new Intent(Constants.ACTION_PAUSE_TIMER);
        PendingIntent pausePendingIntent = PendingIntent.getBroadcast(context, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent resumeIntent = new Intent(Constants.ACTION_RESUME_TIMER);
        PendingIntent resumePendingIntent = PendingIntent.getBroadcast(context, 0, resumeIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Intent stopIntent = new Intent(Constants.ACTION_STOP_TIMER);
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