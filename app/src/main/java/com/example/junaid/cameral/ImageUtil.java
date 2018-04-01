package com.example.junaid.cameral;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by adminpc on 2/4/18.
 */

public class ImageUtil {


    // Function to convert a bitmap image to byte array
    //  byte[] imageByte=profileImage(BitmapImage);
    public static  byte[] getByteArray(Bitmap bitmap){

//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
////        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos);
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, bos);


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();


        //return bos.toByteArray();
        return byteArray;

    }

   // Function to convert a byte array back to bitmap image
    // Bitmap img=convertToBitmap(byteArray);
   public static Bitmap getBitmap(byte[] bitmap) {
       return BitmapFactory.decodeByteArray(bitmap , 0, bitmap.length);
   }


    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 90, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0,      decodedByte.length);
    }



    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    // get the base 64 string
//    String imgString = Base64.encodeToString(getBytesFromBitmap(someImg), Base64.NO_WRAP);

}


/**
 * http://whats-online.info/science-and-tutorials/130/how-to-convert-a-bitmap-image-into-byte-array-in-android-and-reverse/
 * https://xjaphx.wordpress.com/2011/11/13/convert-bitmap-to-byte-array-and-reverse/
 * https://stackoverflow.com/questions/10513976/how-to-convert-image-into-byte-array-and-byte-array-to-base64-string-in-android/10514374#10514374
 *https://stackoverflow.com/questions/13758560/android-bitmap-to-byte-array-and-back-skimagedecoderfactory-returned-null?lq=1
 *
 * https://www.sitepoint.com/transfer-data-between-activities-with-android-parcelable/
 *

 Bitmap img = ImageUtil.getBitmap(byteArray);
 byte[] imageByte=ImageUtil.getByteArray(contact.getPhoto());

 Bitmap img = ImageUtil.convertToBitmap(byteArray);
 byte[] imageByte=ImageUtil.getByteArray(contact.getPhoto());



 */


