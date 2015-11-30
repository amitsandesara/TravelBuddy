package com.nyu.cs9033.travelbuddy.Controllers;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.nyu.cs9033.travelbuddy.Models.Trips;
import com.nyu.cs9033.travelbuddy.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CreateTrip extends Fragment implements View.OnClickListener {

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialog;
    EditText trip_Location;
    EditText trip_Details;
    EditText _dateOfTrip;
    EditText _friends;
    EditText _creatorOfTrip;
    Button createTrip;
    DateFormat df = new SimpleDateFormat(" MMM dd, yyyy, HH:mm");
    String date = df.format(Calendar.getInstance().getTime());

    public static final String TAG = "Create Trip Fragment";

    public CreateTrip() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_trip, container, false);
          trip_Location = (EditText) view.findViewById(R.id.tripLocation);
        trip_Details = (EditText) view.findViewById(R.id.tripDetails);
        _dateOfTrip = (EditText) view.findViewById(R.id.tripDate);
        createTrip = (Button) view.findViewById(R.id.submitCreateTrip);
        _friends = (EditText) view.findViewById(R.id.friends);
//        _creatorOfTrip = (EditText) view.findViewById(R.id.creatorName);

        _dateOfTrip.setInputType(InputType.TYPE_NULL);
        _dateOfTrip.requestFocus();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "Test_bar");
        testObject.saveInBackground();

        createTrip();

        return view;
    }


    public Trips createTrip() {

        setDateField();

        createTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripLocation = trip_Location.getText().toString();
                Log.i(TAG, "Value of location in create activity: " + tripLocation);
                String tripDetails = trip_Details.getText().toString();
                String friends = _friends.getText().toString();
                String dateOfTrip = _dateOfTrip.getText().toString();
//                String creatorOfTrip = _creatorOfTrip.getText().toString();

                if (trip_Location.getText().toString().length() == 0) {
                    final Toast toast = Toast.makeText(getContext(), "Please enter Trip Location", Toast.LENGTH_SHORT);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 500);
                } else if (_dateOfTrip.getText().toString().length() == 0) {
                    final Toast toast = Toast.makeText(getContext(), "Please enter date of trip", Toast.LENGTH_SHORT);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 500);
                } else if (trip_Details.getText().toString().length() == 0) {
                    final Toast toast = Toast.makeText(getContext(), "Please enter trip details", Toast.LENGTH_SHORT);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 500);
                } else {
                    Trips objCreateTrip = new Trips();
//                    if (creatorOfTrip.toString().length() == 0) {
//                        creatorOfTrip = "Amitkumar Sandesara";
//                        Log.i(TAG, creatorOfTrip + "MANUAL");
//                    }
                    if (friends.length() == 0) {
                        friends = "  ";
                    }

                    objCreateTrip.setTrip_Location(tripLocation);
                    objCreateTrip.setTrip_Details(tripDetails);
                    objCreateTrip.setTrip_Date(dateOfTrip);
                    objCreateTrip.setFriends(friends);
//                    objCreateTrip.setCreatedBy(creatorOfTrip);
                    saveTrip(objCreateTrip);
                }
            }


        });

        // TODO - fill in here

        return null;
    }

    private void setDateField() {
        _dateOfTrip.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog = new DatePickerDialog(getContext(), new android.app.DatePickerDialog.OnDateSetListener() {

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
            DatePickerDialog.show();
        }
    }

    /**
     * For HW2 you should treat this method as a
     * way of sending the Trips data back to the
     * main Activity.
     * <p/>
     * Note: If you call finish() here the Activity
     * will end and pass an Intent back to the
     * previous Activity using setResult().
     *
     * @return whether the Trips was successfully
     * saved.
     */
    public boolean saveTrip(Trips trip) {
        // TODO - fill in here
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Creating Trip...");
        dialog.show();


        String objectId;
        final ParseObject gameScore = new ParseObject("Trips");
        gameScore.put("CreatedBy", "Amit Sandesara");
        gameScore.put("DetailsOfTrip", trip.getTrip_Details());
        gameScore.put("Location", trip.getTrip_Location());
//        gameScore.put("createdAt", date);
        gameScore.put("FriendsAwatingAcceptance", trip.getFriends());
        gameScore.put("Trip_Date", trip.getTrip_Date());
        boolean networkState = Services.netConnect(getContext());
        if (networkState) {
            gameScore.saveInBackground(new SaveCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // Success!
                        String objectId = gameScore.getObjectId();
                        Log.i(TAG, "Object ID- " + objectId);
                        dialog.dismiss();
                        final Toast toast = Toast.makeText(getContext(), "Trips Created Successfully", Toast.LENGTH_SHORT);
                        toast.show();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 1000);

                        _dateOfTrip.setText("");
                        _friends.setText("");
                        trip_Details.setText("");
                        trip_Location.setText("");

                    }
                else

                {
                    // Failure!

                    dialog.dismiss();
                    final Toast toast = Toast.makeText(getContext(), "Check Network Status", Toast.LENGTH_SHORT);
                    Log.i(TAG, "Check Network Status");
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 1000);
                }
            }
        });
        }
        else
        {
            final Toast toast = Toast.makeText(getContext(), "No Internet Connectivity", Toast.LENGTH_SHORT);
            toast.show();
            Log.i(TAG, "No Internet Connectivity");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 1000);
        }
        return true;
    }




    public void cancelTrip() {

        // TODO - fill in here
    }
}
