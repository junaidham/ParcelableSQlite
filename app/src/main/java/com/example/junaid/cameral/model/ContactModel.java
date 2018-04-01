package com.example.junaid.cameral.model;


import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ContactModel implements Parcelable {


    private String id;
    private String name;
    private Bitmap photo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public ContactModel() {
    }

    public ContactModel(String id) {
        this.id = id;
    }

    protected ContactModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        photo = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeValue(photo);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ContactModel> CREATOR = new Parcelable.Creator<ContactModel>() {
        @Override
        public ContactModel createFromParcel(Parcel in) {
            return new ContactModel(in);
        }

        @Override
        public ContactModel[] newArray(int size) {
            return new ContactModel[size];
        }
    };


}