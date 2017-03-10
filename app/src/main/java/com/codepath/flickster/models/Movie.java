package com.codepath.flickster.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hkanekal on 3/5/2017.
 */

public class Movie {

    String posterPath;
    String backdropPath;
    String originalTitle;
    String overView;
    float rateing;

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

    public float getRateing() {
        return rateing;
    }

    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overView = jsonObject.getString("overview");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.rateing = Float.valueOf(jsonObject.getString("vote_average").toString());
    }

    public static ArrayList<Movie> fromJSONArray(JSONArray array){
        ArrayList<Movie> results = new ArrayList<>();

        //Log.d("DEBUG","Array Len = "+array.length()+" M -> "+array);
        //for (int f=0;f<array.length();f++) {
        //    results.add(f,null);
        //}
        for(int x=0; x<array.length(); x++){
            try {
                /*
                String c = array.getString(x);
                //Log.d("DEBUG","Ac: "+c);
                JSONObject m = new JSONObject(c);
                Log.d("DEBUG","Mm: "+m);
                Movie mM = new Movie(m);
                results.set(x,mM);
                */
                results.add(new Movie(array.getJSONObject(x)));
                Log.d("DEBUG","res@x -> "+results.get(x).toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

}
