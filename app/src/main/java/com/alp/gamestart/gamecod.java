package com.alp.gamestart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class gamecod extends AppCompatActivity {
    public static final int EASY_DIFFICULTY = 1;
    public static final int NORMAL_DIFFICULTY = 2;
    public static final int HARD_DIFFICULTY = 3;
    public static final int VERY_HARD_DIFFICULTY = 3;
    TextView timeeText;
    TextView scoreText;
    ImageView imageView1;

    ImageView imageView2;

    ImageView imagaView3;

    ImageView imageView4;

    ImageView imageView5;

    ImageView imageView6;

    ImageView imageView7;

    ImageView imageView8;

    ImageView imageView9;

    ImageView imageView10;

    ImageView imageView11;

    ImageView imageView12;

    ImageView[] imaggeArray;

    Handler handler;

    Runnable runnable;
    private int timeInSeconds = 10;
    private CountDownTimer timer;
    int score;
    private TextView saveText;
    private SharedPreferences sharedPreferences;
    private static final String PREFS_NAME = "LeaderboardPrefs";
    private static final String LAST_SCORE_KEY = "lastScore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamecod);
        score = 0;
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imagaView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);
        imageView7 = findViewById(R.id.imageView7);
        imageView8 = findViewById(R.id.imageView8);
        imageView9 = findViewById(R.id.imageView9);
        imageView10 = findViewById(R.id.imageView10);
        imageView11 = findViewById(R.id.imageView11);
        imageView12 = findViewById(R.id.imageView12);
        scoreText = (TextView) findViewById(R.id.scoreText);
        timeeText = findViewById(R.id.timeeText);
        imaggeArray = new ImageView[]{imageView1, imageView2, imagaView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9, imageView10, imageView11, imageView12};
        hideImages();
        saveText = findViewById(R.id.saveText);
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int lastScore = getLastScore();
        saveText.setText("Last Score: " + lastScore);
        if (score % 10 == 0) {
            timeInSeconds += 10;
            updateTimer();
            saveLastScore(score);
        }
        startTimer();
        // imageClicked metodunu çağır

        // Ana layout'a tıklama işleyici ekle
        findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyAreaClicked(v);
            }
        });
        // Diğer imageView'ler için tıklama işleyicilerini belirtin
        for (ImageView imageView : imaggeArray) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageClicked(v);
                }
            });
        }
    }
    public void emptyAreaClicked(View view) {
        // Boş alana tıklanınca -5 puan cezası ver
        score -= 5;
        if (score < 0) {
            score = 0;
        }
        scoreText.setText("Score : " + score);
    }


    public void imageClicked(View view) {
        ImageView clickedImageView = (ImageView) view;
        boolean imageIsVisible = clickedImageView.getVisibility() == View.VISIBLE;

        // Eğer tıklanan görüntü görünürse, skoru artır ve diğer işlemleri yap
        if (imageIsVisible) {
            skorart(view);
        } else {
            // Eğer tıklanan görüntü görünmüyorsa -5 puan cezası ver
            score -= 5;
            if (score < 0) {
                score = 0;
            }
            scoreText.setText("Score : " + score);
        }
    }


    private void saveLastScore(int score) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LAST_SCORE_KEY, score);
        editor.apply();
    }

    private int getLastScore() {
        return sharedPreferences.getInt(LAST_SCORE_KEY, 0);
    }

    public void skorart(View view) {
        score++;
        scoreText.setText("Score : " + score);
        if (score % 10 == 0) {
            timeInSeconds += 10;
            updateTimer();
            resetTimer();
            saveLastScore(score);

        }
    }

    private void startTimer() {
        timer = new CountDownTimer(timeInSeconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeInSeconds = (int) millisUntilFinished / 1000;
                updateTimer();
            }

            @Override
            public void onFinish() {
                // Timer finished, handle here
                timeeText.setText("Time off!");
                for (ImageView image : imaggeArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                hideImages();

                AlertDialog.Builder alert = new AlertDialog.Builder(gamecod.this);
                alert.setTitle("Restart?");
                alert.setMessage("Are you sure to restart the game?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(gamecod.this, "Game over!", Toast.LENGTH_LONG).show();
                    }
                });
                alert.show();

            }
        }.start();
    }

    private void resetTimer() {
        timer.cancel();
        startTimer();
    }

    private void updateTimer() {
        timeeText.setText("Time: " + timeInSeconds + "s");
        scoreText.setText("Score: " + score);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }


    public void hideImages() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image : imaggeArray) {
                    image.setVisibility(View.INVISIBLE);
                }
                handler.postDelayed(this, getDelayForDifficulty()); // Zorluk seviyesine göre gecikmeyi ayarla
                showRandomImage();
            }
        };
        handler.post(runnable);
    }

    private int getDelayForDifficulty() {
        int difficulty = getCurrentDifficulty(); // Şu anki zorluk seviyesini al
        switch (difficulty) {
            case EASY_DIFFICULTY:
                return 1000; // 1 saniye
            case NORMAL_DIFFICULTY:
                return 800; // 0.5 saniye
            case HARD_DIFFICULTY:
                return 700; // 0.3 saniye
            default:
                return 2000; // Varsayılan olarak 1 saniye
        }
    }
    private int getCurrentDifficulty() {
        // Burada kullanıcının seçtiği zorluk seviyesini döndüren bir fonksiyon
        return settings.getCurrentDifficulty();

    }

    private void showRandomImage () {
        Random rnd = new Random();
        int i = rnd.nextInt(12);
        imaggeArray[i].setVisibility(View.VISIBLE);
    }
}