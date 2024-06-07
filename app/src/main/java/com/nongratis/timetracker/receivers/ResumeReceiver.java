package com.nongratis.timetracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ResumeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent resumeIntent = new Intent();
        resumeIntent.setClassName("com.nongratis.timetracker", "com.nongratis.timetracker.receivers.ResumeReceiver");
        resumeIntent.setAction("com.nongratis.timetracker.ACTION_RESUME_TIMER");
    }
}