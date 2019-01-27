package com.zewail.dakrory.zccourses.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.Base64;
import android.widget.ImageView;

import com.zewail.dakrory.zccourses.Models.UserData;


/**
 * Created by A7med Al-Dakrory on 1/17/2019.
 */

public class Tools {
    public static boolean isLoggedIn=false;
    public static int Circular=0;
    public static int Same=1;

    public static void SetImageToImageView(String image,ImageView imageView){
        String cleanImage = image.replace("data:image/png;base64, ", "").replace("data:image/jpeg;base64,","");
        byte[] backToBytes = Base64.decode(cleanImage, Base64.DEFAULT);

        Bitmap bmp = BitmapFactory.decodeByteArray(backToBytes, 0, backToBytes.length);
        imageView.setImageBitmap(cropToSquare(bmp));
    }

    public static void SetImageToImageView(String image,ImageView imageView,int type){
        String cleanImage = image.replace("data:image/png;base64, ", "").replace("data:image/jpeg;base64,","");
        byte[] backToBytes = Base64.decode(cleanImage, Base64.DEFAULT);

        Bitmap bmp = BitmapFactory.decodeByteArray(backToBytes, 0, backToBytes.length);
        if(type==Circular){

            imageView.setImageBitmap(getCroppedBitmap(cropToSquare(bmp)));

        }else {
            imageView.setImageBitmap(bmp);
        }
    }

    public static UserData thisUserAccount;
    public static void SetUsetDataOfThisAccount(Context context,String email, String password){

        isLoggedIn=true;
        SharedPreferences.Editor editor = context.getSharedPreferences("Main", context.MODE_PRIVATE).edit();
        editor.putString(Parameters.UserEmail, email);
        editor.putString(Parameters.UserPassword, password);
        editor.putBoolean(Parameters.UserStatue, isLoggedIn);
        editor.apply();
    }

    public static void LogOut(Context context){

        isLoggedIn=false;
        thisUserAccount=null;
        SharedPreferences.Editor editor = context.getSharedPreferences("Main", context.MODE_PRIVATE).edit();
        editor.putString(Parameters.UserEmail, "");
        editor.putString(Parameters.UserPassword, "");
        editor.putBoolean(Parameters.UserStatue, isLoggedIn);
        editor.apply();
    }

    public static String UserEmail;
    public static String UserPassword;

    public static void getUser(Context context){
        SharedPreferences prefs = context.getSharedPreferences("Main", context.MODE_PRIVATE);
        UserEmail = prefs.getString(Parameters.UserEmail, null);
        UserPassword = prefs.getString(Parameters.UserPassword, null);
        isLoggedIn = prefs.getBoolean(Parameters.UserStatue, false);

    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {

        Bitmap circleBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        BitmapShader shader = new BitmapShader (bitmap,  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setAntiAlias(true);
        Canvas c = new Canvas(circleBitmap);
        c.drawCircle(bitmap.getWidth()/2, bitmap.getHeight()/2, bitmap.getWidth()/2, paint);

        return circleBitmap;
    }

    public static Bitmap cropToSquare(Bitmap bitmap){
        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width)? height - ( height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0)? 0: cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0)? 0: cropH;
        Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);

        return cropImg;
    }
}
