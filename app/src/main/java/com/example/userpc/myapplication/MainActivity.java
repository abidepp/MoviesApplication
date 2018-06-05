package com.example.userpc.myapplication;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.userpc.myapplication.serviceClass.MyLocationService;
import com.example.userpc.myapplication.supportclasses.Constants;
import com.example.userpc.myapplication.supportclasses.Information;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{


    Intent serviceIntent;
    List<String> MovieNames = new ArrayList<String>();
    Constants mConstants;

    // Will show the string "data" that holds the results
    TextView results;
    // URL of object to be parsed
    String[] JsonURL = new String[1];
    // This string will hold the results
    String data = "";
    // Defining the Volley request queue that handles the URL request concurrently
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);
        Button login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userAuthentication();
            }
        });

        //enable location service
        serviceIntent = new Intent(this, MyLocationService.class);
        startService(serviceIntent);
        Log.i("location service","intent call done");



        //to get the movie names when the application starts for search functionality
        mConstants = new Constants();
        mConstants.getConfiguration(this); // call this before you call getMovieNames() for the movie names in search functionality

    }

    public void getVolleyService(Context context, String code)
    {
        // Creates the Volley request queue
        requestQueue = Volley.newRequestQueue(context);

        // Casts results into the TextView found within the main layout XML with id jsonData
        String result;

        // Creating the JsonObjectRequest class called obreq, passing required parameters:
        //GET is used to fetch data from the server, JsonURL is the URL to be fetched from.
        JsonObjectRequest obreq = new JsonObjectRequest(Request.Method.GET, data, new JSONObject(),new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                // Takes the response from the JSON request
                try {
                    JSONObject obj = response;
                    // Retrieves the string labeled "colorName" and "description" from
                    //the response JSON Object
                    //and converts them into javascript objects
                    String success = obj.getString("success");
                    String expires_at = obj.getString("expires_at");
                    String request_token = obj.getString("request_token");



                }
                // Try and catch are included to handle any errors due to JSON
                catch (JSONException e) {
                    // If an error occurs, this prints the error to the log
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "Error");
            }
        });
        // Adds the JSON object request "obreq" to the request queue
        requestQueue.add(obreq);
    }

    public String getData(String response, String code)
    {
        String result = "";
        try {
            JSONObject jobject = new JSONObject(response);
            if(code == "request_token") {
                result = jobject.getString("request_token");
            }
            else if(code =="session_id")
            {
                result = jobject.getString("session_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void userAuthentication()
    {
        Log.i("MoviesApp","inside userAuthentication---->");
        mConstants = new Constants();
        JsonURL[0] = mConstants.getRequest_token_url()+mConstants.getApiKey();
        Log.i("MoviesApp","request_url---->"+JsonURL[0]);


        //to get the request token and session id
        //getVolleyService(this, "request_token");
        String token_response = mConstants.getMoviesData(JsonURL,this);
        String request_token = getData(token_response, "request_token");
        Log.i("MoviesApp", "request_token"+request_token);
        if(request_token != "")
        {
            String[] authentication_url = new String[1];
            authentication_url[0] = mConstants.getAuthenticationUrl()+request_token;
            String authentication_response = mConstants.getMoviesData(authentication_url,this);
            //Log.i("MoviesApp","authentication_response"+authentication_response);

            Intent webViewIntent = new Intent(this, BrowserActivity.class);
            webViewIntent.putExtra("authenticate",authentication_response);
            startActivity(webViewIntent);
//            String[] session_url = new String[1];
//            session_url[0] = mConstants.getSession_url()+request_token;
//            Log.i("session_url","session_url---->"+session_url[0]);
//            String session_response = mConstants.getMoviesData(session_url,this);
//            String session_id = getData(session_response, "session_id");
//            Log.i("session_id", "session_id"+session_id);
        }
        else
        {
            Toast.makeText(this,"could not create session for the user--->request_token is"+request_token,Toast.LENGTH_SHORT).show();
        }

    }
}
