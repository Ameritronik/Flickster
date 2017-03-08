package com.codepath.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hkanekal on 3/5/2017.
 */

public class Movie {
    public String getPosterPath() {
        //Log.d("DEBUG","PostP-> "+posterPath);
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }
    public String getBackdropPath() {
        //Log.d("DEBUG","BackP-> "+backdropPath);
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdropPath);
    }
    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverView() {
        return overView;
    }

    String posterPath;
    String backdropPath;
    String originalTitle;
    String overView;

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overView = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array){
        ArrayList<Movie> results = new ArrayList<>();

        for(int x=0; x<array.length(); x++){
            try {
                results.add(new Movie(array.getJSONObject(x)));
                //Log.d("DEBUG","M["+x+"] = "+results.get(x).originalTitle);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
