package com.example.junaid.cameral.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.junaid.cameral.ImageUtil;
import com.example.junaid.cameral.R;
import com.example.junaid.cameral.model.ContactModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler  extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "jaPhoto.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "img_table";
    private static final String COLUMNS_ID = "_id";
    private static final String COLUMNS_NAME = "contact_name";
//    private static final String COLUMNS_PHONENUM = "contact_number";
//    private static final String COLUMNS_EMAIL = "contact_email";
    private static final String COLUMNS_PHOTO = "photo";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMNS_ID + " INTEGER PRIMARY KEY,"
            + COLUMNS_NAME + " TEXT,"
            + COLUMNS_PHOTO + " BLOB"
            + ")";

//    // Tables creation queries
//    String CREATE_EVENT_TABLE = "create table " + TABLE_EVENT + "("
//            + COLUMN_EVENT_EID + " integer primary key, "
//            + COLUMN_EVENT_CREATION_DATE + " text, "
//            + COLUMN_EVENT_TITLE + " text, "
//            + COLUMN_EVENT_ICON + " text)";
//
//

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }


    // Adding new contact
    public long addContact(ContactModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMNS_NAME, contact.getName()); // Contact Name
        // Convert Bitmap to ByteArray
        byte[] imageByte = ImageUtil.getByteArray(contact.getPhoto());

        values.put(COLUMNS_PHOTO, imageByte); // photo

        // Inserting Row
       long id = db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
        return id;
    }


    // Getting single contact
//    public Contact getContact(int id) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.query(TABLE_NAME,
//                new String[]{ COLUMNS_ID,COLUMNS_NAME,COLUMNS_PHOTO}, COLUMNS_ID + "=?",
//                new String[] { String.valueOf(id) }, null, null, null, null);
//
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        Contact contact = new Contact();
//
//        return contact;
//    }


    // Getting All Contacts
    public List<ContactModel> getAllContacts() {
        List<ContactModel> contactList = new ArrayList<ContactModel>();
        // Select All Query
       // String selectQuery = "SELECT  * FROM "+ TABLE_NAME + "ORDER BY"+COLUMNS_ID;  //no such table: img_tableORDER
        String selectQuery = "SELECT  * FROM "+ TABLE_NAME + " ORDER BY "+COLUMNS_ID; // correct syntax

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // create Obj
                ContactModel contactPhoto = new ContactModel();

                contactPhoto.setId(cursor.getString(cursor.getColumnIndex(COLUMNS_ID)));
                contactPhoto.setName(cursor.getString(cursor.getColumnIndex(COLUMNS_NAME)));

                byte[] imageBytes=cursor.getBlob(cursor.getColumnIndex(COLUMNS_PHOTO)) ;
                Bitmap bmp = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                // Convert ByteArray to Bitmap
                Bitmap img = ImageUtil.getBitmap(cursor.getBlob(cursor.getColumnIndex(COLUMNS_PHOTO)));


                contactPhoto.setPhoto(img);

                // Adding contact to list
                contactList.add(contactPhoto);
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return contactList;

    }



    // Deleting single contact
    public void deleteContact(ContactModel contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMNS_ID + " = ?",
                new String[] { String.valueOf(contact.getId()) });
        db.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}