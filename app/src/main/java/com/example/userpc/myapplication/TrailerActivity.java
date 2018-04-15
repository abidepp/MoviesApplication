package com.example.userpc.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.userpc.myapplication.supportclasses.ConfigurationVideoTube;
import com.example.userpc.myapplication.supportclasses.Constants;
import com.example.userpc.myapplication.supportclasses.Information;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrailerActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {


    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    String trailerUrl = "";

    Constants getKey = new Constants();

    private MyPlayerStateChangeListener playerStateChangeListener;
    private MyPlaybackEventListener playbackEventListener;

    private YouTubePlayer youTubePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);

        Intent startingIntent = getIntent();
        String movieId = startingIntent.getStringExtra("movieID");

        String[] url = new String[1];
        url[0] = "https://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=" + getKey.getApiKey() + "&language=en-US";// get the movie trailer link


        Constants mConstants = new Constants();
        String GetKey = getData(mConstants.getMoviesData(url, this));

        Log.i("GetKey---->",""+GetKey);

        trailerUrl = GetKey;

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        youTubeView.initialize(ConfigurationVideoTube.YOUTUBE_API_KEY, this);

        //initializing the player listener interfaces
        playerStateChangeListener = new MyPlayerStateChangeListener();
        playbackEventListener = new MyPlaybackEventListener();

        //code for seek button on click listener
        final EditText seekToText = (EditText) findViewById(R.id.seek_to_text);
        Button seekToButton = (Button) findViewById(R.id.seek_to_button);
        seekToButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int skipToSecs = Integer.valueOf(seekToText.getText().toString());
                youTubePlayer.seekToMillis(skipToSecs * 1000);
            }
        });
    }

    public void showMessage(String message)
    {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public String getData(String result) {
        List<Information> data = new ArrayList<>();
        String key = "";
        StringBuilder keyName = new StringBuilder();

        // JSONObject jsonObject = null;
        try {

            JSONObject jsonObject = new JSONObject(result);

            JSONArray movies = jsonObject.getJSONArray("results");

            for (int j = 0; j < movies.length(); j++) {
                JSONObject m = movies.getJSONObject(j);

                if (m.getString("type") == "Trailer")   //check this loop for some random key values might get returned
                {
                    key = m.getString("key");
                    Information current = new Information();
                    current.key = key;
                }
            }
            keyName.append(key).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("test", "main.add(data) ----->" + data);
        Log.i("test", "main.add(data) length----->" + data.size());

        return keyName.toString();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        this.youTubePlayer = youTubePlayer;

        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

        if (!wasRestored && trailerUrl != "") {
            youTubePlayer.cueVideo(trailerUrl); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
        else
        {
            showMessage("couldnt find the URL");
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {

        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(getString(R.string.player_error), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }


    private final class MyPlaybackEventListener implements YouTubePlayer.PlaybackEventListener {

        @Override
        public void onPlaying() {
            // Called when playback starts, either due to user action or call to play().
            showMessage("");
        }

        @Override
        public void onPaused() {
            // Called when playback is paused, either due to user action or call to pause().
            showMessage("");
        }

        @Override
        public void onStopped() {
            // Called when playback stops for a reason other than being paused.
            showMessage("");
        }

        @Override
        public void onBuffering(boolean b) {
            // Called when buffering starts or ends.
        }

        @Override
        public void onSeekTo(int i) {
            // Called when a jump in playback position occurs, either
            // due to user scrubbing or call to seekRelativeMillis() or seekToMillis()
        }
    }

    private final class MyPlayerStateChangeListener implements YouTubePlayer.PlayerStateChangeListener {

        @Override
        public void onLoading() {
            // Called when the player is loading a video
            // At this point, it's not ready to accept commands affecting playback such as play() or pause()
        }

        @Override
        public void onLoaded(String s) {
            // Called when a video is done loading.
            // Playback methods such as play(), pause() or seekToMillis(int) may be called after this callback.
        }

        @Override
        public void onAdStarted() {
            // Called when playback of an advertisement starts.
        }

        @Override
        public void onVideoStarted() {
            // Called when playback of the video starts.
        }

        @Override
        public void onVideoEnded() {
            // Called when the video reaches its end.
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
            // Called when an error occurs.
        }
    }
}
