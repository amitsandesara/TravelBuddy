package com.nyu.cs9033.travelbuddy.Controllers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nyu.cs9033.travelbuddy.R;

import java.io.InputStream;

public class ProfileActivity extends HomeActivity {

    Bitmap photo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView name = (TextView) findViewById(R.id.profileName);
        TextView email = (TextView) findViewById(R.id.userEmail);
        ImageView dp = (ImageView) findViewById(R.id.profilePhoto);
        Button saveProfile = (Button) findViewById(R.id.saveProfile);
        Button cancel = (Button) findViewById(R.id.editCancel);
        Button edit = (Button) findViewById(R.id.btnEditProfile);
        final EditText editname = (EditText) findViewById(R.id.editName);
        final RelativeLayout viewProfile = (RelativeLayout) findViewById(R.id.viewProfile);
        final RelativeLayout editProfile = (RelativeLayout) findViewById(R.id.editProfile);



        SharedPreferences googlePlusProfile = getSharedPreferences("Google+", MODE_PRIVATE);

        String encodedImage = googlePlusProfile.getString("GProfilePicURL", "");
        new DownloadImageTask((ImageView) findViewById(R.id.profilePhoto)).execute(encodedImage);
        String userName = googlePlusProfile.getString("GDisplayName", "");
        String userEmail = googlePlusProfile.getString("GEmail", "");

        name.setText(userName);
        email.setText(userEmail);
       // getCroppedBitmap(photo, 75);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProfile.setVisibility(View.GONE);
                editProfile.setVisibility(View.VISIBLE);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewProfile.setVisibility(View.VISIBLE);
                editProfile.setVisibility(View.GONE);
            }
        });

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName= editname.getText().toString();

                SharedPreferences googlePlusProfile = getSharedPreferences("Google+", MODE_PRIVATE);
                SharedPreferences.Editor spEditor = googlePlusProfile.edit();
                spEditor.putString("GDisplayName", uName);

                spEditor.commit();
                final Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            photo = result;
        }
    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        if(bmp.getWidth() != radius || bmp.getHeight() != radius)
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
        else
            sbmp = bmp;
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
                sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(sbmp.getWidth() / 2+0.7f, sbmp.getHeight() / 2+0.7f,
                sbmp.getWidth() / 2+0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);


        return output;
    }

}
