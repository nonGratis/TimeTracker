package com.nongratis.timetracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ResumeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent resumeIntent = new Intent("com.nongratis.timetracker.ACTION_RESUME_TIMER");
        LocalBroadcastManager.getInstance(context).sendBroadcast(resumeIntent);
    }
}