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

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolBar;
    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Intent serviceIntent;
    List<String> MovieNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set up drawerlayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolBar = (Toolbar) findViewById(R.id.toolBar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, toolBar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        setSupportActionBar(toolBar);
        toolBar.showOverflowMenu();
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //   actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_chevron_right);
        //   actionBarDrawerToggle.setDrawerIndicatorEnabled(false);





        //enable location service
        serviceIntent = new Intent(this, MyLocationService.class);
        startService(serviceIntent);
        Log.i("location service","intent call done");


        setNavigationViewListener();

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTabLayout);


        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        mSlidingTabLayout.setViewPager(mViewPager);

        //to get the movie names when the application starts for search functionality
        Constants mConstants = new Constants();
        mConstants.getConfiguration(); // call this before you call getMovieNames() for the movie names in search functionality



    }

//    @Override
//    public void setContentView(int layoutResID)
//    {
//        DrawerLayout fullView = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_main, null);
//        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
//        getLayoutInflater().inflate(layoutResID, linearLayout, true);
//        super.setContentView(fullView);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);
//        setTitle("Activity Title");
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.overflow_menu_items, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if(id == R.id.menuSettings)
        {
            Toast.makeText(this,"settings clicked", Toast.LENGTH_SHORT).show();
        }

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setNavigationViewListener() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        if(item.getItemId() == R.id.nav_send)
        {

            Log.i("Activity started", "initiationg movies activity");
            Intent intent = new Intent(this, MoviesActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.nav_share)
        {

            Log.i("Activity started", "initiationg reviews activity");
            Intent intent = new Intent(this, ReviewsActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.nav_writeReview)
        {

            Log.i("Activity started", "initiationg writereviews activity");
            Intent intent = new Intent(this, WriteReviewActivity.class);
            startActivity(intent);
        }

        return false;
    }


}
