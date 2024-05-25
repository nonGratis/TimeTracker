package com.nongratis.timetracker.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nongratis.timetracker.fragments.TimerFragment;

public class PauseReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent pauseTimer = new Intent(context, TimerFragment.class);
        pauseTimer.setAction("PAUSE_TIMER");
        context.sendBroadcast(pauseTimer);
    }
}