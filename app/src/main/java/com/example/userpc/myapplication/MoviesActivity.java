package com.example.userpc.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userpc.myapplication.Interface.ItemClickListener;
import com.example.userpc.myapplication.Interface.sendDataTOActivity;
import com.example.userpc.myapplication.serviceClass.MyTask;
import com.example.userpc.myapplication.supportclasses.Constants;
import com.example.userpc.myapplication.supportclasses.Information;
import com.example.userpc.myapplication.supportclasses.MyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MoviesActivity extends BaseActivity implements sendDataTOActivity{

    private MyAdapter myadapterObject_upcoming;
    private MyAdapter myadapterObject_nowPlaying;
    TextView updateprogress;



    //get the api_key fro constants class
    Constants mConstants = new Constants();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_movies);

        getLayoutInflater().inflate(R.layout.activity_movies,frameLayout);

        RecyclerView recyclerView_upcoming = (RecyclerView) findViewById(R.id.recyclerView_upcoming);
        RecyclerView recyclerView_nowplaying = (RecyclerView) findViewById(R.id.recyclerView_nowplaying);
        updateprogress = (TextView) findViewById(R.id.updateprogress);



//        String[] myUrl = new String[2];
//        //Some url endpoint that you may have
//        myUrl[0] = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + mConstants.getApiKey() + "&language=en-US&page=1";
//        myUrl[1] = "https://api.themoviedb.org/3/movie/now_playing?api_key="+ mConstants.getApiKey() +"&language=en-US&page=1";

        //String to place our result in
        String result = mConstants.getMoviesData(mConstants.getNewAndUpcomingMoviesUrl(), this);




        List<List> data = this.getData(result);
        Log.i("test","this.getData(result)---->"+this.getData(result));


        myadapterObject_upcoming = new MyAdapter(this, data.get(0));
//        Log.i("test","data.get(0))---->"+data.get(0));

        LinearLayoutManager horizontalLayoutManager_upcoming = new LinearLayoutManager(MoviesActivity.this, LinearLayoutManager.HORIZONTAL, false);

        //upcoming movies
        recyclerView_upcoming.setAdapter(myadapterObject_upcoming);
        recyclerView_upcoming.setLayoutManager(horizontalLayoutManager_upcoming);





        myadapterObject_nowPlaying = new MyAdapter(this, data.get(1));

//        Log.i("test","data.get(1))---->"+data.get(1));

        LinearLayoutManager horizontalLayoutManager_nowPlaying = new LinearLayoutManager(MoviesActivity.this, LinearLayoutManager.HORIZONTAL, false);

       // now playing movies
        recyclerView_nowplaying.setAdapter(myadapterObject_nowPlaying);
        recyclerView_nowplaying.setLayoutManager(horizontalLayoutManager_nowPlaying);

        //myadapterObject_nowPlaying.setClickListener(this); //how can you send activity context to ItemClickListener



    }

    public List<List> getData(String result) {
        List<Information> data = new ArrayList<Information>();
        List<List> main = new ArrayList<>();
        List<Information.ListData> dummy = new ArrayList<>();

        int index = 0;  // used in the for loop for obtaining main List


        //to seperate the upcoming movies from New Movies
        String[] seperatedData = result.split("////");

//        Log.i("test","seperatedData of 0----->"+seperatedData[0]);
//        Log.i("test","seperatedData of 1----->"+seperatedData[1]);
//        Log.i("test","seperatedData length----->"+seperatedData.length);

        JSONObject jsonObject = null;
        for(int i=0;i<seperatedData.length;i++)
        {
            try {
                jsonObject = new JSONObject(seperatedData[i]);

                Log.i("test", "final jsonObject---->" + jsonObject);

                JSONArray movies = jsonObject.getJSONArray("results");

                if(i == 0)
                {
                    index = movies.length();  //to seperate upcoming movies from new movies in  data value to the main List
                }

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


        }

        main.add(data.subList(0,index));
        main.add(data.subList(index,data.size()));



        return main;

    }





    @Override
    public void sendData(String str) {
//        if(str == "completed")
//        {
//            updateprogress.setText("");
//        }
//        else
//        {
//            updateprogress.setText(str);
//        }
    }
}
