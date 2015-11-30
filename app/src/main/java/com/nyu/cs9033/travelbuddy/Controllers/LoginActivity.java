package com.nyu.cs9033.travelbuddy.Controllers;

import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.nyu.cs9033.travelbuddy.R;

import java.io.InputStream;

public class LoginActivity extends AppCompatActivity implements
        ConnectionCallbacks, OnConnectionFailedListener, OnClickListener {

    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;

    private static final String PIC_SIZE = "400";
    private static final String TAG_GooglePlusConnection = "GooglePlusConnection";

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;

    /* A flag indicating that a PendingIntent is in progress and prevents
    * us from starting further intents.
    */
    private boolean mIntentInProgress;

    /* Track whether the sign-in button has been clicked so that we know to resolve
    * all issues preventing sign-in without waiting.
    */
    private boolean mSignInClicked;

    /* Store the connection result from onConnectionFailed callbacks so that we can
    * resolve them when the user clicks sign-in.
    */
    private ConnectionResult mConnectionResult;

    private SignInButton bSignIn;
    private Button bSignOut;
    private LinearLayout proLayout;
    private ImageView proPic;
    private TextView proName;
    private TextView proEmail;
    private Button bShare;
    private Button bRevoke;
    private Button goHome;
    public boolean UserSignedIn = false;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private Bitmap GProfilePhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
////        android.support.v7.app.ActionBar supportActionBar = getSupportActionBar();
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.login);
        getSupportActionBar().setTitle("  Login");
//        if (toolbar != null) {
//            getSupportActionBar().setLogo(R.drawable.tbletters);
//        }

//        FacebookSdk.sdkInitialize(getApplicationContext());
//        callbackManager = CallbackManager.Factory.create();

      //  loginButton = (LoginButton) findViewById(R.id.fbLogin_button);


//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//                mSignInClicked= false;
//                UserSignedIn= true;
//                AccessToken accessToken = loginResult.getAccessToken();
//                Log.e("FB", String.valueOf(accessToken));
//                Profile fbProfile = Profile.getCurrentProfile();
//                proName = (TextView) findViewById(R.id.pro_Name);
//                proName.setText(fbProfile.getName());
//                proLayout.setVisibility(View.VISIBLE);
//                bSignOut = (Button) findViewById(R.id.sign_out);
//                bSignOut.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        callFacebookLogout();
//                    }
//                });
//
//
//                final Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                startActivity(intent);
//                finish();
//            }

            /**
             * Logout From Facebook
             */
//            public void callFacebookLogout() {
//                LoginManager.getInstance().logOut();
//                proLayout.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onCancel() {
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                // App code
//            }
//
//        });


        if (!mSignInClicked)
        {
//            final Intent goToHome = new Intent(getApplicationContext(), HomeActivity.class);
//            startActivity(goToHome);
        }


//declaring variables
        bSignIn = (SignInButton) findViewById(R.id.sign_in_button);
        bSignOut = (Button) findViewById(R.id.sign_out);
        bShare = (Button) findViewById(R.id.share_button);
        bRevoke = (Button) findViewById(R.id.revokeAccess);
        proLayout = (LinearLayout) findViewById(R.id.pro_layout);
        proPic = (ImageView) findViewById(R.id.pro_Pic);
        proName = (TextView) findViewById(R.id.pro_Name);
        proEmail = (TextView) findViewById(R.id.pro_Email);
        goHome = (Button) findViewById(R.id.goToHome);

        bSignIn.setVisibility(View.VISIBLE);
        bSignOut.setVisibility(View.GONE);
        bShare.setVisibility(View.GONE);
        bRevoke.setVisibility(View.GONE);
        proLayout.setVisibility(View.GONE);
        goHome.setVisibility(View.GONE);

//button Onlicklisteners
        bSignIn.setOnClickListener(this);
        bSignOut.setOnClickListener(this);
        bShare.setOnClickListener(this);
        bRevoke.setOnClickListener(this);

        goHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent goToHome = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(goToHome);
            }
        });

//settings options for GoogleApiClient
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

    }



    @Override
    public void onClick(View v) {
// TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.sign_in_button:
// Signin with Google+
                signIn();
                break;
            case R.id.sign_out:
// Signout with Google+
                signOut();
                break;
            case R.id.share_button:
// Signout with Google+
                shareLink();
                break;
            case R.id.revokeAccess:
// Signout with Google+
                revokeAccess();
                break;

        }
    }

    private void revokeAccess() {
        Log.i("Revoke", "Revoke Access");
// Prior to disconnecting, run clearDefaultAccount().
        Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
        Plus.AccountApi.revokeAccessAndDisconnect(mGoogleApiClient)
                .setResultCallback(new ResultCallback<Status>() {

                    @Override
                    public void onResult(Status arg0) {
// mGoogleApiClient is now disconnected and access has been revoked.
// Trigger app logic to comply with the developer policies

                        mGoogleApiClient.disconnect();
                        mGoogleApiClient.connect();

                        UpdateLayout(false);
                    }

                });

    }

    private void shareLink() {
        Log.i("share","Sharing Link");
// Launch the Google+ share dialog with attribution to your app.
        Intent shareIntent = new PlusShare.Builder(this)
                .setType("text/plain")
                .setText("Know more about me")
                .setContentUrl(Uri.parse("http://www.amitsandesara.com"))
                .getIntent();

        startActivityForResult(shareIntent, 0);

    }

    protected void signOut() {
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
            UpdateLayout(false);
        }

    }


    private void signIn() {
        if(!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (SendIntentException e) {
// The intent was canceled before it was sent.  Return to the default
// state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {

        if (!mIntentInProgress) {
// Store the ConnectionResult so that we can use it later when the user clicks
// 'sign-in'.
            mConnectionResult = result;

            if (mSignInClicked) {
// The user has already clicked 'sign-in' so we attempt to resolve all
// errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    @Override
    public void onConnected(Bundle arg0) {
// TODO Auto-generated method stub
        mSignInClicked = false;
        Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        String personName = currentPerson.getDisplayName();

        Log.i(TAG_GooglePlusConnection, personName + " is connected!");

//Updating Profile Information
        UpdateProfile();
//Updating Layout
        UpdateLayout(true);

    }

    private void UpdateLayout(boolean signinStatus) {
// TODO Auto-generated method stub
        if(signinStatus)
        {
            bSignIn.setVisibility(View.GONE);
            bSignOut.setVisibility(View.VISIBLE);
            bShare.setVisibility(View.VISIBLE);
            bRevoke.setVisibility(View.VISIBLE);
            //proLayout.setVisibility(View.VISIBLE);
            goHome.setVisibility(View.VISIBLE);

        }
        else{
            bSignIn.setVisibility(View.VISIBLE);
            bSignOut.setVisibility(View.GONE);
            bShare.setVisibility(View.GONE);
            bRevoke.setVisibility(View.GONE);
            //proLayout.setVisibility(View.GONE);
            goHome.setVisibility(View.GONE);
        }

    }

    private void UpdateProfile() {
// TODO Auto-generated method stub
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
            String personName = currentPerson.getDisplayName();
            String personPhoto = currentPerson.getImage().getUrl();
            String personGooglePlusProfile = currentPerson.getUrl();
            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

            SharedPreferences googlePlusProfile = getSharedPreferences("Google+", MODE_PRIVATE);
            SharedPreferences.Editor spEditor = googlePlusProfile.edit();
            spEditor.putString("GDisplayName", personName);
            spEditor.putString("GEmail", email);
            spEditor.putString("GProfilePicURL", personPhoto);
            spEditor.commit();

            Log.i(TAG_GooglePlusConnection, personName);
            Log.i(TAG_GooglePlusConnection, email);
            proName.setText(personName);
            proEmail.setText(email);

            personPhoto = personPhoto.substring(0, personPhoto.length() - 2)
                    + PIC_SIZE;

            new UpdateImage().execute(personPhoto);
        }
    }

    private class UpdateImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
// Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
// Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
// Set the bitmap into ImageView
            GProfilePhoto = result;
            proPic.setImageBitmap(result);
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
// TODO Auto-generated method stub
        mGoogleApiClient.connect();
    }

}
/*
*
* //created by Akhila
* //simple login activity using email

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LoginActivity extends Activity{

    private EditText username;
   // private EditText emailaddress;
    private EditText password;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get view data
        username = (EditText) findViewById(R.id.editTextUsername);
        //emailaddress=(EditText) findViewById(R.id.editTextEmail);
        password = (EditText) findViewById(R.id.editTextPassword);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        //attaching on click listener with login button
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    private void login() {

        String username, password;
       // String emailaddress;

        username = this.username.getText().toString();
       // emailaddress = this.emailaddress.getText().toString();
        password = this.password.getText().toString();


        boolean isDataProper = validateData(username, password);

        if (isDataProper) {

            // setup and show progress dialog
            progress = new ProgressDialog(LoginActivity.this);
            progress.setMessage("Logging in ....");
            progress.show();

            //parse login in background
            ParseUser.logInInBackground(username,password, new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    //dismiss the progress dialog
                    progress.dismiss();

                    //check if there is any parse exception
                    if(e != null){
                        Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    else{

                        //Start a Intent for the Dispatch Activity
                        Intent intent = new Intent(LoginActivity.this, DispatchActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            });


        }
    }

        // data validation
    private boolean validateData(String username, String password){

        boolean isValidated = true;
        String msg="";
        if(username.equals("") ){
            isValidated = false;
            msg = "Username field cannot be left blank!!";
        }
        else if(password.equals("")){
            isValidated = false;
            msg = "Password field cannot be left blank!!";
        }

        if(!msg.equals(""))
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();

        return isValidated;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

*
* */