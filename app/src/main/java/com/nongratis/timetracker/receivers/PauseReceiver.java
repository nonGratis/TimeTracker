package com.nongratis.timetracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PauseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent pauseIntent = new Intent();
        pauseIntent.setClassName("com.nongratis.timetracker", "com.nongratis.timetracker.receivers.PauseReceiver");
        pauseIntent.setAction("com.nongratis.timetracker.ACTION_PAUSE_TIMER");
    }
}