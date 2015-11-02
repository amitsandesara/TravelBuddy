package com.nyu.cs9033.travelbuddy.Controllers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nyu.cs9033.travelbuddy.R;

public class NavigationDrawer extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG_Menu = "ACTION MENU ";
    private DrawerLayout drawerLayout;
    private String[] appDrawer;
    private ActionBarDrawerToggle drawerListner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        drawerLayout = (DrawerLayout) findViewById(R.id.defaultDrawer);
        drawerListner = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerListner);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_home_white);

        ListView listView = (ListView) findViewById(R.id.drawerList);
        // listView.setOnItemClickListener(new DrawerItemClickListener());
        appDrawer = getResources().getStringArray(R.array.navigationDrawer);
//        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, appDrawer));
        MyAdapterForDrawer drawerAdapter = new MyAdapterForDrawer(this);
        listView.setAdapter(drawerAdapter);
        listView.setOnItemClickListener(this);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListner.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListner.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerListner.onOptionsItemSelected(item)){
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.shareApplication) {
            // DO SOMETHING
            Log.i(TAG_Menu, "Share app clicked");
            final Toast toast = Toast.makeText(getApplicationContext(),"Yet to create Help page..!!", Toast.LENGTH_SHORT);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 1000);      //Remove this toast once share application is functional
            return true;
        }
        else if (id == R.id.action_settings){
            final Intent openSettings = new Intent(getApplicationContext(), AppSettingsActivity.class);
            startActivity(openSettings);
            return true;
        }
        else if (id == R.id.Help){
            final Toast toast = Toast.makeText(getApplicationContext(),"Yet to create Help page..!!", Toast.LENGTH_SHORT);
            toast.show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toast.cancel();
                }
            }, 1000);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if (position == appDrawer.length)
        {}
        else {
            String iconForSelectedDrawerItem = appDrawer[position];
            switch (iconForSelectedDrawerItem)
            {
                case "Trips":
                {
                    getSupportActionBar().setIcon(R.drawable.ic_home_black);
                    break;
                }

                case "Profile":
                {
                    final Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                    break;
                }

                case "Logout":
                {
                    getSupportActionBar().setIcon(R.drawable.ic_power_settings_new_black_24dp);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);

                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure you want to Logout?");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                            dialog.dismiss();
                        }


                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing
                            getSupportActionBar().setIcon(R.drawable.ic_home_black);

                            //Change this to go to homepage instead of recreating the activity
//                            final Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
//                            startActivity(intent);
//                            finish();
                            drawerLayout.openDrawer(Gravity.LEFT);
                            setTitle("Home");
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                    break;
                }
            }
            //getSupportActionBar().setIcon(R.drawable.ic_home_white);
            drawerLayout.setSelected(true);
            drawerLayout.closeDrawers();
            selectTitle(appDrawer[position]);
        }
    }

    public void selectTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
//
//class MyAdapterForDrawer extends BaseAdapter {
//
//    String[] drawerItems;
//    private Context context;
//    int[] icons = {R.drawable.ic_home_black,R.drawable.ic_airplanemode_active_black_24dp, R.drawable.ic_settings_black_24dp, R.drawable.ic_favorite_black_24dp, R.drawable.ic_list_black_24dp, R.drawable.ic_favorite_black_24dp, R.drawable.ic_power_settings_new_black_24dp};
//    public MyAdapterForDrawer(Context context){
//        this.context = context;
//        drawerItems = context.getResources().getStringArray(R.array.navigationDrawer);
//    }
//    /**
//     * How many items are in the data set represented by this Adapter.
//     *
//     * @return Count of items.
//     */
//    @Override
//    public int getCount() {
//        return drawerItems.length;
//    }
//
//    /**
//     * Get the data item associated with the specified position in the data set.
//     *
//     * @param position Position of the item whose data we want within the adapter's
//     *                 data set.
//     * @return The data at the specified position.
//     */
//    @Override
//    public Object getItem(int position) {
//        return drawerItems[position];
//    }
//
//    /**
//     * Get the row id associated with the specified position in the list.
//     *
//     * @param position The position of the item within the adapter's data set whose row id we want.
//     * @return The id of the item at the specified position.
//     */
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    /**
//     * Get a View that displays the data at the specified position in the data set. You can either
//     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
//     * parent View (GridView, ListView...) will apply default layout parameters unless you use
//     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
//     * to specify a root view and to prevent attachment to the root.
//     *
//     * @param position    The position of the item within the adapter's data set of the item whose view
//     *                    we want.
//     * @param convertView The old view to reuse, if possible. Note: You should check that this view
//     *                    is non-null and of an appropriate type before using. If it is not possible to convert
//     *                    this view to display the correct data, this method can create a new view.
//     *                    Heterogeneous lists can specify their number of view types, so that this View is
//     *                    always of the right type (see {@link #getViewTypeCount()} and
//     *                    {@link #getItemViewType(int)}).
//     * @param parent      The parent that this view will eventually be attached to
//     * @return A View corresponding to the data at the specified position.
//     */
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View row = null;
//        if (convertView == null)
//        {
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            row = layoutInflater.inflate(R.layout.drawer_custom_rows, parent, false);
//        }
//        else
//        {
//            row = convertView;
//        }
//        TextView titleTextView = (TextView) row.findViewById(R.id.drawerRow);
//        ImageView titleImageView = (ImageView) row.findViewById(R.id.drawerImageIcon);
//        titleTextView.setText(drawerItems[position]);
//        titleImageView.setImageResource(icons[position]);
//        return row;
//    }
//}
