package com.example.recipeapp.TimerData.broadcast;

public interface TimerBroadcastListener {
    void onTimerUpdated(int remainingTimeInSeconds);

    void onTimerDone();

    void onTimerStopped();
}
