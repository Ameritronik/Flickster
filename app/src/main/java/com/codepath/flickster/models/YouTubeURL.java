package com.codepath.flickster.models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by hkanekal on 3/5/2017.
 *  Youtube api key: AIzaSyA4bc4yZfIBjfVMTJtvZdQ3C1gmE78DJzg
 */

public class YouTubeURL {

    String YouTubeKey;
    String YouTubeName;
    String YouTubeSize;

    public String getYTKey() {
        return YouTubeKey;
    }
    public String getYTName() {
        return YouTubeName;
    }
    public String getYTSize() {
        return YouTubeSize;
    }
    public YouTubeURL(JSONObject jsonObject) throws JSONException {
        this.YouTubeKey = jsonObject.getString("key");
        this.YouTubeName = jsonObject.getString("name");
        this.YouTubeSize = jsonObject.getString("size");
    }
    public static ArrayList<YouTubeURL> fromJSONArray(JSONArray array){
        ArrayList<YouTubeURL> results = new ArrayList<>();
        for(int x=0; x<array.length(); x++){
            try {
                results.add(new YouTubeURL(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }


}
