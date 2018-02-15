package com.example.userpc.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.example.userpc.myapplication.supportclasses.Constants;
import com.example.userpc.myapplication.supportclasses.Information;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrailerActivity extends AppCompatActivity {

    Constants getKey = new Constants();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        int data = getIntent().getIntExtra("movieId",-1);
        Log.i("test", "position clicked-->"+data);

        VideoView videoView = (VideoView) findViewById(R.id.videoView);


        //get MoviesActivity Object
        MoviesActivity mMoviesActivity = new MoviesActivity();

        String[] url = new String[1];
        url[0] = "https://api.themoviedb.org/3/movie/157336/videos?api_key="+getKey.getApiKey()+"&language=en-US";// get the movie ID dynamically

        String GetKey = mMoviesActivity.getMoviesData(url);

        String trailerUrl = "https://www.youtube.com/watch?v="+GetKey;
        videoView.setVideoPath(trailerUrl);
        videoView.start();

    }

    public String getData(String result) {
        List<Information> data = new ArrayList<>();
        String key="";
        StringBuilder keyName = new StringBuilder();

        // JSONObject jsonObject = null;
        try {

            JSONObject jsonObject = new JSONObject(result);

            JSONArray movies = jsonObject.getJSONArray("results");

            for (int j = 0; j < movies.length(); j++) {
                JSONObject m = movies.getJSONObject(j);

                if(m.getString("type") == "Trailer")   //check this loop for some random key values might get returned
                {
                    key = m.getString("key");
                    Information current = new Information();
                    current.key = key;
                }
            }
            keyName.append(key).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("test","main.add(data) ----->"+data);
        Log.i("test","main.add(data) length----->"+data.size());

        return keyName.toString();

    }
}
