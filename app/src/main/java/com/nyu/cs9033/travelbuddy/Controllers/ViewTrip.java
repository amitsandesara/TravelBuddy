package com.nyu.cs9033.travelbuddy.Controllers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.nyu.cs9033.travelbuddy.Models.Trips;
import com.nyu.cs9033.travelbuddy.R;

import java.util.ArrayList;
import java.util.HashMap;


public class ViewTrip extends Fragment implements View.OnClickListener {

    public static final String TAG = "View Trips Fragment";
    private static Integer TripCount = 0;
    private ArrayList<Trips> TripList = new ArrayList<Trips>();
    private SimpleAdapter TripListAdapter;
    private ArrayList<HashMap<String, String>> TripListView = new ArrayList<HashMap<String, String>>();


    public ViewTrip() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_trip, container, false);
//        ListView trips = null;
//        trips.findViewById(R.id.parse_viewTrip);
        TripCount = 0;
        TripListAdapter = new SimpleAdapter(getContext(), TripListView,
                R.layout.triplist_main, new String[] { "L1", "L2" }, new int[] {
                R.id.TripNameInList, R.id.TripDetailInList });
        ListView list = (ListView) view.findViewById(R.id.TripList);
        list.setAdapter(TripListAdapter);
//        PopulateTrips(getContext());
        Log.i(TAG,
                "Setting List adapters for listview and button click events.");

//        final ParseQuery<Trips> query= new ParseQuery<>("Trips");
//        query.orderByAscending("createdAt");
//        query.findInBackground(new FindCallback<Trips>() {
//            @Override
//            public void done(List<Trips> list, ParseException e) {
//                if (e == null) {
//                    for (Trips t : list) {
//                        Trips trip = new Trips();
//                        trip.setCreatedBy(t.getCreatedBy());
//                        trip.setTrip_Location(t.getTrip_Location());
//                        trip.setTrip_Date(t.getTrip_Date());
//
//                        try {
//                            Log.i(TAG, "Trip Location: " + query.get("Trip_Location"));
//                        } catch (ParseException e1) {
//                            e1.printStackTrace();
//                        }
//                        Log.i(TAG, "Trip Date: " + t.getTrip_Date());
//                        Log.i(TAG, "Trip Created By: " + t.getCreatedBy());
//                    }
//                    ArrayAdapter<Trips> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, trips);
//                    Log.i(TAG, "Found " + list.size() + " trips in Database");
//                    list.setAdapter(adapter);
//                } else {
//                    Log.d("TAG", "Error: " + e.getMessage());
//                }
//            }
//        });


        return view;
    }


    /**
     * Populate all trips from database on application load
     */
//    private void PopulateTrips(Context context) {
//        final ParseQuery<Trips> query= new ParseQuery<>("Trips");
//        query.orderByAscending("createdAt");
//        query.findInBackground(new FindCallback<Trips>() {
//            @Override
//            public void done(List<Trips> list, ParseException e) {
//                if (e == null) {
//                    ArrayList<Trips> T = new ArrayList<Trips>();
//                    Cursor SelectTrip;
//                    if (!TripList.isEmpty()) {
//                        for (Trip T1 : TripList) {
//                            HashMap<String, String> item = new HashMap<String, String>();
//                            item.put("L1", T1.getName());
//                            item.put(
//                                    "L2",
//                                    "On:" + T1.getTripStartDate() + " At:"
//                                            + T1.getLocationName() + " With "
//                                            + T1.getMemberCount() + " more friend");
//                            TripListView.add(item);
//                            TripListAdapter.notifyDataSetChanged();
//                            TripCount++;
//                            TripCount.toString();
//                        }
//                        Log.i(TAG, "Populated Trip list from database.");
//                    }
//                }
//            }
//    }



    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }


}