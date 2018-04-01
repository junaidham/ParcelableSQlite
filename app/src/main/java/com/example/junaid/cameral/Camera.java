package com.example.junaid.cameral;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.example.junaid.cameral.db.DatabaseHandler;
import com.example.junaid.cameral.model.ContactModel;
import com.example.junaid.cameral.util.ProviderUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camera extends AppCompatActivity {

    Button btnCamera,btnGallery, btnSubmit;
    private EditText editName;
    ImageView image_preview;

    Uri photoURI = null;
    private File photoFile;
    private String selectedImagePath = "";
    final private int PICK_IMAGE = 1;
    final private int CAPTURE_IMAGE = 2;
    final private int REQUEST_TAKE_PHOTO = 2;

    private static final String TAG = MainActivity.class.getSimpleName();
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        /**
         * create DatabaseHandler object
         */
        databaseHandler = new DatabaseHandler(this);

        //find view Id
        initView();

        // onClick
        onClickListener();


    }


    private void onClickListener() {

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera();
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallery();

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySumbit();

            }
        });

    }

    String name;
    private void mySumbit() {

        // convert bitmap to byte
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        decodeFile(selectedImagePath).compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte imageInByte[] = stream.toByteArray();
        Bitmap bmpImage = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);

        Log.e("output before", imageInByte.toString());


//        Bitmap bmpImage = ImageUtil.decodeBase64(selectedImagePath);


        name = editName.getText().toString();

        ContactModel contactPhoto = new ContactModel();

        // set values at obj
        contactPhoto.setName(name);
        contactPhoto.setPhoto(bmpImage);
        databaseHandler.addContact(contactPhoto);

        Intent  intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();


    }

    private void gallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
    }


    private void camera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (android.os.Build.VERSION.SDK_INT > 23){
            if (intent.resolveActivity(getPackageManager()) != null) {
                photoURI = setImageUri();
//                photoURI = FileProvider.getUriForFile(MainActivity.this, getString(R.string.file_provider_authority), photoFile);
                photoURI = ProviderUtil.getOutputMediaFileUri(getBaseContext());

                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, CAPTURE_IMAGE);

            }
        }else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
            startActivityForResult(intent, CAPTURE_IMAGE);
        }
    }


    private void initView() {
        btnCamera = findViewById(R.id.btnCamera);
        btnGallery = findViewById(R.id.btnGallery);
        btnSubmit = findViewById(R.id.btnSubmit);
        image_preview = findViewById(R.id.image_preview);
        editName = findViewById(R.id.editName);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE) {
                selectedImagePath = getAbsolutePath(data.getData());
                image_preview.setImageBitmap(decodeFile(selectedImagePath));
            } else if (requestCode == CAPTURE_IMAGE) {
                selectedImagePath = getImagePath();
                image_preview.setImageBitmap(decodeFile(selectedImagePath));
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }


    public Bitmap decodeFile(String path) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, o);
            // The new size we want to scale to
            final int REQUIRED_SIZE = 100;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeFile(path, o2);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;

    }

    public String getAbsolutePath(Uri uri) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }


    public Uri setImageUri() {
        // Store image in dcim
        photoFile = new File(Environment.getExternalStorageDirectory()
                + "/DCIM/Camera/", "IMG_" +currentDateFormat() + ".png");
        Uri imgUri = Uri.fromFile(photoFile);
        this.selectedImagePath = photoFile.getAbsolutePath();
        return imgUri;
    }

    public String getImagePath() {
        return selectedImagePath;
    }


    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }





}
