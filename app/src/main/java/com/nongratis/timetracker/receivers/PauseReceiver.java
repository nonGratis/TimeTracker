package com.nongratis.timetracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class PauseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent pauseIntent = new Intent("com.nongratis.timetracker.ACTION_PAUSE_TIMER");
        LocalBroadcastManager.getInstance(context).sendBroadcast(pauseIntent);
    }
}