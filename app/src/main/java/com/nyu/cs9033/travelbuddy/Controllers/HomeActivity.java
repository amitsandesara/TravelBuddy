package com.nyu.cs9033.travelbuddy.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.nyu.cs9033.travelbuddy.R;

public class HomeActivity extends AppCompatActivity {


    private static final String TAG_Menu = "ACTION MENU ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);

        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.shareApplication) {
            // DO SOMETHING
            Log.i(TAG_Menu, "Share app clicked");
            final Toast toast = Toast.makeText(getApplicationContext(),"Yet to create Help page..!!", Toast.LENGTH_SHORT);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 1000);      //Remove this toast once share application is functional
            return true;
        }
        else if (id == R.id.action_settings){
            final Intent openSettings = new Intent(getApplicationContext(), AppSettingsActivity.class);
            startActivity(openSettings);
            return true;
        }
        else if (id == R.id.Help){
            final Toast toast = Toast.makeText(getApplicationContext(),"Yet to create Help page..!!", Toast.LENGTH_SHORT);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 1000);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
