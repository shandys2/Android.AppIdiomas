package com.dani.lingua;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class Activity2 extends Activity {

    VideoView video;
    Button jugar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_2);
        video=  findViewById(R.id.video);
        jugar = findViewById(R.id.jugar);

        Uri path = Uri.parse("android.resource://com.dani.lingua/"+R.raw.weed);
        video.setVideoURI(path);
        video.setMediaController(new MediaController(this));
        video.start();
        video.requestFocus();

        jugar.setOnClickListener(view -> {
            Intent myIntent = new Intent(Activity2.this, MainActivity.class);
            //myIntent.putExtra("key", value); //Optional parameters
            Activity2.this.startActivity(myIntent, ActivityOptions.makeSceneTransitionAnimation(Activity2.this).toBundle());
        });

    }
}