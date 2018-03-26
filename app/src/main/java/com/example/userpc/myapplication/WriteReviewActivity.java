package com.example.userpc.myapplication;


import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.userpc.myapplication.supportclasses.Constants;

import java.util.Iterator;
import java.util.Map;

public class WriteReviewActivity extends BaseActivity implements SearchView.OnQueryTextListener {

    SearchView searchView;
    Button reviewItButton;
    ListView list;
    String selectedItem = ""; // to get the movie ID from the movie name in list view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_write_review);

        getLayoutInflater().inflate(R.layout.activity_write_review,frameLayout);

        reviewItButton = (Button) findViewById(R.id.reviewitbutton);
        searchView = (SearchView) findViewById(R.id.searchView);
        list = (ListView) findViewById(R.id.listView);
        list.setVisibility(View.INVISIBLE);

        //to get the MovieNames list from the constants file
        final Constants mConstants = new Constants();

        Log.i("test","data of movie names---->"+mConstants.getMovieNames());
        Log.i("test","size of movie names---->"+mConstants.getMovieNames().size());

        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mConstants.getMovieNames()));
        list.setTextFilterEnabled(true);
        setupSearchView(selectedItem);


        //on list itemclick

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                selectedItem = (String) list.getItemAtPosition(i);
                setupSearchView(selectedItem);
            }
        });



        reviewItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedItem != "")
                {
                    final Dialog myDialog;
                    myDialog = new Dialog(view.getContext());
                    myDialog.setContentView(R.layout.submit_review);
                    myDialog.setCancelable(true);
                    myDialog.setCanceledOnTouchOutside(true);
                    Button submit = (Button) myDialog.findViewById(R.id.submitreview);

                    final EditText movieID = (EditText) myDialog.findViewById(R.id.movieidedit);
                    final EditText shortReview = (EditText) myDialog.findViewById(R.id.shortreviewedit);
                    final EditText fullReview = (EditText) myDialog.findViewById(R.id.fullreviewedit);
                    ImageButton close = (ImageButton) myDialog.findViewById(R.id.closeimageButton);

                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            myDialog.dismiss();
                        }
                    });

                    //to populate the popup with movie ID
                    Map<String, String> map  = mConstants.getTitleAndIdMap();

                    Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<String, String> entry = iterator.next();
                        if(selectedItem == entry.getKey())
                        {
                            movieID.setText(entry.getValue());
                        }
                    }

                    myDialog.show();

                    submit.setOnClickListener(new View.OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            //your submit review calculation goes here
                            if(movieID.getText().toString() != "" && shortReview.getText().toString() !="" && fullReview.getText().toString() != "")
                            {
                                postReview(movieID.getText().toString().trim());
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"please select the movie name from list to post review", Toast.LENGTH_LONG).show();
                }
                }

        });
    }

    public void setupSearchView(String searchValue)
    {
        if(searchValue =="")
        {
            searchView.setIconifiedByDefault(false);
            searchView.setOnQueryTextListener(this);
            searchView.setSubmitButtonEnabled(false);
            searchView.setQueryHint(getString(R.string.search_Movie_Names));
        }
        else
        {
            searchView.setQuery("", false);
            searchView.setQuery(searchValue, true);
            //to clear the list and put it back to invisible state
            list.clearTextFilter();
            list.setVisibility(View.INVISIBLE);
        }
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

    public void postReview(String review)
    {

    }
}
