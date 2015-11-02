package com.nyu.cs9033.travelbuddy;

/**
 * Created by amitsandesara on 11/2/15.
 */

import android.content.SharedPreferences;
public enum AppPreferences {
    DEVICE_ID {
        @Override
        public String getString(SharedPreferences sharedPreferences){
            return sharedPreferences.getString(DEVICE_ID.name(), "");
        }

        @Override
        public void set(SharedPreferences sharedPreferences, String value){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(DEVICE_ID.name(), value); editor.commit();
        }
    };
    public abstract String getString(SharedPreferences sharedPreferences);
    public abstract void set(SharedPreferences sharedPreferences,
                             String value);
}

