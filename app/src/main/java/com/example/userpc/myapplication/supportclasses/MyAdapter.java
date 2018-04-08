package com.example.userpc.myapplication.supportclasses;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userpc.myapplication.Interface.ItemClickListener;
import com.example.userpc.myapplication.MoviesActivity;
import com.example.userpc.myapplication.R;
import com.example.userpc.myapplication.TrailerActivity;
import com.example.userpc.myapplication.serviceClass.ImageLoadTask;
import com.example.userpc.myapplication.serviceClass.MyTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by user pc on 1/3/2018.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder>{

    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();
    List<Map> reviewsData = Collections.emptyList();
    Bitmap bmp = null;//for loading movie poster from imageLoadTask class
    ItemClickListener clickListener;
    Context context;

    public MyAdapter(Context context, List<Information> data)
    {
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }



    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       // Log.i("Test","inside createViewHolder");
        View view = inflater.inflate(R.layout.movies_row_layout, parent, false);
        final MyHolder holder = new MyHolder(view);
       // Log.i("Test","returned holder---->"+holder);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {


        Information current = new Information();
        current = data.get(position);

        holder.title.setText(current.title);


        //to set image from the URL
        Constants constantsObj = new Constants();
        String imageAddress = constantsObj.getConfiguration_image_url();


        ImageLoadTask getImage = new ImageLoadTask();
        //Perform the doInBackground method, passing in our url
        try {
            bmp = getImage.execute(imageAddress+current.image).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        holder.image.setImageBitmap(bmp);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String movieTitle = data.get(position).title;// get the movie title which is selected
                String movieID = ""; //to get the movieID of the selected movie
                Map<String, String> map = new HashMap<String, String>();

                Constants constants = new Constants(); // to get the titlesAndID map for reference iteration here
                map = constants.getTitleAndIdMap();

                Iterator <Map.Entry<String,String>> iterator = map.entrySet().iterator();

                while(iterator.hasNext())
                {
                    Map.Entry<String, String> entry = iterator.next();
                    Log.i(""+entry.getKey(), ""+entry.getValue());
                    Log.i("title"+movieTitle, ""+entry.getKey());
//                    if(map.containsKey(movieTitle))
//                    {
//                        Toast.makeText(context, "success", Toast.LENGTH_LONG).show();
//                    }
                    String temp = entry.getKey();
                    if(movieTitle.equals(temp))
                    {
                        movieID = entry.getValue();
                        Log.i("movie id selected--->", ""+entry.getValue());
                    }
                }

                if(movieID != "")
                {
                    Log.i(movieID,"movieId");
                    Intent intent = new Intent(context, TrailerActivity.class);
                    intent.putExtra("movieID", movieID);
                    context.startActivity(intent);
//                    moviesActivity.startTrailerActivityFromMoviesActivity(movieID, context);
                }
                else
                {
                    Toast.makeText(context, "Oops! The trailer for the movie is not available", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    @Override
    public int getItemCount()
    {
        return data.size();
    }



    class MyHolder extends RecyclerView.ViewHolder
    {

        ImageView image;
        TextView title;
        Context context;

        public MyHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.movieTitle);
            image = (ImageView) itemView.findViewById(R.id.movieImage);
            this.context = itemView.getContext();
        }
    }
}
