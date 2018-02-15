package com.example.userpc.myapplication;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.userpc.myapplication.serviceClass.MyLocationService;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolBar;
    ViewPager mViewPager;
    SlidingTabLayout mSlidingTabLayout;
    Intent serviceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceIntent = new Intent(this, MyLocationService.class);
        startService(serviceIntent);
        Log.i("location service","intent call done");

        toolBar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        toolBar.showOverflowMenu();
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        setNavigationViewListener();

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.slidingTabLayout);


        mViewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        mSlidingTabLayout.setViewPager(mViewPager);


    }

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
