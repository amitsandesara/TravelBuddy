package com.nyu.cs9033.travelbuddy.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;

import com.nyu.cs9033.travelbuddy.R;

public class MainActivity extends AppCompatActivity {
    
    boolean googlePlusLogin;
    ImageView appLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//Logo in Action bar

        if (getSupportActionBar() != null) {
            getSupportActionBar().setIcon(R.drawable.tbletters);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
//Logo in UI
//        appLogo = (ImageView) findViewById(R.id.Logo);
//        appLogo.setImageResource(R.drawable.tbletters);
//



        if (googlePlusLogin)
        {
            // Build GoogleApiClient to request access to the basic user profile
        }
        else
            startLoginActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    private void startLoginActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, 1500);


    }




}

