package com.example.projectskipsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    MediaPlayer audioBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splashscreen();
    }

    public void splashscreen(){

        audioBackground = MediaPlayer.create(this, R.raw.audio);

        audioBackground.setVolume(1, 1);

        audioBackground.start();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Tostarted = new Intent(MainActivity.this,GetStarted.class);
                startActivity(Tostarted);
                finish();
            }
        },1500); //2000 ms = 2 detik
    }
}
