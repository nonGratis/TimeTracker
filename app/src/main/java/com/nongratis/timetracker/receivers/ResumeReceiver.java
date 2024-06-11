package com.nongratis.timetracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class ResumeReceiver extends BroadcastReceiver {
    private static final String TAG = "ResumeReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.i(TAG, "Received resume intent");

            Intent resumeIntent = new Intent("com.nongratis.timetracker.ACTION_RESUME_TIMER");
            LocalBroadcastManager.getInstance(context).sendBroadcast(resumeIntent);

            Log.d(TAG, "Resume action set");
        } catch (Exception e) {
            Log.e(TAG, "Exception in" + TAG, e);
        }
    }
}