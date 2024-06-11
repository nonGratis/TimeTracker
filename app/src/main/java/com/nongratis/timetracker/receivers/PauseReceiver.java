package com.nongratis.timetracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class PauseReceiver extends BroadcastReceiver {
    private static final String TAG = "PauseReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.i(TAG, "Received pause intent");

            Intent pauseIntent = new Intent("com.nongratis.timetracker.ACTION_PAUSE_TIMER");
            LocalBroadcastManager.getInstance(context).sendBroadcast(pauseIntent);

            Log.d(TAG, "Pause action set");
        } catch (Exception e) {
            Log.e(TAG, "Exception in" + TAG, e);
        }
    }
}