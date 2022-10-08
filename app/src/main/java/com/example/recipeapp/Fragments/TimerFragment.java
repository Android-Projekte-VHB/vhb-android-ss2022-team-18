package com.example.recipeapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.recipeapp.R;
import com.example.recipeapp.TimerData.Timer;
import com.example.recipeapp.TimerData.broadcast.TimerBroadcastListener;
import com.example.recipeapp.TimerData.broadcast.TimerBroadcastReceiver;
import com.example.recipeapp.TimerData.notification.NotificationHelper;
import com.example.recipeapp.TimerData.preference.PreferenceHelper;
import com.example.recipeapp.TimerData.service.TimerService;

import java.text.DecimalFormat;

public class TimerFragment extends Fragment implements TimerBroadcastListener {

    private TextView timerText;
    private ImageButton startButton, stopButton;
    private EditText hours, minutes, seconds;
    private TimerBroadcastReceiver broadcastReceiver;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view =inflater.inflate(R.layout.fragment_timer, container, false);
        timerText = view.findViewById(R.id.time);
        startButton = view.findViewById(R.id.start_button);
        stopButton = view.findViewById(R.id.stop_button);
        hours = view.findViewById(R.id.hour_input);
        minutes = view.findViewById(R.id.minute_input);
        seconds = view.findViewById(R.id.second_input);
        initBroadcasts();
        setupNotifications();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startButton.setOnClickListener(v -> {
            startTimer();
        });
        stopButton.setOnClickListener(v -> {
            stopTimer();
        });
    }

    private void initBroadcasts() {
        broadcastReceiver = new TimerBroadcastReceiver(this);
    }

    private void setupNotifications() {
        NotificationHelper notificationHelper = new NotificationHelper(getActivity());
        notificationHelper.createNotificationChannel();
    }


    @Override
    public void onStart() {
        super.onStart();
        registerBroadcastReceiver();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateButtonStates();
    }

    private void updateButtonStates() {
        PreferenceHelper preferenceHelper = new PreferenceHelper(getContext());
        if (preferenceHelper.getBoolean(PreferenceHelper.TIMER_RUNNING_KEY, false)){
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        }
    }

    private void registerBroadcastReceiver() {
        broadcastReceiver = new TimerBroadcastReceiver(this);
        requireActivity().registerReceiver(broadcastReceiver, TimerBroadcastReceiver.getIntentFilter());
    }

    @Override
    public void onStop() {
        super.onStop();
        this.unregisterBroadcastReceiver();

    }

    private void unregisterBroadcastReceiver() {
        if (broadcastReceiver != null) {
            requireActivity().unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    private void stopTimer() {
        requireActivity().stopService(new Intent(getActivity(), TimerService.class));
    }

    private void startTimer() {
        if (checkInputs()) {
            int[] setTime = getSetTime();
            int timeInSeconds = (setTime[0] * 60 * 60) + (setTime[1] * 60) + (setTime[2]);
            if (timeInSeconds == 0) {
                Toast.makeText(getActivity(), getString(R.string.time_not_set), Toast.LENGTH_SHORT).show();
            } else {
                startTimerForTime(timeInSeconds);
            }
            resetEdits();
        } else {
            Toast.makeText(getActivity(), R.string.incorrect_input, Toast.LENGTH_SHORT).show();
        }
    }

    private void resetEdits() {
        hours.setText("");
        minutes.setText("");
        seconds.setText("");
    }

    private void startTimerForTime(int timeInSeconds) {
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        timerText.setText(Timer.getFormattedStringFromInt(getActivity(), timeInSeconds));
        Intent intent = new Intent(getActivity(), TimerService.class);
        intent.putExtra(Timer.TIME_KEY, timeInSeconds);
        requireActivity().startForegroundService(intent);
    }

    private boolean checkInputs() {
        int[] setTime = getSetTime();
        int h = setTime[0], m = setTime[1], s = setTime[2];
        return h <= 99 && m <= 59 && s <= 59;
    }

    @SuppressLint("SetTextI18n")
    private int[] getSetTime() {
        int h = 0, m = 0, s = 0;

        if (hours.getText().toString().isEmpty()) {
            hours.setText("00");
        }
        if (minutes.getText().toString().isEmpty()) {
            minutes.setText("00");
        }
        if (seconds.getText().toString().isEmpty()) {
            seconds.setText("00");
        }

        h = Integer.parseInt(hours.getText().toString());
        m = Integer.parseInt(minutes.getText().toString());
        s = Integer.parseInt(seconds.getText().toString());

        return new int[]{h, m, s};

    }

    private void updateTimerValue(int remainingSeconds) {
        DecimalFormat df = new DecimalFormat("00");
        int hours = remainingSeconds / 60 / 60;
        remainingSeconds = remainingSeconds - (hours * 60 * 60);
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        String currentTimer = getString(R.string.timer_template).replace("$HOURS", df.format(hours))
                .replace("$MINUTES", df.format(minutes))
                .replace("$SECONDS", df.format(seconds));
        timerText.setText(currentTimer);
    }

    private void resetAndNotify(String message) {
        timerText.setText("");
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTimerUpdated(int remainingTimeInSeconds) {
        requireActivity().runOnUiThread(() -> {
            timerText.setText(Timer.getFormattedStringFromInt(getContext(), remainingTimeInSeconds));
        });
    }

    @Override
    public void onTimerDone() {
        Log.d("TIMER_KEY", "Timer done");
        requireActivity().runOnUiThread(() -> {
            resetAndNotify("Timer done");
            runAlarmClock();
        });
    }

    public void runAlarmClock(){
        MediaPlayer music = MediaPlayer.create(getActivity(), R.raw.alarm_clock);
        music.start();
    }

    @Override
    public void onTimerStopped() {
        requireActivity().runOnUiThread(() -> {
            resetAndNotify("Timer stopped");
        });
    }


}