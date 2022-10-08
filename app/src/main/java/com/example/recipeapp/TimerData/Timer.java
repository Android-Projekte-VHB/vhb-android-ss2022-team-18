package com.example.recipeapp.TimerData;

import android.content.Context;

import com.example.recipeapp.R;
import com.example.recipeapp.TimerData.broadcast.TimerBroadcastListener;

import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Timer implements Runnable {

    public static String TIME_KEY = "TIME_KEY";
    private boolean isRunning;
    private int time;
    private long scheduledAt;
    private static final int TICK_RATE = 1000;
    private ScheduledFuture<?> scheduledFuture;
    private final TimerBroadcastListener listener;

    public Timer(TimerBroadcastListener listener){
        this.listener = listener;
        isRunning = false;
    }

    public void setTime(int time){
        this.time = time;
    }


    public void start()
    {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(this, TICK_RATE, TICK_RATE, TimeUnit.MILLISECONDS);
        scheduledAt = System.currentTimeMillis();
        isRunning = true;
    }

    public void stop(boolean isCancelled){
        if (isRunning){
            scheduledFuture.cancel(true);
            isRunning = false;
            if (isCancelled){
                listener.onTimerStopped();
            }
        }
    }

    @Override
    public void run() {
        int secondsSinceStart = (int) ((System.currentTimeMillis() - scheduledAt) / 1000);
        int remainingTimeInSeconds = time - secondsSinceStart;
        if (remainingTimeInSeconds > 0){
            listener.onTimerUpdated(remainingTimeInSeconds);
        }
        else {
            listener.onTimerDone();
            scheduledFuture.cancel(true);
        }
    }

    public static String getFormattedStringFromInt(Context context, int remainingSeconds) {
        DecimalFormat df = new DecimalFormat("00");
        int hours = remainingSeconds / 60 / 60;
        remainingSeconds = remainingSeconds - (hours * 60 * 60);
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        return context.getString(R.string.timer_template).replace("$HOURS", df.format(hours))
                .replace("$MINUTES", df.format(minutes))
                .replace("$SECONDS", df.format(seconds));
    }
}
