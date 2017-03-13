package com.codepath.flickster.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.codepath.flickster.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
/**
 * Created by hkanekal on 3/11/2017.
 */

public class QuickPlayActivity extends YouTubeBaseActivity {

        public static final String YT_API_KEY = "AIzaSyA4bc4yZfIBjfVMTJtvZdQ3C1gmE78DJzg";
        public String mId, mName,mKey;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_quickplay);
            Bundle extras = getIntent().getExtras();
            mId = extras.getString("mId");
            mName = extras.getString("mName");
            mKey = extras.getString("mKey");
            YouTubePlayerView youTubePlayerView =
                    (YouTubePlayerView) findViewById(R.id.player);

            youTubePlayerView.initialize(YT_API_KEY,
                    new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            YouTubePlayer youTubePlayer, boolean b) {
                            youTubePlayer.loadVideo(mKey);
                        }
                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult youTubeInitializationResult) {
                            Toast.makeText(QuickPlayActivity.this, "Youtube Failed!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
