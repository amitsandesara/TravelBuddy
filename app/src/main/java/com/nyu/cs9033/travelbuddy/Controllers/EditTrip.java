package com.nyu.cs9033.travelbuddy.Controllers;

import android.content.Context;
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
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nyu.cs9033.travelbuddy.Models.Trips;
import com.nyu.cs9033.travelbuddy.R;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EditTrip extends Fragment implements View.OnClickListener, AbsListView.OnScrollListener {

    public static final String TAG = "Edit Trips Fragment";
    private static Integer TripCount = 0;
    private ArrayList<Trips> TripList = new ArrayList<Trips>();
    private SimpleAdapter TripListAdapter;
    private ArrayList<HashMap<String, String>> TripListView = new ArrayList<HashMap<String, String>>();
    ListView list;

    public EditTrip() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_trip, container, false);

        TripListAdapter = new SimpleAdapter(getActivity(), TripListView,
                R.layout.triplist_main, new String[] { "L1", "L2" }, new int[] {
                R.id.TripNameInList, R.id.TripDetailInList });
        list = (ListView) view.findViewById(R.id.TripList);
//        list.setOnScrollListener(this);
        list.setAdapter(TripListAdapter);
        PopulateTrips(getContext());

        list.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        return view;
    }


    /**
     * Populate all trips from database on application load
     */
    private void PopulateTrips(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        Gson gson = new Gson();
        String json = sp.getString("MyObject", "");
//        Trips listResult = gson.fromJson(json, Trips.class);

        Type type = new TypeToken<List<Trips>>() {
        }.getType();
        List<Trips> listResult = gson.fromJson(json, type);
        for (int i = 0; i < listResult.size(); i++) {
            Trips p = listResult.get(i);
            if (p.getString("TripDateTime") != null) {
                HashMap<String, String> item = new HashMap<>();
                Log.d("EditTrip", p.getString("TripName"));
                item.put("L1", p.getString("TripName"));
                item.put(
                        "L2",
                        "On:" + p.getString("TripDateTime") + " at:"
                                + p.getString("Location") + " with "
                                + p.getInt("MemberCount") + " more friend");
                TripListView.add(item);
                TripListAdapter.notifyDataSetChanged();

            }
        }
        Log.d(TAG, String.valueOf(listResult.size()));
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

    }
}