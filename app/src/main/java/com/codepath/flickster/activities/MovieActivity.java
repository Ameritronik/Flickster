package com.codepath.flickster.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codepath.flickster.R;
import com.codepath.flickster.adapters.ComplexRecyclerViewAdapter;
import com.codepath.flickster.models.Movie;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieActivity extends AppCompatActivity {

    ArrayList<Movie> movies;
    ComplexRecyclerViewAdapter movieAdapter;
    RecyclerView lvItems;
    JSONArray movieJsonResults;
    private static final boolean UseoKHTTP = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        getSupportActionBar().hide();
        lvItems = (RecyclerView) findViewById(R.id.lvMovies);
        movies = new ArrayList<>();
        movieAdapter = new ComplexRecyclerViewAdapter(this, movies);
        lvItems.setAdapter(movieAdapter);
        lvItems.setLayoutManager(new LinearLayoutManager(this));

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        if(UseoKHTTP) {
            // Use OkHttpClient singleton
            OkHttpClient client = new OkHttpClient();
            Picasso picasso = new Picasso.Builder(this).downloader(new OkHttp3Downloader(client)).build();
            Request request = new okhttp3.Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }
                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    String responseData = response.body().string();
                    try {
                        JSONObject jindata = new JSONObject(responseData);
                        if (jindata != null) {
                            movieJsonResults = jindata.getJSONArray("results");;
                            movies.addAll(Movie.fromJSONArray(jindata.getJSONArray("results")));
                            // Run view-related code back on the main thread
                            MovieActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    movieAdapter.notifyDataSetChanged();
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else { // Use AsyncHttp
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray movieJsonResults = null;
                    try {
                        movieJsonResults = response.getJSONArray("results");
                        movies.addAll(Movie.fromJSONArray(movieJsonResults));
                        movieAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                }
            });
        }

    }
}
