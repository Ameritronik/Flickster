package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.codepath.flickster.R.drawable.my_placeholder;
import static com.codepath.flickster.R.drawable.my_placeholder_error;

/**
 * Created by hkanekal on 3/5/2017.
 */

public class MovieArrayAdapter extends ArrayAdapter<Movie> {
    public MovieArrayAdapter(Context context, List<Movie> movies){
        super(context, android.R.layout.simple_list_item_1, movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // get the data item for position
        Movie movie = getItem(position);

        // check if existing view is being used or reused
        if(convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
        }

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);
        tvTitle.setTextColor(Color.YELLOW);
        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setTextColor(Color.GREEN);
        tvOverview.setText(movie.getOverView());

        ImageView ivImage;

        // Switch images between Poster for portrait and Backdrop for landscape
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // find image view // clear out image if set
            ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            ivImage.setImageResource(0);
            Picasso.with(getContext()).load(movie.getPosterPath())
                    .fit()
                    .centerInside()
                    .placeholder(my_placeholder)
                    .error(my_placeholder_error)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(ivImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // find image view // clear out image if set
            ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImageLand);
            ivImage.setImageResource(0);
            Picasso.with(getContext()).load(movie.getBackdropPath())
                    .fit()
                    .centerCrop()
                    .placeholder(my_placeholder)
                    .error(my_placeholder_error)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(ivImage);
        }

        // return the data set
        return convertView;
    }
}
