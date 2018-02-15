package com.example.userpc.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.userpc.myapplication.supportclasses.Constants;
import com.example.userpc.myapplication.supportclasses.Information;
import com.example.userpc.myapplication.supportclasses.MyReviewsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewsActivity extends AppCompatActivity {

    MyReviewsAdapter adapter;
    //get the api_key fro constants class
    Constants getKey = new Constants();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

       // progressBar = (ProgressBar) findViewById(R.id.progressBar);
        RecyclerView recyclerView_reviews = (RecyclerView) findViewById(R.id.recyclerView_reviews);

        //get movies data from service call
        MoviesActivity mMoviesActivity = new MoviesActivity();

        String[] myUrl = new String[1];
        //Some url endpoint that you may have
        myUrl[0] = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + getKey.getApiKey() + "&language=en-US&page=1";
        String result = mMoviesActivity.getMoviesData(myUrl);

        //get list for results to be populated in reviews activity
        List<Information> data = this.getData(result);

        adapter = new MyReviewsAdapter(this, data);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ReviewsActivity.this);
        recyclerView_reviews.setAdapter(adapter);
        recyclerView_reviews.setLayoutManager(mLinearLayoutManager);
    }


    public List<Information> getData(String result) {
        List<Information> data = new ArrayList<>();

       // JSONObject jsonObject = null;
            try {

                JSONObject jsonObject = new JSONObject(result);

                JSONArray movies = jsonObject.getJSONArray("results");

                Log.i("test","movies--data) ----->"+movies);

                for (int j = 0; j < movies.length(); j++) {
                    JSONObject m = movies.getJSONObject(j);

                    String title = m.getString("original_title");
                    String poster = m.getString("poster_path");
                    String rating = m.getString("vote_average");
                    String review = m.getString("overview");

                    Information current = new Information();
                    current.reviewsposter = poster;
                    current.reviewsTitle = title;
                    current.reviewsRating = rating;
                    current.reviewsoverview = review;
                    data.add(current);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        Log.i("test","main.add(data) ----->"+data);
        Log.i("test","main.add(data) length----->"+data.size());

        return data;

    }
}
