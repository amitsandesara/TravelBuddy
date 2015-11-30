package com.nyu.cs9033.travelbuddy.Controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nyu.cs9033.travelbuddy.Models.Trips;
import com.nyu.cs9033.travelbuddy.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class ViewTrip extends Fragment implements View.OnClickListener {

    public static final String TAG = "View Trips Fragment";
    List<Trips> trips = new ArrayList<>();

    public ViewTrip() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_trip, container, false);
//        ListView trips = null;
//        trips.findViewById(R.id.parse_viewTrip);
        final ListView listView1 = (ListView) view.findViewById(R.id.listView1);

        ParseQuery<Trips> query= new ParseQuery<>("Trips");
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<Trips>() {
            @Override
            public void done(List<Trips> list, ParseException e) {
                if (e == null){
//                    for (Trips t: list){
//                        Trips trip = new Trips();
//                        trip.setCreatedBy(t.getCreatedBy());
//                        trip.setTrip_Location(t.getTrip_Location());
//                        trip.setTrip_Date(t.getTrip_Date());
//                        trips.add(trip);
//                        Log.i(TAG, "Trip Location: " + t.getTrip_Location());
//                        Log.i(TAG, "Trip Date: " +t.getTrip_Date());
//                        Log.i(TAG, "Trip Created By: " +t.getCreatedBy());
//                    }
//                    ArrayAdapter<Trips> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, trips);
//                    Log.i(TAG, "Found " + list.size() +" trips in Database");
//                    listView1.setAdapter(adapter);
                }
                else{
                    Log.d("TAG", "Error: " + e.getMessage());
                }
            }
        });


        return view;
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }


}