package com.example.userpc.myapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.userpc.myapplication.serviceClass.MyLocationService;
import com.example.userpc.myapplication.supportclasses.Constants;
import com.example.userpc.myapplication.supportclasses.Information;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{


    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;
    Intent serviceIntent;
    List<String> MovieNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);

        //enable location service
        serviceIntent = new Intent(this, MyLocationService.class);
        startService(serviceIntent);
        Log.i("location service","intent call done");


        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTabLayout);


        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        mSlidingTabLayout.setViewPager(mViewPager);

        //to get the movie names when the application starts for search functionality
        Constants mConstants = new Constants();
        mConstants.getConfiguration(); // call this before you call getMovieNames() for the movie names in search functionality

        //String to place our result in global movies list
        String result = mConstants.getMoviesData(mConstants.getNewAndUpcomingMoviesUrl());

    }

    public List<Information> getData(String result) {
        List<Information> data = new ArrayList<Information>();

        int index = 0;  // used in the for loop for obtaining main List

        JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);

                Log.i("test", "final jsonObject---->" + jsonObject);

                JSONArray movies = jsonObject.getJSONArray("results");


                for (int j = 0; j < movies.length(); j++) {
                    JSONObject m = movies.getJSONObject(j);

                    String title = m.getString("original_title");
                    String poster = m.getString("poster_path");


                    Information current = new Information();
                    current.image = poster;
                    current.title = title;
                    data.add(current);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        return data;

    }

}
