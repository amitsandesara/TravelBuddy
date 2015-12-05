package com.nyu.cs9033.travelbuddy.Controllers;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.nyu.cs9033.travelbuddy.Adapters.GridViewAdapter;
import com.nyu.cs9033.travelbuddy.R;

import java.io.File;

public class TripImages extends AppCompatActivity {

    private static final String TAG = "Photos from Device";
    // Declare variables
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    GridView grid;
//    LazyAdapter adapter;
    File file;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        // Check for SD Card
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG)
                    .show();
        } else {
           fetchFile();
        }

        if (file.isDirectory()) {
            listFile = file.listFiles();
            // Create a String array for FilePathStrings
            FilePathStrings = new String[listFile.length];
            // Create a String array for FileNameStrings
            FileNameStrings = new String[listFile.length];
            Log.i(TAG, "Total images found:- " + listFile.length);
            for (int i = 0; i < listFile.length; i++) {
                // Get the path of the image file
                FilePathStrings[i] = listFile[i].getAbsolutePath();
                // Get the name image file
                FileNameStrings[i] = listFile[i].getName();
            }
        }

        // Locate the GridView in gridview_main.xml
        grid = (GridView) findViewById(R.id.gridview);
        // Pass String arrays to LazyAdapter Class
        GridViewAdapter adapter = new GridViewAdapter(this, FilePathStrings, FileNameStrings);
//        // Set the LazyAdapter to the GridView
//        grid.setAdapter(adapter);

        // Capture gridview item click
//        grid.setOnItemClickListener(new OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                Intent i = new Intent(MainActivity.this, ViewImage.class);
//                // Pass String arrays FilePathStrings
//                i.putExtra("filepath", FilePathStrings);
//                // Pass String arrays FileNameStrings
//                i.putExtra("filename", FileNameStrings);
//                // Pass click position
//                i.putExtra("position", position);
//                startActivity(i);
//            }
//
//        });
    }

    private void fetchFile() {
        final ProgressDialog dialog = new ProgressDialog(TripImages.this);
        dialog.setMessage("Fetching Images...");
        dialog.show();

        // Locate the image folder in your SD Card
        file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + File.separator + "SDImageTutorial");
        // Create a new folder if no folder named SDImageTutorial exist
        file.mkdirs();
        dialog.hide();
    }

}
