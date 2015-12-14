package com.nyu.cs9033.travelbuddy.Controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nyu.cs9033.travelbuddy.Models.Person;
import com.nyu.cs9033.travelbuddy.Models.Trips;
import com.nyu.cs9033.travelbuddy.R;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewTripActivity extends Activity {

	private static final String TAG = "ViewTripActivity";
    private static final String ACTIVE_TRIP = "ActiveTrip";
    private ArrayList<Person> PersonList = new ArrayList<>();
    String Friends = "";
    private String TripName = "";
    private String TripLocation = "";
    private String TripDateTime = "";
    int MemberCount = 0;
    String Time = "";
    String Date = "";
    String TripDetails = "";

    TextView name, tripLocation, tripDate, friends, details;
    Button editTrip;
    String ID;
    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trips);

        name = (TextView) findViewById(R.id.textView_trip_name);
        tripLocation = (TextView) findViewById(R.id.textView_trip_location);
        tripDate = (TextView) findViewById(R.id.textView_trip_date);
        details = (TextView) findViewById(R.id.textView_notes);
        friends = (TextView) findViewById(R.id.textView_friends);
        editTrip = (Button) findViewById(R.id.view_editTrip);

        Intent i = getIntent();
        ID = i.getStringExtra("objectID");

        Log.d("GETTING Trip", "from ViewTrip  Fragment:- " + ID);

        getTrip(ID);

        editTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String datetime = TripDateTime;
                Log.d("DateTime:- ", "" + datetime);

                String name = TripName;
                String location = TripLocation;
                int lengthOfDate = datetime.length();
                int posOfTime = datetime.lastIndexOf(",");
                Time = datetime.substring(posOfTime+1 , lengthOfDate);
                Log.d("Time:- ", Time+"");
                Date = datetime.substring(0, posOfTime);
                Log.d("Date:- ", Date + "");

                Intent intent = new Intent(getApplicationContext(), CreateTripActivity.class);
                intent.putExtra("TripName", TripName);
                intent.putExtra("TripDate", Date);
                intent.putExtra("TripTime", Time);
                intent.putExtra("TripDetails", TripDetails);
                intent.putExtra("TripLocation", TripLocation);
                intent.putExtra("Friends", Friends);
                intent.putExtra("TripID", ID);
                intent.putExtra("Members", MemberCount);
                intent.putExtra("CreateTrip", true);
                startActivity(intent);
            }
        });
//        viewTrip(p);                   //initialize respective view component of trip object.
	}

	/**
	 * Create a Trip object via the recent trip that
	 * was passed to TripViewer via an Intent.
	 *
	 * @param i The Intent that contains
	 * the most recent trip data.
	 *
	 * @return The Trip that was most recently
	 * passed to TripViewer, or null if there
	 * is none.
	 */

	public void getTrip(final String ID) {

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.show();

        ParseQuery<Trips> query = ParseQuery.getQuery("Trips");
        query.getInBackground(ID, new GetCallback<Trips>() {
            public void done(Trips trip, ParseException e) {
                if (e == null) {
                    Log.d("GetTrip ", "Entered");
                    TripName = trip.getString("TripName");
                    name.setText(TripName);
                    TripLocation = trip.getString("Location");
                    tripLocation.setText(TripLocation);
                    TripDateTime = trip.getString("TripDateTime");
                    tripDate.setText(TripDateTime);
                    TripDetails = trip.getString("DetailsOfTrip");
                    details.setText(TripDetails);

//                    friends.setText(getFriendsString(trip.get));
                } else {
                    final Toast toast = Toast.makeText(getApplicationContext(), "Error: " + e, Toast.LENGTH_SHORT);
                    toast.show();
                    // something went wrong
                }
            }
        });

        ParseQuery<Person> getPerson = ParseQuery.getQuery("Person");
        getPerson.orderByAscending("Trip_ID");
        getPerson.findInBackground(new FindCallback<Person>() {
            @Override
            public void done(List<Person> listResult, ParseException e) {
                if (e == null) {
                    Log.d("Person listResult", "size: " + listResult.size() + "");
                    for (int i = 0; i < listResult.size(); i++) {
                        Person p = listResult.get(i);
                        if (Objects.equals(p.getString("Trip_ID"), ID)) {
                            if (p.getString("PersonName").length() != 0)
                                PersonList.add(p);
                                MemberCount++;
                        }
                    }
                    int i;
                    if (PersonList.size() != 0) {
                        for (i = 0; i < PersonList.size()-1; i++) {
                            String personName = PersonList.get(i).getString("PersonName");
                            if (personName.length() != 0)
                                Friends += personName + ", ";
                        }
                        if (PersonList.get(i).getString("PersonName").length() != 0)
                            Friends += PersonList.get(i).getString("PersonName") + ", ";
                        friends.setText(Friends);
                    }
                } else {
                    final Toast toast = Toast.makeText(getApplicationContext(), "Error:- " + e, Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        mProgressDialog.hide();

        }
}

