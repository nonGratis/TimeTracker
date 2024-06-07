package com.nongratis.timetracker.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nongratis.timetracker.utils.NotificationHelper;

public class StopReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent stopIntent = new Intent();
        stopIntent.setClassName("com.nongratis.timetracker", "com.nongratis.timetracker.receivers.StopReceiver");
        stopIntent.setAction("com.nongratis.timetracker.ACTION_STOP_TIMER");

        // Cancel the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NotificationHelper.getNotificationId());
    }
}