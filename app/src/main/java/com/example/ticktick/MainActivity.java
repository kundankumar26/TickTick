package com.example.ticktick;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar timerSeekbar;
    TextView timerTextview;
    Button timerButton;
    CountDownTimer countdownTimer;
    boolean isTimerActive = false;
    boolean timesUp = false;

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        String secondString = Integer.toString(seconds);

        if(seconds < 10){
            secondString = "0" + seconds;
        }

        secondString = minutes + ":" + secondString;
        timerTextview.setText(secondString);
        timerSeekbar.setProgress(secondsLeft);
    }

    @SuppressLint("SetTextI18n")
    public void startTimer(View view){

        if(!isTimerActive) {
            isTimerActive = true;
            timerSeekbar.setEnabled(false);
            timerButton.setText("Stop");

            countdownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    Log.i(" Time left ", String.valueOf((int) millisUntilFinished));
                    int secondsLeft = (int) millisUntilFinished / 1000;
                    updateTimer(secondsLeft);
                }

                @Override
                public void onFinish() {
                    timesUp = true;
                    String completeMessage = "Time's Up";
                    MediaPlayer playMedia = MediaPlayer.create(getApplicationContext(), R.raw.metalsound);
                    playMedia.start();
                    timerTextview.setText(completeMessage);
                }
            }.start();
        }
        else{
            isTimerActive = false;
            timerSeekbar.setEnabled(true);
            timerButton.setText("Start");
            countdownTimer.cancel();
            if(timesUp) {
                timerTextview.setText("0:00");
                timesUp = false;
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekbar = findViewById(R.id.timerSeekBar);
        timerTextview = findViewById(R.id.timerTextview);
        timerButton = findViewById(R.id.timerButton);

        timerSeekbar.setMax(300);
        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}