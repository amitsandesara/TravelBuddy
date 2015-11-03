package com.nyu.cs9033.travelbuddy.Controllers;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.nyu.cs9033.travelbuddy.Models.Trip;
import com.nyu.cs9033.travelbuddy.R;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateTripActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG1 = "CreateTripActivity";
    private SimpleDateFormat dateFormatter;
    private EditText _dateOfTrip;
    private EditText _friends;
    private static final int CONTACT_PICKER_RESULT = 1001;
    private static final String DEBUG_TAG = "Contact List";
    private static final int RESULT_OK = -1;
    static final int PICK_CONTACT = 1;  // The request code
    private DatePickerDialog DatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        if ((getSupportActionBar() == null)) {
            throw new AssertionError();
        }
        else
        {
            getSupportActionBar().setIcon(R.drawable.ic_airplanemode_active_black_24dp);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        setContentView(R.layout.activity_createtrip);
        createTrip();
        // TODO - fill in here
    }

    /**
     * This method should be used to
     * instantiate a Trip model object.
     *
     * @return The Trip as represented
     * by the View.
     */
    public Trip createTrip() {
        final EditText trip_Location = (EditText) findViewById(R.id.tripLocation);
        final EditText trip_Details = (EditText) findViewById(R.id.tripDetails);
        _dateOfTrip = (EditText) findViewById(R.id.tripDate);
        _friends = (EditText) findViewById(R.id.friends);
        final EditText _creatorOfTrip = (EditText) findViewById(R.id.creatorName);
        setDateTimeField();

//        _friends.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//                startActivityForResult(intent, PICK_CONTACT);
//            }
//        });


        final Button createTrip = (Button) findViewById(R.id.submitCreateTrip);
        createTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripLocation = trip_Location.getText().toString();
                //Log.i(TAG1, "Value of location in create activity: " + tripLocation);
                String tripDetails = trip_Details.getText().toString();
                String friends = _friends.getText().toString();
                String dateOfTrip = _dateOfTrip.getText().toString();
                String creatorOfTrip = _creatorOfTrip.getText().toString();


                if (trip_Location.getText().toString().length() == 0) {
                    final Toast toast = Toast.makeText(CreateTripActivity.this, "Please enter Trip Location", Toast.LENGTH_SHORT);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 500);
                } else if (_dateOfTrip.getText().toString().length() == 0) {
                    final Toast toast = Toast.makeText(CreateTripActivity.this, "Please enter date of trip", Toast.LENGTH_SHORT);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 500);
                } else if (trip_Details.getText().toString().length() == 0) {
                    final Toast toast = Toast.makeText(CreateTripActivity.this, "Please enter trip details", Toast.LENGTH_SHORT);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 500);
                } else {
                    Trip objCreateTrip = new Trip();
                    if (creatorOfTrip.toString().length() == 0) {
                        creatorOfTrip = "Amitkumar Sandesara";
                        Log.i(TAG1, creatorOfTrip + "MANUAL");
                    }
                    if (friends.length() == 0) {
                        friends = "  ";
                    }
                    objCreateTrip.setTrip_Location(tripLocation);
                    objCreateTrip.setTrip_Details(tripDetails);
                    objCreateTrip.setTrip_Date(dateOfTrip);
                    objCreateTrip.setFriends(friends);
                    objCreateTrip.setCreatedBy(creatorOfTrip);
                    saveTrip(objCreateTrip);

                  
                }
            }
        });

        // TODO - fill in here

        return null;
    }

//    @Override
//    public void onActivityResult(int reqCode, int resultCode, Intent data){ super.onActivityResult(reqCode, resultCode, data);
//
//        switch(reqCode)
//        {
//            case (PICK_CONTACT):
//                if (resultCode == Activity.RESULT_OK)
//                {
//                    Uri contactData = data.getData();
//                    Cursor c = managedQuery(contactData, null, null, null, null);
//                    if (c.moveToFirst())
//                    {
//                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
//
//                        String hasName =
//                                c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
//
//                        if (hasName.equalsIgnoreCase("1"))
//                        {
//                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
//                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
//                            phones.moveToFirst();
//                            String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            // Toast.makeText(getApplicationContext(), cNumber, Toast.LENGTH_SHORT).show();
//                            //setCn(cNumber);
//                        }
//                    }
//                }
//        }
//    }


    private void setDateTimeField() {
        _dateOfTrip.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                _dateOfTrip.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {
        if (view == _dateOfTrip) {
            hideSoftKeyboard();
            DatePickerDialog.show();
        }
    }
    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }


    /**
     * For HW2 you should treat this method as a
     * way of sending the Trip data back to the
     * main Activity.
     *
     * Note: If you call finish() here the Activity
     * will end and pass an Intent back to the
     * previous Activity using setResult().
     *
     * @return whether the Trip was successfully
     * saved.
     */

    public boolean saveTrip(Trip trip) {


//        ParseObject gameScore = new ParseObject("GameScore");
//        gameScore.put("score", 1337);
//        gameScore.put("playerName", "Sean Plott");
//        gameScore.put("cheatMode", false);
//        gameScore.saveInBackground();
//        Intent createTripIntent = new Intent(CreateTripActivity.this, MainActivity.class);
//        createTripIntent.putExtra("objTrip", trip);
//        setResult(RESULT_OK, createTripIntent);
//        finish();
        return true;
    }

    /**
     * This method should be used when a
     * user wants to cancel the creation of
     * a Trip.
     *
     * Note: You most likely want to call this
     * if your activity dies during the process
     * of a trip creation or if a cancel/back
     * button event occurs. Should return to
     * the previous activity without a result
     * using finish() and setResult().
     */

    public void cancelTrip() {

        // TODO - fill in here
    }

}



