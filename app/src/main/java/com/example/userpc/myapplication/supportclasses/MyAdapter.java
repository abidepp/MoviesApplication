package com.example.userpc.myapplication.supportclasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.userpc.myapplication.Interface.ItemClickListener;
import com.example.userpc.myapplication.R;
import com.example.userpc.myapplication.serviceClass.ImageLoadTask;
import com.example.userpc.myapplication.serviceClass.MyTask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by user pc on 1/3/2018.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();
    List<Map> reviewsData = Collections.emptyList();
    Bitmap bmp = null;//for loading movie poster from imageLoadTask class
    private ItemClickListener clickListener;

    public MyAdapter(Context context, List<Information> data)
    {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }



    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       // Log.i("Test","inside createViewHolder");
        View view = inflater.inflate(R.layout.movies_row_layout, parent, false);
        MyHolder holder = new MyHolder(view);
       // Log.i("Test","returned holder---->"+holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {


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
    }


    @Override
    public int getItemCount()
    {
        return data.size();
    }


    public void setClickListener(ItemClickListener itemClickListener)
    {
        this.clickListener = itemClickListener;
    }


    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        ImageView image;
        TextView title;

        public MyHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.movieTitle);
            image = (ImageView) itemView.findViewById(R.id.movieImage);
        }

        @Override
        public void onClick(View view) {
            if(clickListener != null)
            clickListener.onClick(view,getAdapterPosition());
        }
    }
}
