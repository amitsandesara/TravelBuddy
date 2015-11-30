package com.nyu.cs9033.travelbuddy.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;

import com.nyu.cs9033.travelbuddy.Models.Trips;
import com.nyu.cs9033.travelbuddy.R;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public class MainActivity extends AppCompatActivity {
    
    boolean googlePlusLogin;
    ImageView appLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
////Logo in Action bar
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setIcon(R.drawable.tbletters);
//            getSupportActionBar().setDisplayUseLogoEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }
//Logo in UI
//        appLogo = (ImageView) findViewById(R.id.Logo);
//        appLogo.setImageResource(R.drawable.tbletters);

// Enable Local Datastore.
        ParseObject.registerSubclass(Trips.class);
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "ME4oPzD9NyAwKrGB8hNnkQCCRbYjdb8Bd2YShI6B", "IvKnCjeM6ulQYVs6ZWd9n0V9tqgWdgFJsEBOHiCe");
        ParseInstallation.getCurrentInstallation().saveInBackground();

//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "bar");
//        testObject.saveInBackground();

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
        }, 1200);


    }




}

/**
 *
 *
 *
 * //created by akhila
 *
 *
 *

 import android.app.Activity;
 import android.app.ProgressDialog;
 import android.content.Intent;
 import android.os.Bundle;
 import android.view.Menu;
 import android.view.MenuItem;
 import android.view.View;
 import android.widget.Button;
 import android.widget.TextView;
 import android.widget.Toast;

 import com.parse.LogOutCallback;
 import com.parse.ParseException;
 import com.parse.ParseUser;


 public class MainActivity extends Activity {

 TextView userText;
 Button btnLogout;
 ProgressDialog progressDialog;

 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.activity_main);

 userText = (TextView) findViewById(R.id.txtMsg);
 Intent intent = getIntent();
 String user = "Hello " + intent.getStringExtra(DispatchActivity.EXTRA_MESSAGE) + "," + "\n"+ "Welcome to TravelBuddy";
 userText.setText(user);


 btnLogout = (Button) findViewById(R.id.btnLogout);
 //add on click listener to logout button
 btnLogout.setOnClickListener(new View.OnClickListener() {
 @Override
 public void onClick(View v) {
 logout();
 }
 });

 }


 private void logout(){

 //create and show progress dialogue
 progressDialog = new ProgressDialog(MainActivity.this);
 progressDialog.setMessage("Logging out ...wait!!");
 progressDialog.show();
 //parse log out
 ParseUser.logOutInBackground(new LogOutCallback() {
 @Override
 public void done(ParseException e) {
 //dismiss the progress dialogue
 progressDialog.dismiss();
 if(e != null ){
 Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
 }
 else{
 //start new activity
 Intent intent = new Intent(MainActivity.this, DispatchActivity.class);
 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
 startActivity(intent);
 }

 }
 });

 }


 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
 // Inflate the menu; this adds items to the action bar if it is present.
 getMenuInflater().inflate(R.menu.menu_main, menu);
 return true;
 }

 @Override
 public boolean onOptionsItemSelected(MenuItem item) {
 // Handle action bar item clicks here. The action bar will
 // automatically handle clicks on the Home/Up button, so long
 // as you specify a parent activity in AndroidManifest.xml.
 int id = item.getItemId();

 //noinspection SimplifiableIfStatement
 if (id == R.id.action_settings) {
 return true;
 }

 return super.onOptionsItemSelected(item);
 }
 }

 */
