package com.nyu.cs9033.travelbuddy.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nyu.cs9033.travelbuddy.R;

public class HomeActivity extends AppCompatActivity implements OnItemClickListener {

    private static final String TAG_Menu = "ACTION MENU ";
    DrawerLayout drawerLayout;
    ListView listView;
    private String[] appDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = (DrawerLayout) findViewById(R.id.defaultDrawer);
        listView = (ListView) findViewById(R.id.drawerList);
       // listView.setOnItemClickListener(new DrawerItemClickListener());
        appDrawer = getResources().getStringArray(R.array.navigationDrawer);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, appDrawer));
        listView.setOnItemClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
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

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position+1 == appDrawer.length) //ignoring the Logout button
        {}
        else
            selectTitle(appDrawer[position]);
    }

    public void selectTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
