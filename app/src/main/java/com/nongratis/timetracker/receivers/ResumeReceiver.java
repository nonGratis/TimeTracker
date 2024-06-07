package com.nongratis.timetracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.nongratis.timetracker.Constants;

public class ResumeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent resumeTimer = new Intent(Constants.ACTION_RESUME_TIMER);
        LocalBroadcastManager.getInstance(context).sendBroadcast(resumeTimer);
    }
}