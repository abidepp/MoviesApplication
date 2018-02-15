package com.example.userpc.myapplication.serviceClass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by user pc on 1/26/2018.
 */
public class ImageLoadTask extends AsyncTask<String, String, Bitmap> {

    @Override
    protected Bitmap doInBackground(String... params) {

        URL url = null;
        try {
            url = new URL(params[0]);
            return BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
