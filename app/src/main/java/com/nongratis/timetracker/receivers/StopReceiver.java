package com.nongratis.timetracker.receivers;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nongratis.timetracker.Constants;
import com.nongratis.timetracker.fragments.TimerFragment;
import com.nongratis.timetracker.utils.NotificationHelper;

public class StopReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent stopTimer = new Intent(context, TimerFragment.class);
        stopTimer.setAction(Constants.ACTION_STOP_TIMER);
        context.sendBroadcast(stopTimer);

        // Cancel the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NotificationHelper.getNotificationId());
    }
}