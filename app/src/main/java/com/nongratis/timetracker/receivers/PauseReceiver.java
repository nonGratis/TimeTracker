package com.nongratis.timetracker.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PauseReceiver extends BroadcastReceiver {
    private static final String TAG = "PauseReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Log.i(TAG, "Received pause intent");

            Intent pauseIntent = new Intent();
            pauseIntent.setClassName("com.nongratis.timetracker", "com.nongratis.timetracker.receivers.PauseReceiver");
            pauseIntent.setAction("com.nongratis.timetracker.ACTION_PAUSE_TIMER");

            Log.d(TAG, "Pause action set");
        } catch (Exception e) {
            Log.e(TAG, "Exception in" + TAG, e);
        }
    }
}