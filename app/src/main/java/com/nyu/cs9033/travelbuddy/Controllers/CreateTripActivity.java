package com.nyu.cs9033.travelbuddy.Controllers;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.nyu.cs9033.travelbuddy.R;

import java.util.Locale;

public class CreateTripActivity extends ActionBarActivity implements ActionBar.TabListener{

    private static final String TAG = "CreateTripActivity";
    SlidingTabLayout mSlidingTabLayout;
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private ActionBarDrawerToggle drawerListner;
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    Boolean create = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.ic_airplanemode_active_black_24dp);
        getSupportActionBar().setTitle("  Trips");
        CharSequence Titles[]={"View Trip","Create Trip", "Trip Pictures"};
        int Numboftabs = 3;
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);


        Intent intent = getIntent();
        create = intent.getBooleanExtra("CreateTrip", false);
        if (create){
            mViewPager.setCurrentItem(1);
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("Trip_Name", intent.getStringExtra("TripName"));
            editor.putString("Trip_Date", intent.getStringExtra("TripDate"));
            editor.putString("Trip_Time", intent.getStringExtra("TripTime"));
            editor.putString("Trip_Details", intent.getStringExtra("TripDetails"));
            editor.putString("Trip_Location", intent.getStringExtra("TripLocation"));
            editor.putString("Trip_Friends", intent.getStringExtra("Friends"));
            editor.putString("Trip_ID", intent.getStringExtra("TripID"));
            editor.putInt("MemberCount", intent.getIntExtra("Members", 0));
            editor.putBoolean("CreateTrip", true);
            editor.commit();

        }
//        trip_Location = (EditText) findViewById(R.id.tripLocation);
//        trip_Details = (EditText) findViewById(R.id.tripDetails);
//        _dateOfTrip = (EditText) findViewById(R.id.tripDate);
//
//        _friends = (EditText) findViewById(R.id.friends);
//        _creatorOfTrip = (EditText) findViewById(R.id.creatorName);
//        _dateOfTrip.setInputType(InputType.TYPE_NULL);
//        _dateOfTrip.requestFocus();
//        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
//        createTrip();
    }


    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
        CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
        int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created



        public SectionsPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb) {
            super(fm);

            this.Titles = mTitles;
            this.NumbOfTabs = mNumbOfTabsumb;

        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case 0:
                    fragment = new ViewTrip();
                    break;
                case 1:
                    fragment = new CreateTrip();
                    break;
                case 2:
//                    fragment = new EditTrip();
//                    break;
//                case 3:
                    fragment = new UploadTripImages();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return NumbOfTabs;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return Titles[0].toString().toUpperCase(l);
                case 1:
                    return Titles[1].toString().toUpperCase(l);
                case 2:
                    return Titles[2].toString().toUpperCase(l);
                case 3:
                    return Titles[3].toString().toUpperCase(l);
            }
            return null;
        }
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int n = item.getItemId();
        switch (n)
        {
            case R.id.btnGoToHome:
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
    /**
     * This method should be used to
     * instantiate a Trips model object.
     *
     * @return The Trips as represented
     * by the View.
     */


    /**
     * Called when a tab enters the selected state.
     *
     * @param tab The tab that was selected
     * @param ft  A {@link FragmentTransaction} for queuing fragment operations to execute
     *            during a tab switch. The previous tab's unselect and this tab's select will be
     *            executed in a single transaction. This FragmentTransaction does not support
     */
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

        mViewPager.setCurrentItem(tab.getPosition());
    }

    /**
     * Called when a tab exits the selected state.
     *
     * @param tab The tab that was unselected
     * @param ft  A {@link FragmentTransaction} for queuing fragment operations to execute
     *            during a tab switch. This tab's unselect and the newly selected tab's select
     *            will be executed in a single transaction. This FragmentTransaction does not
     */
    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    /**
     * Called when a tab that is already selected is chosen again by the user.
     * Some applications may use this action to return to the top level of a category.
     *
     * @param tab The tab that was reselected.
     * @param ft  A {@link FragmentTransaction} for queuing fragment operations to execute
     *            once this method returns. This FragmentTransaction does not support
     */
    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
