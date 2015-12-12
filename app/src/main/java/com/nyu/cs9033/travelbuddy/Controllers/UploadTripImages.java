package com.nyu.cs9033.travelbuddy.Controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.nyu.cs9033.travelbuddy.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.*;

public class UploadTripImages extends Fragment  {

    public static final String TAG = "Upload Image Fragment";
    Button uploadImage;
    private static final int SELECT_PHOTO = 100;
    private static final int SELECT_PICTURE = 1;
    private static final int RESULT_OK = 50;

    public UploadTripImages() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_trip_images, container, false);

        uploadImage = (Button) view.findViewById(R.id.uploadbtn);

        uploadImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);


            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PICTURE && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                ImageView imageView = (ImageView) getActivity().findViewById(R.id.selectImage);
                imageView.setImageBitmap(bitmap);
                uploadToParse(bitmap,uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    //    @Override
    public void uploadToParse(Bitmap bitmap, Uri uri) {
        Log.d(TAG, "uploadToParse: image");


        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);

        byte[] image = stream.toByteArray();

        ParseFile file = new ParseFile("myimage.jpeg", image);

        final ParseObject imgupload = new ParseObject("Images");
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Uploading Image...");
        dialog.show();

        imgupload.put("Images", file);
        imgupload.saveInBackground();
        file.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Success!
                    String objectId = imgupload.getObjectId();
                    Log.i(TAG, "Object ID- " + objectId);
                    dialog.dismiss();
                    final Toast toast = Toast.makeText(getContext(), "Image Uploaded Successfully", Toast.LENGTH_SHORT);
                    toast.show();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toast.cancel();
                        }
                    }, 1000);

                }
            }
        });

    }

}