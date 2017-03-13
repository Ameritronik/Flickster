package com.codepath.flickster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.flickster.R;
import com.codepath.flickster.activities.MovieDetailActivity;
import com.codepath.flickster.activities.QuickPlayActivity;
import com.codepath.flickster.models.Movie;
import com.codepath.flickster.models.YouTubeURL;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.codepath.flickster.R.drawable.my_placeholder;
import static com.codepath.flickster.R.drawable.my_placeholder_error;

/**
 * Created by hkanekal on 3/8/2017.
 */

public class ComplexRecyclerViewAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder>{
    // Store a member variable for the contacts
    private ArrayList<Movie> mMovies;
    ArrayList<YouTubeURL> youTubeUrls = new ArrayList<YouTubeURL>();
    private static int DELAY = 10;
    // Store the context for easy access
    private static Context mContext;
    private final int POP = 0, NOP = 1, SMPL =2; // Pop = popular Nop = Not popular

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder movieViewHolder;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        switch (viewType) {
            case POP:
                View v1 = inflater.inflate(R.layout.item_popmovie, parent, false);
                movieViewHolder = new PopViewHolder(v1);
                break;
            case NOP:
                View v2 = inflater.inflate(R.layout.item_nonpopmovie, parent, false);
                movieViewHolder = new NopViewHolder(v2);
                break;
            default:
                View v = inflater.inflate(R.layout.item_movie, parent, false);
                movieViewHolder = new SimpleViewHolder(v);
                break;
        }
        // Return a new holder instance
        return movieViewHolder;
    }

    private void configureDefaultViewHolder(SimpleViewHolder holder, int position) {
        // Get the data model based on position
        final Movie movie = mMovies.get(position);
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
        // Now look for any key click on a movie for detailed screen view
        // need to pass the following
        // ivMovieDetailImage tvTitle releaseDate ratingBar tvOverview
        ImageView movieClicked = (ImageView) holder.ivImage;
        movieClicked.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                // replace backdrop path with youtube URL
                dataBundle.putInt("mRate",(int) ((movie.getRateing()/2.0)+0.5));
                dataBundle.putString("mURL", movie.getBackdropPath());
                dataBundle.putString("mTitle", movie.getOriginalTitle());
                dataBundle.putString("mRelDate",movie.getReleaseDate());
                dataBundle.putString("mOverview",movie.getOverView());
                Intent intent = new Intent(mContext,MovieDetailActivity.class);
                intent.putExtras(dataBundle);
                mContext.startActivity(intent);
            }
        });
    }

    private void configureViewHolder1(PopViewHolder holder, final int position) {
        // Get the data model based on position
        final Movie movie = mMovies.get(position);
        // Set item views based on your views and data model
        ImageView movieClicked = (ImageView) holder.ivPopImage;
        TextView tvTitle =  holder.tvPopTitle;
        TextView tvOverview = holder.tvPopOverview;
        tvTitle.setTextColor(Color.WHITE);
        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setTextColor(Color.GREEN);
        tvOverview.setText(movie.getOverView());
        // Switch images between Poster for portrait and Backdrop for landscape
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext()).load(movie.getPosterPath())
                    .fit()
                    .centerInside()
                    .placeholder(my_placeholder)
                    .error(my_placeholder_error)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(holder.ivPopImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // find image view // clear out image if set
            Picasso.with(getContext()).load(movie.getBackdropPath())
                    .fit()
                    .centerInside()
                    .placeholder(my_placeholder)
                    .error(my_placeholder_error)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(holder.ivPopImage);
        }
        // Now look for any key click on a movie for detailed screen view
        // need to pass the following
        // ivMovieDetailImage tvTitle releaseDate ratingBar tvOverview movidId
        movieClicked.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                // replace backdrop path with youtube URL
                dataBundle.putString("mId", movie.getMovieId());
                dataBundle.putString("mName", movie.getOriginalTitle());
                String rmKey = getYouTubeKey(position);
                if(rmKey == null) { rmKey = getYouTubeKey(position); } // retry
                dataBundle.putString("mKey", rmKey);
                if(rmKey != null ) {
                    Intent intent = new Intent(mContext, QuickPlayActivity.class);
                    intent.putExtras(dataBundle);
                    mContext.startActivity(intent);
                }
            }
        });
        tvTitle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                // replace backdrop path with youtube URL
                dataBundle.putInt("mRate",(int) ((movie.getRateing()/2.0)+0.5));
                dataBundle.putString("mURL", movie.getBackdropPath());
                dataBundle.putString("mTitle", movie.getOriginalTitle());
                dataBundle.putString("mRelDate",movie.getReleaseDate());
                dataBundle.putString("mOverview",movie.getOverView());
                dataBundle.putString("mId", null);
                dataBundle.putString("mName", null);
                dataBundle.putString("mKey", null);
                Intent intent = new Intent(mContext,MovieDetailActivity.class);
                intent.putExtras(dataBundle);
                mContext.startActivity(intent);
            }
        });
    }

    public String getYouTubeKey(int position) {
        String uTubeKey = "";
        final boolean[] urlListAdded = {false};
        // Get the data model based on position
        final Movie movie = mMovies.get(position);
        // Assemble You Tube URL
        final String[] uTubeURL1 = {"https://api.themoviedb.org/3/movie/"};
        String uTubeURL2 = movie.getMovieId();
        String uTubeURL3 = "/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        String uTubeURL = uTubeURL1[0] +uTubeURL2+uTubeURL3;
        final OkHttpClient client = new OkHttpClient();
        Request reqUTubeURLs = new okhttp3.Request.Builder().url(uTubeURL).build();
        client.newCall(reqUTubeURLs).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                String responseData = response.body().string();
                try {
                    JSONObject jindata = new JSONObject(responseData);
                    JSONArray movieJsonUTubeResults = new JSONArray();
                    movieJsonUTubeResults = jindata.getJSONArray("results");
                    youTubeUrls.addAll(YouTubeURL.fromJSONArray(movieJsonUTubeResults));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        int waitcount = 0;
        while((youTubeUrls.size() == 0) &&(waitcount < 10000000)) {
            waitcount++;
        }
        for(int x=0;x<youTubeUrls.size();x++) {
            if (youTubeUrls.get(x).getYTKey() != null) {
                uTubeKey = youTubeUrls.get(x).getYTKey();
            } else { uTubeKey = "NoKey"; Log.d("DEBUG","CRVA-> empty"+ uTubeKey);}
        }
        for (int k =0; k< youTubeUrls.size(); k++) {
            youTubeUrls.remove(k);
        }
        youTubeUrls = new ArrayList<YouTubeURL>();
        return uTubeKey;
    }

    private void configureViewHolder2(NopViewHolder holder, final int position) {
        // Get the data model based on position
        final Movie movie = mMovies.get(position);
        // Set item views based on your views and data model
        TextView tvTitle =  holder.tvNopTitle;
        TextView tvOverview = holder.tvNopOverview;
        tvTitle.setTextColor(Color.YELLOW);
        tvTitle.setText(movie.getOriginalTitle());
        tvOverview.setTextColor(Color.YELLOW);
        tvOverview.setText(movie.getOverView());
        // Switch images between Poster for portrait and Backdrop for landscape
        int orientation = getContext().getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            Picasso.with(getContext()).load(movie.getPosterPath())
                    .fit()
                    .centerInside()
                    .placeholder(my_placeholder)
                    .error(my_placeholder_error)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(holder.ivNopImage);
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // find image view // clear out image if set
            Picasso.with(getContext()).load(movie.getBackdropPath())
                    .fit()
                    .centerInside()
                    .placeholder(my_placeholder)
                    .error(my_placeholder_error)
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(holder.ivNopImage);
        }
        tvTitle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Bundle dataBundle = new Bundle();
                // replace backdrop path with youtube URL
                dataBundle.putInt("mRate",(int) ((movie.getRateing()/2.0)+0.5));
                dataBundle.putString("mURL", movie.getBackdropPath());
                dataBundle.putString("mTitle", movie.getOriginalTitle());
                dataBundle.putString("mRelDate",movie.getReleaseDate());
                dataBundle.putString("mOverview",movie.getOverView());
                dataBundle.putString("mId", movie.getMovieId());
                dataBundle.putString("mName", movie.getOriginalTitle());
                String rmKey = getYouTubeKey(position);
                if(rmKey == null) { rmKey = getYouTubeKey(position); } // retry
                dataBundle.putString("mKey", rmKey);
                if(rmKey != null ) {
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    intent.putExtras(dataBundle);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case POP:
                PopViewHolder vh1 = (PopViewHolder) holder;
                configureViewHolder1(vh1, position);
                break;
            case NOP:
                NopViewHolder vh2 = (NopViewHolder) holder;
                configureViewHolder2(vh2, position);
                break;
            default:
                SimpleViewHolder vh = (SimpleViewHolder) holder;
                configureDefaultViewHolder(vh, position);
                break;
        }
    }

    private Movie getItem(int position) {
        return mMovies.get(position);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    @Override
    public int getItemViewType(int position) {
        // Here check for popularity and return one of the pop/nop types
        int PopNop=SMPL;
        float popularity = mMovies.get(position).getRateing();
        //Log.d("DEBUG","CH1-> "+popularity);
        if(popularity > 5.0 ) {
            PopNop = POP;
        } else if ((popularity < 5.0) && (popularity > 2.0)) {
            PopNop = NOP;
        } else if (popularity < 2.0){
            PopNop = SMPL;
        }
        return PopNop;
    }

    // Easy access to the context object in the recyclerview
    private static Context getContext() {
        return mContext;
    }
    // Pass in the contact array into the constructor
    public ComplexRecyclerViewAdapter(Context context, ArrayList<Movie> movies) {
        mMovies = movies;
        mContext = context;
        //Log.d("DEBUG","Adapt -> "+mMovies.toString());
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTitle;
        public TextView tvOverview;
        public ImageView ivImage;
        private RatingBar ratingBar;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public SimpleViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
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

    public static class PopViewHolder extends RecyclerView.ViewHolder{
        private TextView tvPopTitle;
        private TextView tvPopOverview;
        private ImageView ivPopImage;
        private RatingBar ratingBar;

        public PopViewHolder(View itemView) {
            super(itemView);
            tvPopTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvPopOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            // find image view // clear out image if set
            int orientation = getContext().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                ivPopImage = (ImageView) itemView.findViewById(R.id.ivMovieImagePop);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ivPopImage = (ImageView) itemView.findViewById(R.id.ivMovieImagePopLand);
            }
            ivPopImage.setImageResource(0);
        }

        public TextView getPopTitle(){
            return tvPopTitle;
        }

        public TextView getPopOverview() {
            return tvPopOverview;
        }

        public ImageView getPopImage() {
            return ivPopImage;
        }

        public void setPopTitle(TextView PopTitle){
            this.tvPopTitle = PopTitle;
        }

        public void setPopOverview(TextView PopOverview) {
            this.tvPopOverview = PopOverview;
        }

        public void setPopImage(ImageView PopImage) {
            this.ivPopImage = PopImage;
        }
    }

    public static class NopViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNopTitle;
        private TextView tvNopOverview;
        private ImageView ivNopImage;
        private RatingBar ratingBar;

        public NopViewHolder(View itemView) {
            super(itemView);
            tvNopTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvNopOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
            // find image view // clear out image if set
            int orientation = getContext().getResources().getConfiguration().orientation;
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                ivNopImage = (ImageView) itemView.findViewById(R.id.ivMovieImageNop);
            } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                ivNopImage = (ImageView) itemView.findViewById(R.id.ivMovieImageNopLand);
            }
            //ivNopImage.setImageResource(0);
        }

        public TextView getNopTitle(){
            return tvNopTitle;
        }

        public TextView getNopOverview() {
            return tvNopOverview;
        }

        public ImageView getNopImage() {
            return ivNopImage;
        }

        public void setNopTitle(TextView NopTitle){
            this.tvNopTitle = NopTitle;
        }

        public void setNopOverview(TextView NopOverview) {
            this.tvNopOverview = NopOverview;
        }

        public void setNopImage(ImageView NopImage) {
            this.ivNopImage = NopImage;
        }
    }

}

