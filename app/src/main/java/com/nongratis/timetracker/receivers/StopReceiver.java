package com.nongratis.timetracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class StopReceiver extends BroadcastReceiver {
    private static final String TAG = "StopReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.i(TAG, "Received stop intent");

            // Send a local broadcast to stop the timer
            Intent stopIntent = new Intent("com.nongratis.timetracker.ACTION_STOP_TIMER");
            LocalBroadcastManager.getInstance(context).sendBroadcast(stopIntent);

            Log.i(TAG, "Stop action set");
        } catch (Exception e) {
            Log.e(TAG, "Exception in" + TAG, e);
        }
    }
}