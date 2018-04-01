package com.example.junaid.cameral;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.junaid.cameral.adapter.ContactAdapter;
import com.example.junaid.cameral.db.DatabaseHandler;
import com.example.junaid.cameral.model.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class ListDisplay extends AppCompatActivity {
    ListView listView;
    ContactAdapter imageAdapter;
    ArrayList<ContactModel> dataList = new ArrayList<ContactModel>();
    List<ContactModel> ModedataList = new ArrayList<ContactModel>();

    DatabaseHandler databaseHandler;
    ContactModel ptModel;

    byte[] imageName;
    String textNameTitle;
    String imageId;
    Bitmap theImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_display);

        // find id
        listView = (ListView) findViewById(R.id.list);

        // create DatabaseHandler object
        databaseHandler = new DatabaseHandler(this);

        getData();

        getListView();

    }

    private void getData() {

        /**
         * Reading and getting all records from database
         */
        List<ContactModel> contacts = databaseHandler.getAllContacts();
        for (ContactModel cn : contacts) {
            String log = "Id:" + cn.getId()
                    + " Name: " + cn.getName()
                    + " ,Image: " + cn.getPhoto();

            // Writing Contacts to log
            Log.d("Result: ", log);
            // add contacts data in arrayList
            dataList.add(cn);

        }
    }



    private void getListView() {
        /**
         * Set Data base Item into listview
         */
        ModedataList = databaseHandler.getAllContacts();
        imageAdapter = new ContactAdapter(this, R.layout.row, dataList);
//        imageAdapter = new ContactAdapter(this, R.layout.row, dataList);
        listView.setAdapter(imageAdapter);




        /**
         * go to next activity for detail image
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                ptModel = ModedataList.get(position);

                Log.d("Before Send:****", "modelContactPhoto");

                // Starting the Intent in the First Activity
                Intent intent = new Intent(ListDisplay.this, DisplayActivity.class);
                // mplemented the Parcelable interface correctly
                intent.putExtra("modelContactPhoto", ptModel);
                startActivity(intent);
                finish();


            }
        });




    }


}
