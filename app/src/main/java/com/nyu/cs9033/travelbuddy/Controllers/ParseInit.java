package com.nyu.cs9033.travelbuddy.Controllers;

import android.app.Application;
import android.util.Log;

import com.nyu.cs9033.travelbuddy.Models.Trips;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

/**
 * Created by amitsandesara on 12/12/15.
 */
public class ParseInit extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Trips.class);
        Parse.enableLocalDatastore(getBaseContext());
        Parse.initialize(this, "ME4oPzD9NyAwKrGB8hNnkQCCRbYjdb8Bd2YShI6B", "IvKnCjeM6ulQYVs6ZWd9n0V9tqgWdgFJsEBOHiCe");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        Log.d("Parse", " Initialized");
    }
}
