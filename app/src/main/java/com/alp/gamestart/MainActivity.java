package com.alp.gamestart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Intent musicServiceIntent;
    Button start;
    Button setting;
    public  static MediaPlayer mediaPlayer;
    private boolean isMusicPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.startBtn);
        setting = findViewById(R.id.settingBtn);
        mediaPlayer = MediaPlayer.create(this, R.raw.musix);
        mediaPlayer.setLooping(true);


        if (isMusicPlaying && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGame();
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        if (!isMusicPlaying) {
            mediaPlayer.start();
            isMusicPlaying = true;
        }
    }
    private void openGame() {
        Intent intent = new Intent(MainActivity.this, gamecod.class);
        startActivity(intent);
    }

    private void openSettings() {
        Intent intent = new Intent(MainActivity.this, settings.class);
        startActivity(intent);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}