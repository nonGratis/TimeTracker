package com.nongratis.timetracker.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.nongratis.timetracker.Constants;
import com.nongratis.timetracker.fragments.TimerFragment;

public class ResumeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent stopTimer = new Intent(context, TimerFragment.class);
        stopTimer.setAction(Constants.ACTION_RESUME_TIMER);
        context.sendBroadcast(stopTimer);
    }
}