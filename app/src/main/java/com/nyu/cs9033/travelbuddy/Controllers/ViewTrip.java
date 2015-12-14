package com.nyu.cs9033.travelbuddy.Controllers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.nyu.cs9033.travelbuddy.Models.Trips;
import com.nyu.cs9033.travelbuddy.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class ViewTrip extends Fragment implements View.OnClickListener, AbsListView.OnScrollListener {

    public static final String TAG = "View Trips Fragment";
    private ArrayList<Trips> TripList1 = new ArrayList<>();
    private SimpleAdapter TripListAdapter;
    private ArrayList<HashMap<String, String>> TripListView = new ArrayList<>();
    ListView list;

    public ViewTrip() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_trip, container, false);
//        ListView trips = null;
//        trips.findViewById(R.id.parse_viewTrip);

        TripListAdapter = new SimpleAdapter(getActivity(), TripListView,
                R.layout.triplist_main, new String[] { "L1", "L2", "L3" }, new int[] {
                R.id.TripNameInList, R.id.TripDetailInList, R.id.TripDetailInList2 });
        list = (ListView) view.findViewById(R.id.TripList);
//        list.setOnScrollListener(this);
        list.setAdapter(TripListAdapter);
        PopulateTrips(getContext());

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Trips getSelectedTrip = TripList1.get(position);
                String x1 = getSelectedTrip.getObjectId();
                String name = getSelectedTrip.getTrip_Name();
                Log.d("TripName", name+"");
                Log.d("TripList1 size  ", TripList1.size()+"");
                Intent viewTrip = new Intent(getActivity(), ViewTripActivity.class);
                viewTrip.putExtra("trip", getSelectedTrip);
                viewTrip.putExtra("objectID", x1);
                startActivity(viewTrip);
            }
        });
        list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        Log.i(TAG,
                "Setting List adapters for listView and button click events.");

        return view;
    }


    /**
     * Populate all trips from database on application load
     */
    private void PopulateTrips(Context context) {
        final ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Fetching Trips..");
        mProgressDialog.show();

        final ParseQuery<Trips> query= new ParseQuery<>("Trips");
        query.orderByAscending("TripDateTime");
        query.findInBackground(new FindCallback<Trips>() {
            @Override
            public void done(final List<Trips> listResult, ParseException e) {
                if (e == null) {
                    int len = listResult.size();

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = sp.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(listResult);
                    editor.putString("MyObject", json);
                    editor.apply();

                    for (int i = 0; i < len; i++) {
                        Trips p = listResult.get(i);
                        if (p.getString("TripDateTime") != null)
                        {
                            HashMap<String, String> item = new HashMap<>();
                            item.put("L1", p.getString("TripName"));
                            item.put(
                                    "L2",
                                    "On:" + p.getString("TripDateTime") + " at:"
                                            + p.getString("Location") + " with "
                                            + p.getInt("MemberCount") + " more friend");
                            item.put("L3", "Test");
                            String x = p.getObjectId();
                            TripList1.add(p);
                            TripListView.add(item);
                            TripListAdapter.notifyDataSetChanged();



                        }
                    }
//                    list.setAdapter(TripListAdapter);
                }
                else {
                    Log.d("ViewTrips", "Error: " + e.getMessage());
                }
            }
        });
        mProgressDialog.hide();
    }




    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }

    /**
     * Callback method to be invoked while the list view or grid view is being scrolled. If the
     * view is being scrolled, this method will be called before the next frame of the scroll is
     * rendered. In particular, it will be called before any calls to
     * {@link Adapter#getView(int, View, ViewGroup)}.
     *
     * @param view        The view whose scroll state is being reported
     * @param scrollState The current scroll state. One of
     *                    {@link #SCROLL_STATE_TOUCH_SCROLL} or {@link #SCROLL_STATE_IDLE}.
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.d("Scroll1", "clicked");
    }

    /**
     * Callback method to be invoked when the list or grid has been scrolled. This will be
     * called after the scroll has completed
     *
     * @param view             The view whose scroll state is being reported
     * @param firstVisibleItem the index of the first visible cell (ignore if
     *                         visibleItemCount == 0)
     * @param visibleItemCount the number of visible cells
     * @param totalItemCount   the number of items in the list adaptor
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.d("Scroll2", "clicked" + firstVisibleItem + " "+ visibleItemCount + " "+totalItemCount);
//        view.setSelection(firstVisibleItem++);
    }



}