package com.codepath.flickster.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.codepath.flickster.R.drawable.my_placeholder;
import static com.codepath.flickster.R.drawable.my_placeholder_error;

/**
 * Created by hkanekal on 3/10/2017.
 */

public class MovieDetailActivity extends AppCompatActivity{

    // ivMovieDetailImage tvTitle releaseDate ratingBar tvOverview
    String mURL = null;
    String mTitle = null;
    String mRelDate = null;
    String mOverview = null;
    String mId = null;
    String mName = null;
    String mKey = null;
    int rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        mURL = extras.getString("mURL");
        mTitle = extras.getString("mTitle");
        mRelDate = extras.getString("mRelDate");
        rate = extras.getInt("mRate");
        mOverview = extras.getString("mOverview");
        mId = extras.getString("mId");
        mName = extras.getString("mName");
        mKey = extras.getString("mKey");
        // now that you have all of the items display them
        displayMovieDetails(this, mURL,mTitle,mRelDate,rate,mOverview,mId,mName,mKey);
    }

    private void displayMovieDetails(final Context context, String mURL, String mTitle,
                                     String mRelDate, int rate, String mOverview,
                                     final String mId, final String mName, final String mKey) {
        setContentView(R.layout.activity_moviedisplay);
        getSupportActionBar().hide();
        TextView tvTitle =  (TextView) findViewById(R.id.tvTitle);
        TextView tvOverview = (TextView) findViewById(R.id.tvOverview);
        TextView tvRelDate = (TextView) findViewById(R.id.releaseDate);
        tvTitle.setTextColor(Color.WHITE);
        tvTitle.setText(mTitle);
        tvRelDate.setTextColor(Color.WHITE);
        tvRelDate.setText("Release Date: "+mRelDate);
        tvRelDate.setTextColor(Color.WHITE);
        tvRelDate.setTextSize(16);
        tvOverview.setTextColor(Color.WHITE);
        tvOverview.setText(mOverview);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setNumStars(rate);
        ImageView ivImage =  (ImageView)  findViewById(R.id.ivMovieDetailImage);
        Picasso.with(context).load(mURL)
                    .fit()
                    .centerInside()
                    .placeholder(my_placeholder)
                    .error(my_placeholder_error)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(ivImage);
        if(mKey != null) {
            ivImage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Bundle dataBundle = new Bundle();
                    // replace backdrop path with youtube URL
                    dataBundle.putString("mId", mId);
                    dataBundle.putString("mName", mName);
                    dataBundle.putString("mKey", mKey);
                    Intent intent = new Intent(context, QuickPlayActivity.class);
                    intent.putExtras(dataBundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}
