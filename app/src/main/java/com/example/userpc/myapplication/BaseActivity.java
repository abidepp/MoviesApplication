package com.example.userpc.myapplication;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolBar;
    ActionBarDrawerToggle actionBarDrawerToggle;

    protected FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        //set up drawerlayout
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
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

        setNavigationViewListener();
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


        if(item.getItemId() == R.id.movies)
        {

            Log.i("Activity started", "initiationg movies activity");
            Intent intent = new Intent(this, MoviesActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.reviews)
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
        if(item.getItemId() == R.id.Home)
        {

            Log.i("Activity started", "initiationg Main activity");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.nav_About)
        {
            Log.i("Activity started", "initiationg AboutUs activity");

        }

        return false;
    }

}
