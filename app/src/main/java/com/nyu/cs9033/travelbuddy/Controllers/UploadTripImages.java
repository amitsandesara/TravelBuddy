package com.nyu.cs9033.travelbuddy.Controllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class UploadTripImages extends Fragment implements View.OnClickListener {

    public static final String TAG = "Upload Image Fragment";
    Button uploadImage;
    private static final int SELECT_PHOTO = 100;
    private static final int SELECT_PICTURE = 1;
    private static final int RESULT_OK = 50;
    String selectedImagePath;
    //ADDED
    String filemanagerstring;

    public UploadTripImages() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_trip_images, container, false);
        uploadImage = (Button) view.findViewById(R.id.uploadbtn);
        ImageView imageView = (ImageView) view.findViewById(R.id.selectImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, SELECT_PHOTO);
            }
        });
        uploadImage.setOnClickListener(this);
//                uploadImage.setOnClickListener(new View.OnClickListener() {
//
//                    public void onClick(View arg0) {
//
//                        Intent intent = new Intent();
//                        intent.setType("image/*");
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
//                        startActivityForResult(Intent.createChooser(intent,
//                                "Select Picture"), SELECT_PICTURE);
//                    }
//                });

        return view;
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == SELECT_PICTURE) {
//                Uri selectedImageUri = data.getData();
//                selectedImagePath = getPath(selectedImageUri);
//            }
//        }
//    }
//
//    public String getPath(Uri uri) {
//
//        if( uri == null ) {
//            return null;
//        }
    /**
     * Called when a view has been clicked.
     *
     * @param arg0 The view that was clicked.
     */
    @Override
    public void onClick(View arg0) {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);



        // Create the ParseFile
        byte[] image = null;
        ParseFile file = new ParseFile("bg.png", image);

        final ParseObject imgupload = new ParseObject("Images");
        final ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setMessage("Uploading Image...");
        dialog.show();


        imgupload.put("ImageName", "Test");
        imgupload.put("Images", file);
        imgupload.saveInBackground();
        file.saveInBackground();
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


//    @Override
//    public String  onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//
//        switch(requestCode) {
//            case SELECT_PHOTO:
//                if(resultCode == RESULT_OK){
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    InputStream imageStream = getContentResolver().openInputStream(selectedImage);
//                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
//                    return yourSelectedImage.toString();
//                }
//        }
//    }


//    private Bitmap decodeUri(Uri selectedImage) throws FileNotFoundException {
//
//        // Decode image size
//        BitmapFactory.Options o = new BitmapFactory.Options();
//        o.inJustDecodeBounds = true;
//        BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o);
//
//        // The new size we want to scale to
//        final int REQUIRED_SIZE = 140;
//
//        // Find the correct scale value. It should be the power of 2.
//        int width_tmp = o.outWidth, height_tmp = o.outHeight;
//        int scale = 1;
//        while (true) {
//            if (width_tmp / 2 < REQUIRED_SIZE
//                    || height_tmp / 2 < REQUIRED_SIZE) {
//                break;
//            }
//            width_tmp /= 2;
//            height_tmp /= 2;
//            scale *= 2;
//        }
//
//        // Decode with inSampleSize
//        BitmapFactory.Options o2 = new BitmapFactory.Options();
//        o2.inSampleSize = scale;
//        return BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, o2);
//
//    }


}

