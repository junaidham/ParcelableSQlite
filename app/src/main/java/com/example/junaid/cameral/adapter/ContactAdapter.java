package com.example.junaid.cameral.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.junaid.cameral.ImageUtil;
import com.example.junaid.cameral.R;
import com.example.junaid.cameral.model.ContactModel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends ArrayAdapter {
    Context context;
    int layoutResourceId;
    ArrayList<ContactModel> dataList=new ArrayList<ContactModel>();


    public ContactAdapter(Context context, int layoutResourceId, ArrayList<ContactModel> dataList) {
        super(context, layoutResourceId, dataList);

        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.dataList = dataList;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        View  view = convertView; // view -> row
        ImageHolder holder = null;
        if (view == null){
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(layoutResourceId, parent,false);

            holder = new ImageHolder();
            holder.tvId = view.findViewById(R.id.tvIdR);
            holder.tvTitle = view.findViewById(R.id.tvTitleR);
            holder.imgPhoto = view.findViewById(R.id.imgPhotoR);
            view.setTag(holder);
        }else {
            holder = (ImageHolder) view.getTag();
        }

        // access data to respect of position
        ContactModel contactPhoto = dataList.get(position);
        holder.tvId.setText(contactPhoto.getId());
        holder.tvTitle.setText(contactPhoto.getName());

        //convert byte to bitmap take from contact class
//        byte[] outImage = contactPhoto.getPhoto();
//        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
//        Bitmap theImage = BitmapFactory.decodeStream(imageStream);

        // Convert Bitmap to ByteArray
        byte[] imageByte=ImageUtil.getByteArray(contactPhoto.getPhoto());
        // // Convert ByteArray to Bitmap
        Bitmap theImage = ImageUtil.getBitmap(imageByte);

        holder.imgPhoto.setImageBitmap(theImage);    // set img



        return  view;
    }




    // inner Holder class
    static class ImageHolder {
        ImageView imgPhoto;
        TextView tvTitle;
        TextView tvId;
    }

}