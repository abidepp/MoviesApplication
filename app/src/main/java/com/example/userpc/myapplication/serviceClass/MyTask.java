package com.example.userpc.myapplication.serviceClass;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.userpc.myapplication.Interface.sendDataTOActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by user pc on 1/26/2018.
 */
public class MyTask extends AsyncTask<String, String, String> {

    sendDataTOActivity msendDataTOActivity;
    String progressData = "";
    public MyTask(Activity activity)
    {
        msendDataTOActivity = (sendDataTOActivity) activity;
       // msendDataTOActivity.sendData();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressData = "getting content";
        msendDataTOActivity.sendData(progressData);

    }


    @Override
    protected String doInBackground(String... params) {

        String result = "";
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0;i<params.length;i++) {
            try {
                Log.i("test","url check--->"+params[i]);
                URL myUrl = new URL(params[i]);
                HttpURLConnection getConnection = (HttpURLConnection) myUrl.openConnection();

                //set methods and timeout
                getConnection.setRequestMethod("GET");
                getConnection.setReadTimeout(15000);
                getConnection.setConnectTimeout(15000);

                //set connection to our URl
                getConnection.connect();

                InputStreamReader inputStreamReader = new InputStreamReader(getConnection.getInputStream());

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                int progress = 0;
                while ((inputLine = bufferedReader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                    publishProgress("fetching...");
                }
                stringBuilder.append("////");

                bufferedReader.close();
                inputStreamReader.close();

                if(i>0)
                {
                    result = stringBuilder.toString();
                    Log.i("test", "value from api 1--->" + result);
                }else
                {
                    String check = stringBuilder.toString();
                    //Log.i("test", "value from api 0--->" + check);
                }
                result = stringBuilder.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        msendDataTOActivity.sendData(values[0]);
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressData = "completed";
        msendDataTOActivity.sendData(progressData);
        }


}
