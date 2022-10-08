package com.example.recipeapp.TimerData.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceHelper {
    public static final String TIMER_RUNNING_KEY = "de.ur.mi.android.timer.TIMER.RUNNING.KEY";

    private SharedPreferences preferences;
    private Context context;

    public PreferenceHelper(Context context){
        this.context = context;
        initPreferences();
    }

    private void initPreferences() {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void put(String key, boolean value){
        preferences.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defaultValue){
        return preferences.getBoolean(key, defaultValue);
    }
}
