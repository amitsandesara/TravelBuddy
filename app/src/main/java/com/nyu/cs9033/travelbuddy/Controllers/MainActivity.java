package com.nyu.cs9033.travelbuddy.Controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.nyu.cs9033.travelbuddy.Models.Person;
import com.nyu.cs9033.travelbuddy.Models.Trips;
import com.nyu.cs9033.travelbuddy.R;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// Enable Local Datastore.

        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(Trips.class);
        ParseObject.registerSubclass(Person.class);
        Parse.initialize(this, "ME4oPzD9NyAwKrGB8hNnkQCCRbYjdb8Bd2YShI6B", "IvKnCjeM6ulQYVs6ZWd9n0V9tqgWdgFJsEBOHiCe");
        ParseInstallation.getCurrentInstallation().saveInBackground();

        SharedPreferences googlePlusProfile = getSharedPreferences("Google+", MODE_PRIVATE);
        Boolean signedIn = googlePlusProfile.getBoolean("UserSignedIn", false);
        if (signedIn)
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    final Intent mainIntent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 1000);
        }
        else{
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
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
