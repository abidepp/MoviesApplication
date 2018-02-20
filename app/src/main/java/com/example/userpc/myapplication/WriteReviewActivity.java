package com.example.userpc.myapplication;


import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.userpc.myapplication.supportclasses.Constants;

public class WriteReviewActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SearchView searchView;
    Button reviewItButton;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        reviewItButton = (Button) findViewById(R.id.reviewitbutton);
        searchView = (SearchView) findViewById(R.id.searchView);
        list = (ListView) findViewById(R.id.listView);
        list.setVisibility(View.INVISIBLE);

        //to get the MovieNames list from the constants file
        Constants mConstants = new Constants();

        Log.i("test","data of movie names---->"+mConstants.getMovieNames());
        Log.i("test","size of movie names---->"+mConstants.getMovieNames().size());

        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mConstants.getMovieNames()));
        list.setTextFilterEnabled(true);
        setupSearchView();



        reviewItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog myDialog;
                myDialog = new Dialog(view.getContext());
                myDialog.setContentView(R.layout.submit_review);
                myDialog.setCancelable(true);
                myDialog.setCanceledOnTouchOutside(true);
                Button submit = (Button) myDialog.findViewById(R.id.submitreview);

                EditText movieID = (EditText) myDialog.findViewById(R.id.movieidedit);
                EditText shortReview = (EditText) myDialog.findViewById(R.id.shortreviewedit);
                EditText fullReview = (EditText) myDialog.findViewById(R.id.fullreviewedit);
                ImageButton close = (ImageButton) myDialog.findViewById(R.id.closeimageButton);

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        myDialog.dismiss();
                    }
                });
                myDialog.show();

                submit.setOnClickListener(new View.OnClickListener()
                {

                    @Override
                    public void onClick(View v)
                    {
                        //your login calculation goes here
                    }
                });
            }
        });
    }

    public void setupSearchView()
    {
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint(getString(R.string.search_Movie_Names));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        if (TextUtils.isEmpty(query)) {
            list.clearTextFilter();
            list.setVisibility(View.INVISIBLE);
        } else {
            list.setFilterText(query.toString());
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        list.setVisibility(View.VISIBLE);

        if (TextUtils.isEmpty(newText)) {
            list.clearTextFilter();
            list.setVisibility(View.INVISIBLE);
        } else {
            list.setFilterText(newText.toString());
        }
        return true;
    }
}
