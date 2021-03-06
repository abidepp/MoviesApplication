package com.example.userpc.myapplication.supportclasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.example.userpc.myapplication.R;
import com.example.userpc.myapplication.serviceClass.MyTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by user pc on 1/26/2018.
 */
public class Constants {

    private final String api_key= "6c899bbd67713e8f2720b10f00b18807";
    private final String request_token_url = "https://api.themoviedb.org/3/authentication/token/new?api_key=";
    private final String session_url = "https://api.themoviedb.org/3/authentication/session/new?api_key="+getApiKey()+"&request_token=";
    private final String authentication_url ="https://www.themoviedb.org/authenticate/";
    private final String configuration_image_url = "http://image.tmdb.org/t/p/w342";
    private String[] myUrl = {"https://api.themoviedb.org/3/movie/upcoming?api_key=" + getApiKey() + "&language=en-US&page=1",
                      "https://api.themoviedb.org/3/movie/now_playing?api_key="+ getApiKey() +"&language=en-US&page=1"};
    private static List<String> MovieNames; //for search function in reviews activity
    private static Map<String, String> titleAndIdMap = new HashMap<>();


    public String getAuthenticationUrl() {
        return authentication_url;
    }

    public String getSession_url() {
        return session_url;
    }

    public String getRequest_token_url(){
        return request_token_url;
    }

    public String getApiKey()
    {
        return api_key;
    }

    public String getConfiguration_image_url()
    {
        return configuration_image_url;
    }


    //get the new and Upcomingmovies URl from this function
    public String[] getNewAndUpcomingMoviesUrl()
    {
        return myUrl;
    }

    //get the new and upcoming movies data from this function
    public String getMoviesData(String[] myUrl, Activity activity)
    {
        String result = "";
        //Instantiate new instance of our class
        MyTask getRequest = new MyTask(activity);
        //Perform the doInBackground method, passing in our url
        try {
            result = getRequest.execute(myUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void getConfiguration(Activity activity)
    {
        String result = getMoviesData(getNewAndUpcomingMoviesUrl(), activity);
        setMovieNames(getData(result)); // get the movie names in a string list and set the data to MovieNames

    }

    public void setMovieNames(List<String> MovieNames)
    {
        this.MovieNames = MovieNames;
        Log.i("test","data of movie names---->"+getMovieNames());
        Log.i("test","size of movie names---->"+getMovieNames().size());
    }

    public List<String> getMovieNames()
    {
        return MovieNames;
    }

    public Map<String, String> getTitleAndIdMap()
    {
        return titleAndIdMap;
    }

    public List<String> getData(String result)
    {

        List<String> MovieNames = new ArrayList<>();

        //get only the movie names from the response
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);

            Log.i("test", "final jsonObject---->" + jsonObject);

            JSONArray movies = jsonObject.getJSONArray("results");

            for (int j = 0; j < movies.length(); j++) {
                JSONObject m = movies.getJSONObject(j);

                String title = m.getString("original_title");
                String movie_id = m.getString("id");

                MovieNames.add(title);
                titleAndIdMap.put(title, movie_id);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return MovieNames;

    }


    public void sendData(String data, Activity activity, boolean isDialogFirstTimeCalled)
    {

        Dialog myDialog = new Dialog(activity);
        TextView dialogue;
        if(isDialogFirstTimeCalled)
        {
//            myDialog.dismiss();
        }

        else if(data != "completed")
        {

            myDialog.setContentView(R.layout.dialog_box);
            myDialog.setCancelable(true);
            myDialog.setCanceledOnTouchOutside(true);
            myDialog.show();
            dialogue = (TextView) myDialog.findViewById(R.id.dialog);
            dialogue.setText(data);

        }
        else if(data == "completed")
        {
//            dialogue.setText(data);
            myDialog.dismiss();
        }

    }
}
