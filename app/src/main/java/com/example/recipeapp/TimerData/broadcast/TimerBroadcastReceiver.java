package com.example.recipeapp.TimerData.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class TimerBroadcastReceiver extends BroadcastReceiver {

    private static final String TIMER_UPDATED = "de.ur.mi.android.task.timer.TIMER_UPDATED";
    private static final String TIMER_DONE = "de.ur.mi.android.task.timer.TIMER_DONE";
    private static final String TIMER_STOPPED = "de.ur.mi.android.task.timer.TIMER_STOPPED";
    private static final String REMAINING_TIME_IN_SECONDS = "REMAINING_TIME_IN_SECONDS";

    private final TimerBroadcastListener listener;

    public TimerBroadcastReceiver(TimerBroadcastListener listener) {
        this.listener = listener;
    }

    public void unregisterReceiver(TimerBroadcastReceiver receiver){
        unregisterReceiver(receiver);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case TIMER_UPDATED:
                int remainingTimeInSeconds = intent.getExtras().getInt(REMAINING_TIME_IN_SECONDS);
                listener.onTimerUpdated(remainingTimeInSeconds);
                break;
            case TIMER_DONE:
                listener.onTimerDone();
                break;
            case TIMER_STOPPED:
                listener.onTimerStopped();
                break;
        }
    }


    public static IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(TimerBroadcastReceiver.TIMER_UPDATED);
        filter.addAction(TimerBroadcastReceiver.TIMER_DONE);
        filter.addAction(TimerBroadcastReceiver.TIMER_STOPPED);
        return filter;
    }


    public static Intent getUpdateIntent(int remainingTimeInSeconds) {
        Intent intent = new Intent();
        intent.setAction(TIMER_UPDATED);
        intent.putExtra(REMAINING_TIME_IN_SECONDS,remainingTimeInSeconds);
        return intent;
    }

    public static Intent getEndIntent() {
        Intent intent = new Intent();
        intent.setAction(TIMER_DONE);
        return intent;
    }

    public static Intent getCancelledIntent() {
        Intent intent = new Intent();
        intent.setAction(TIMER_STOPPED);
        return intent;
    }

}

