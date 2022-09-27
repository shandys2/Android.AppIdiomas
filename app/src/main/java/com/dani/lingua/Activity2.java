package com.dani.lingua;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Activity2 extends Activity {

    VideoView video;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_2);
        video= (VideoView) findViewById(R.id.video);
        Uri path = Uri.parse("android.resource://com.dani.lingua/"+R.raw.weed);
        video.setVideoURI(path);
        video.setMediaController(new MediaController(this));
        System.out.println("Dura");
        System.out.println(video.getDuration());
        video.start();
        video.requestFocus();

    }
}
