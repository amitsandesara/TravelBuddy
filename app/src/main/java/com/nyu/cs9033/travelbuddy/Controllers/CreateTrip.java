package com.nyu.cs9033.travelbuddy.Controllers;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nyu.cs9033.travelbuddy.Models.Person;
import com.nyu.cs9033.travelbuddy.Models.Trips;
import com.nyu.cs9033.travelbuddy.R;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CreateTrip extends Fragment implements View.OnClickListener {

    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialog;
    AutoCompleteTextView trip_Location;
    EditText trip_Details;
    EditText _dateOfTrip;
    EditText _friends;
    EditText _creatorOfTrip;
    EditText trip_Time;
    EditText trip_Name;
    Button addfriends;
    TextView invitedFriends;
    public String formattedDate;
    public String formattedTime;
    public String formattedDateTime;

    Button createTrip;
    DateFormat df = new SimpleDateFormat(" MMM dd, yyyy, HH:mm");
    String date = df.format(Calendar.getInstance().getTime());
    private ArrayList<Person> Per = new ArrayList<Person>();
    static final int REQUEST_PERSON = 10;


    public static final String TAG = "Create Trip Fragment";

    public CreateTrip() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_trip, container, false);
        trip_Location = (AutoCompleteTextView) view.findViewById(R.id.tripLocation);
        trip_Location.setAdapter(new PlacesAutoCompleteAdapter(getActivity(), R.layout.list_item));

        trip_Details = (EditText) view.findViewById(R.id.tripDetails);
        _dateOfTrip = (EditText) view.findViewById(R.id.tripDate);
        createTrip = (Button) view.findViewById(R.id.submitCreateTrip);
//        _friends = (EditText) view.findViewById(R.id.friends);
        trip_Name = (EditText) view.findViewById(R.id.tripName);
        trip_Time = (EditText) view.findViewById(R.id.tripStartTime);
        invitedFriends = (TextView) view.findViewById(R.id.Friendsnumber);
        addfriends = (Button) view.findViewById(R.id.addfriends);
        addfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // startCreateFriendActivity();
                Intent PickFriends = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(PickFriends, REQUEST_PERSON);
            }
        });
        _dateOfTrip.setInputType(InputType.TYPE_NULL);
        _dateOfTrip.requestFocus();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//        ParseObject testObject = new ParseObject("TestObject");
//        testObject.put("foo", "Test_bar");
//        testObject.saveInBackground();

        createTrip();

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PERSON && resultCode == Activity.RESULT_OK) {
            Uri ContactURI = data.getData();
            String[] queryFields = new String[]{
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Email.ADDRESS};    //ContactsContract.Contacts.DISPLAY_NAME

            Cursor c = getActivity().getContentResolver().query(ContactURI, queryFields, null, null, null);
            assert c != null;
            c.moveToFirst();
            String name = c.getString(0);
            String Phone = c.getString(1);
            String Email = c.getString(2);
            Log.i(TAG ," Friend added:- " + name +" " + Phone + " " + Email);
            //Integer ContactID = 0;

            Person P = new Person();
            P.setName(name);
            P.setPhone(Phone);
            P.setEmail(Email);
            Per.add(P);

            String FriendsName = invitedFriends.getText() + ", "
                    + name.split(" ")[0];
            if (invitedFriends.getText().length() == 17) {
                FriendsName = FriendsName.replaceFirst(",", "");
            }
            invitedFriends.setText(FriendsName);
            Log.i(TAG, "Populating Person data to Trip model from contacts application");
        }
    }

    public Trips createTrip() {

        setDateField();

        trip_Time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        trip_Time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
//                formattedTime = hour + ":" + minute;
            }
        });

        createTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tripLocation = trip_Location.getText().toString();
                Log.i(TAG, "Value of location in create activity: " + tripLocation);
                String tripDetails = trip_Details.getText().toString();
//                String friends = _friends.getText().toString();
                String dateOfTrip = _dateOfTrip.getText().toString();
                Log.i(TAG,"Date of Trip is:- " + dateOfTrip );

                String tripName = trip_Name.getText().toString();
                String tripTime = trip_Time.getText().toString();
                Log.i(TAG,"Time of Trip is:- " + tripTime );
//                String creatorOfTrip = _creatorOfTrip.getText().toString();
                try
                {
                    String currentDate = dateOfTrip +" "+tripTime;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    Date tempDate = simpleDateFormat.parse(currentDate);
                    SimpleDateFormat outputDateFormat2 = new SimpleDateFormat("MMM dd, yyyy, HH:mm");
                    formattedDateTime = outputDateFormat2.format(tempDate);

                }
                catch (java.text.ParseException e) {
                    System.out.println("Parse Exception" + e);
                    e.printStackTrace();
                }


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
                    final Toast toast = Toast.makeText(getContext(), "Please enter Date of Trip", Toast.LENGTH_SHORT);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 500);
                } else if (trip_Details.getText().toString().length() == 0) {
                    final Toast toast = Toast.makeText(getContext(), "Please enter Trip Details", Toast.LENGTH_SHORT);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 500);
                } else if (trip_Time.getText().toString().length() == 0) {
                    final Toast toast = Toast.makeText(getContext(), "Please enter Trip Time", Toast.LENGTH_SHORT);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 500);
                } else if (trip_Name.getText().toString().length() == 0) {
                    final Toast toast = Toast.makeText(getContext(), "Please enter Trip Name", Toast.LENGTH_SHORT);
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

                    Log.i(TAG, "Date:- " + date);
                    objCreateTrip.setTrip_Location(tripLocation);
                    objCreateTrip.setTrip_Details(tripDetails);
//                    objCreateTrip.setTrip_Date(formattedDate);
                    objCreateTrip.setFriends(Per.toArray(new Person[Per.toArray().length]));
                    objCreateTrip.setTrip_Name(tripName);
                    objCreateTrip.setTrip_Time(formattedDateTime);
                    objCreateTrip.setMemberCount(Per.toArray().length);
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
     * @return whether the Trips was successfully
     * saved.
     */
    public boolean saveTrip(Trips trip) {
        // TODO - fill in here
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Creating Trip...");
        dialog.show();
        final ProgressBar mSpinner = new ProgressBar(getContext());


        final Trips gameScore = new Trips();
        String objectId;
//        final ParseObject gameScore = new ParseObject("Trips");
        gameScore.put("CreatedBy", "Amit Sandesara");
        gameScore.put("DetailsOfTrip", trip.getTrip_Details());
        gameScore.put("Location", trip.getTrip_Location());
        gameScore.put("TripName", trip.getTrip_Name());
        gameScore.put("TripDateTime", trip.getTrip_Time());
//        gameScore.put("createdAt", date);
//        gameScore.put("FriendsAwatingAcceptance", trip.getFriends());
//        gameScore.put("TripDate", trip.getTrip_Date());
        boolean networkState = Services.netConnect(getContext());
        Log.i("Network Status", String.valueOf(networkState));
        networkState = true;
        if (networkState) {
            gameScore.saveInBackground(new SaveCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        // Success!
                        String objectId = gameScore.getObjectId();
                        Log.i(TAG, "Object ID- " + objectId);
                        int i = 0;
                        for (Person p : Per){
                            if (i < Per.size()){
                                ParseObject person = new ParseObject("Person");
                                person.put("Trip_ID", objectId);
                                person.put("PersonName", p.getName());
                                person.put("PersonPhone", p.getPhone());
                                person.put("PersonEmail", p.getEmail());
                                person.saveInBackground();
                                Log.i(TAG, " Person is:- "+ p.getName()+ p.getPhone()+p.getEmail());
                            }
                            i++;
                        }

                        dialog.dismiss();
                        final Toast toast = Toast.makeText(getContext(), "Trips Created Successfully", Toast.LENGTH_SHORT);
                        toast.show();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                                Intent i = new Intent(getContext(), CreateTripActivity.class);
                                startActivity(i);
                            }
                        }, 1000);

                    }
                    else
                    {
                        // Failure!

                        dialog.dismiss();
                        final Toast toast = Toast.makeText(getContext(), "Erroe occurred", Toast.LENGTH_SHORT);
                        Log.i(TAG, "Check Network Status");
                        Log.i("Parse Exception:- ", String.valueOf(e));
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

    }
}

class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {

    ArrayList<String> resultList;

    Context mContext;
    int mResource;

    PlaceAPI mPlaceAPI = new PlaceAPI();

    public PlacesAutoCompleteAdapter(Context context, int resource) {
        super(context, resource);

        mContext = context;
        mResource = resource;
    }

    @Override
    public int getCount() {
        // Last item will be the footer
        return resultList.size();
    }

    @Override
    public String getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    resultList = mPlaceAPI.autocomplete(constraint.toString());

                    filterResults.values = resultList;
                    filterResults.count = resultList.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
        };

        return filter;
    }
}