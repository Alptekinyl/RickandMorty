package com.alp.gamestart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class settings extends AppCompatActivity {
    private Button stopMusicButton;
    private MainActivity mainActivity;
    private Button normalDifficultyButton;
    private Button hardDifficultyButton;
    private Button veryHardDifficultyButton;
    private static int currentDifficulty = gamecod.NORMAL_DIFFICULTY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        stopMusicButton = findViewById(R.id.stopMusicBtn);
        normalDifficultyButton = findViewById(R.id.normalDifficultyBtn);
        hardDifficultyButton = findViewById(R.id.hardDifficultyBtn);
        veryHardDifficultyButton = findViewById(R.id.veryHardDifficultyBtn);

        normalDifficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setCurrentDifficulty(gamecod.NORMAL_DIFFICULTY);
                Intent intent = new Intent(settings.this, gamecod.class);
                startActivity(intent);
            }
        });

        hardDifficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setCurrentDifficulty(gamecod.HARD_DIFFICULTY);
                Intent intent = new Intent(settings.this, gamecod.class);
                startActivity(intent);
            }
        });

        veryHardDifficultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setCurrentDifficulty(gamecod.VERY_HARD_DIFFICULTY);
                Intent intent = new Intent(settings.this, gamecod.class);
                startActivity(intent);
            }
        });




        stopMusicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.mediaPlayer != null) {
                    if (MainActivity.mediaPlayer.isPlaying()) {
                        MainActivity.mediaPlayer.pause();
                    } else {
                        MainActivity.mediaPlayer.start();
                    }
                }
            }
        });
    }
    public static int getCurrentDifficulty() {
        return currentDifficulty;
    }

    public static void setCurrentDifficulty(int difficulty) {
        currentDifficulty = difficulty;
    }
}