package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

import static com.codepath.flickster.R.drawable.my_placeholder;
import static com.codepath.flickster.R.drawable.my_placeholder_error;

/**
 * Created by hkanekal on 3/7/2017.
 */

public class MovieRecyclerAdapter extends
        RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder>{
    // Store a member variable for the contacts
    private ArrayList<Movie> mMovies;
    // Store the context for easy access
    private static Context mContext;

    @Override
    public MovieRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View movieListView = inflater.inflate(R.layout.item_movie, parent, false);
        // Return a new holder instance
        ViewHolder movieViewHolder = new ViewHolder(movieListView);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieRecyclerAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Movie movie = mMovies.get(position);
        // Set item views based on your views and data model
        TextView tvTitle =  holder.tvTitle;
        TextView tvOverview = holder.tvOverview;
        tvTitle.setTextColor(Color.YELLOW);
        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setTextColor(Color.GREEN);
        tvOverview.setText(movie.getOverView());
        //Log.d("DEBUG","Recyc MT-> "+movie.getOriginalTitle());
        // Switch images between Poster for portrait and Backdrop for landscape
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext()).load(movie.getPosterPath())
                    .fit()
                    .centerInside()
                    .placeholder(my_placeholder)
                    .error(my_placeholder_error)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(holder.ivImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // find image view // clear out image if set
            Picasso.with(getContext()).load(movie.getBackdropPath())
                    .fit()
                    .centerInside()
                    .placeholder(my_placeholder)
                    .error(my_placeholder_error)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(holder.ivImage);
        }
    }

    private Movie getItem(int position) {
        return mMovies.get(position);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    // Easy access to the context object in the recyclerview
    private static Context getContext() {
        return mContext;
    }
    // Pass in the contact array into the constructor
    public MovieRecyclerAdapter(Context context, ArrayList<Movie> movies) {
        mMovies = movies;
        mContext = context;
        //Log.d("DEBUG","Adapt -> "+mMovies.toString());
    }
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTitle;
        public TextView tvOverview;
        public ImageView ivImage;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            // find image view // clear out image if set
            int orientation = getContext().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                ivImage = (ImageView) itemView.findViewById(R.id.ivMovieImage);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ivImage = (ImageView) itemView.findViewById(R.id.ivMovieImageLand);
            }
            ivImage.setImageResource(0);
        }
    }
}
