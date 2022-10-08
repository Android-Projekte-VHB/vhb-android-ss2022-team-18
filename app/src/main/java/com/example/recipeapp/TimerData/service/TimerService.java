package com.example.recipeapp.TimerData.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.example.recipeapp.Fragments.TimerFragment;
import com.example.recipeapp.R;
import com.example.recipeapp.TimerData.Timer;
import com.example.recipeapp.TimerData.broadcast.TimerBroadcastListener;
import com.example.recipeapp.TimerData.broadcast.TimerBroadcastReceiver;
import com.example.recipeapp.TimerData.notification.NotificationHelper;
import com.example.recipeapp.TimerData.preference.PreferenceHelper;


public class TimerService extends Service {
    private NotificationHelper notificationHelper;
    private Timer timer;
    private PowerManager.WakeLock wakeLock;
    private PreferenceHelper preferenceHelper;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initNotifications();
        startForeground(NotificationHelper.NOTIFICATION_ID_TIMER_SERVICE,
                notificationHelper.createNotification(getString(R.string.notification_title),
                        getString(R.string.notification_message_updated).replace("$TIME", String.valueOf(intent.getIntExtra(Timer.TIME_KEY, 0))),
                        R.drawable.ic_launcher_foreground,
                        notificationHelper.createContentIntent(TimerFragment.class)));
        initPreferences();
        initTimer(intent);
        setTimerRunning(true);
        initWakeLock(getTimeFromIntent(intent));
        return START_STICKY;
    }

    private void initNotifications() {
        notificationHelper = new NotificationHelper(this);
    }

    private void initPreferences() {
        preferenceHelper = new PreferenceHelper(this);
    }

    private void initTimer(Intent intent) {
        int time = getTimeFromIntent(intent);

        timer = new Timer(new TimerBroadcastListener() {
            @Override
            public void onTimerUpdated(int remainingTimeInSeconds) {
                broadcastTimerUpdated(remainingTimeInSeconds);
                adjustNotification(remainingTimeInSeconds);
            }

            @Override
            public void onTimerDone() {
                timer.stop(false);
                stopSelf();
                broadcastTimerDone();
                adjustNotification(getString(R.string.notification_message_done));
            }

            @Override
            public void onTimerStopped() {
                stopSelf();
                broadcastTimerStopped();
                adjustNotification(getString(R.string.notification_message_stopped));
            }
        });
        timer.setTime(time);
        timer.start();
    }

    private int getTimeFromIntent(Intent intent) {
        return intent.getIntExtra(Timer.TIME_KEY, 0);
    }

    private void initWakeLock(int time) {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Timer::WakeLockTag");
        wakeLock.acquire(time);
    }

    @Override
    public void onDestroy() {
        timer.stop(true);
        setTimerRunning(false);
        wakeLock.release();
        stopForeground(false);
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void adjustNotification(int remainingTimeInSeconds){
        Notification notification = notificationHelper.createNotification(getString(R.string.notification_title),
                getString(R.string.notification_message_updated).replace("$TIME", Timer.getFormattedStringFromInt(this, remainingTimeInSeconds)),
                R.drawable.ic_launcher_foreground,
                notificationHelper.createContentIntent(TimerFragment.class));
        notificationHelper.showNotification(NotificationHelper.NOTIFICATION_ID_TIMER_SERVICE, notification);
    }

    private void adjustNotification(String message){
        Notification notification = notificationHelper.createNotification(getString(R.string.notification_title),
                message,
                R.drawable.ic_launcher_foreground,
                notificationHelper.createContentIntent(TimerFragment.class));
        notificationHelper.showNotification(NotificationHelper.NOTIFICATION_ID_TIMER_SERVICE, notification);
    }


    private void broadcastTimerUpdated(int remainingTime)
    {
        Intent intent = TimerBroadcastReceiver.getUpdateIntent(remainingTime);
        sendBroadcast(intent);
    }

    private void broadcastTimerDone()
    {
        Intent intent = TimerBroadcastReceiver.getEndIntent();
        sendBroadcast(intent);
    }

    private void broadcastTimerStopped()
    {
        Intent intent = TimerBroadcastReceiver.getCancelledIntent();
        sendBroadcast(intent);
    }

    private void setTimerRunning(boolean isRunning){
        Log.d("PREFS", "Put Value in prefs");
        preferenceHelper.put(PreferenceHelper.TIMER_RUNNING_KEY, isRunning);
    }
}
