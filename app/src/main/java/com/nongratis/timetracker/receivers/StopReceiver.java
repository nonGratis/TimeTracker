package com.nongratis.timetracker.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.nongratis.timetracker.utils.NotificationHelper;

public class StopReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent stopIntent = new Intent("com.nongratis.timetracker.ACTION_STOP_TIMER");
        LocalBroadcastManager.getInstance(context).sendBroadcast(stopIntent);

        // Cancel the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NotificationHelper.getNotificationId());
    }
}