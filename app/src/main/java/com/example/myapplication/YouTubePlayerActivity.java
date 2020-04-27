package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class YouTubePlayerActivity extends Activity {
    private static Video video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        Intent intent = getIntent();
        video = (Video) intent.getSerializableExtra("video");

        Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + video.getYouTubeID()));
        youtubeIntent.putExtra("force_fullscreen", true);
        youtubeIntent.putExtra("finish_on_ended", true);

        startActivity(youtubeIntent);
    }
}
