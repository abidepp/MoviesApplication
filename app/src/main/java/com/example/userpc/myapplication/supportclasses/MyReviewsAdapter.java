package com.example.userpc.myapplication.supportclasses;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.userpc.myapplication.MoviesActivity;
import com.example.userpc.myapplication.R;
import com.example.userpc.myapplication.serviceClass.ImageLoadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by user pc on 1/28/2018.
 */
public class MyReviewsAdapter extends RecyclerView.Adapter<MyReviewsAdapter.MyViewHolderReviews> {

    List<Information> data = new ArrayList<>();
    LayoutInflater inflater;

    public MyReviewsAdapter(Context context, List<Information> data)
    {
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolderReviews onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View myView = inflater.inflate(R.layout.reviews_row_layout,parent, false);
        MyViewHolderReviews holder = new MyViewHolderReviews(myView);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolderReviews holder, int position) {


        Information temp = new Information();
        temp = data.get(position);

        //to set image from the URL
        Constants constantsObj = new Constants();
        String imageAddress = constantsObj.getConfiguration_image_url();

        //getImagePoster
        ImageLoadTask getImage = new ImageLoadTask();
        Bitmap poster = null;
        try {
             poster = getImage.execute(imageAddress+temp.reviewsposter).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        holder.title.setText(temp.reviewsTitle);
        holder.rating.setText(temp.reviewsRating);
        holder.review.setText(temp.reviewsoverview);
        holder.poster.setImageBitmap(poster);

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolderReviews extends RecyclerView.ViewHolder {

        ImageView poster;
        TextView title;
        TextView rating;
        TextView review;
        public MyViewHolderReviews(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.review_row_image);
            title = (TextView) itemView.findViewById(R.id.review_row_title);
            rating = (TextView) itemView.findViewById(R.id.review_row_rating);
            review = (TextView) itemView.findViewById(R.id.review_row_reviews);
        }
    }
}
