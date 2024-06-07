package com.nongratis.timetracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.nongratis.timetracker.Constants;

public class PauseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent pauseTimer = new Intent(Constants.ACTION_PAUSE_TIMER);
        LocalBroadcastManager.getInstance(context).sendBroadcast(pauseTimer);
    }
}