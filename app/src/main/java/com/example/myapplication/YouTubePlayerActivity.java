package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class YouTubePlayerActivity extends Activity {
    private static Video video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        Log.i("YoutubePlayer", "YoutubePlayer created");

        Intent intent = getIntent();
        video = (Video) intent.getSerializableExtra("video");

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player_view);

        if (youTubePlayerView != null) {
            youTubePlayerView.getYouTubePlayerWhenReady(youTubePlayer -> {
                String videoId = video.getYouTubeID();
                Log.i("Youtube ID", videoId);
                youTubePlayer.cueVideo(videoId, 0);
                youTubePlayer.play();
            });
        }
    }
}
