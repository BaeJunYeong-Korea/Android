package com.example.covid_test3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.os.CountDownTimer;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {
    private RadioButton rb_15min;
    private RadioButton rb_20min;
    private RadioButton rb_30min;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private Button mButtonReset;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = 900000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        RadioGroup rg_minute = findViewById(R.id.rg_minute);
        rb_15min = findViewById(R.id.rb_15min);
        rb_20min = findViewById(R.id.rb_20min);
        rb_30min = findViewById(R.id.rb_30min);
        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        mButtonReset = findViewById(R.id.button_reset);

        rg_minute.setOnCheckedChangeListener((radioGroup, i) -> {
            if(i == R.id.rb_15min){
                Toast.makeText(TimerActivity.this,"15분을 선택하셨습니다.",Toast.LENGTH_SHORT).show();
                mTimeLeftInMillis = 900000;
                resetTimer(900000);
            } else if(i == R.id.rb_20min){
                Toast.makeText(TimerActivity.this,"20분을 선택하셨습니다.",Toast.LENGTH_SHORT).show();
                mTimeLeftInMillis = 1200000;
                resetTimer(1200000);
            } else if (i == R.id.rb_30min) {
                Toast.makeText(TimerActivity.this,"30분을 선택하셨습니다.",Toast.LENGTH_SHORT).show();
                mTimeLeftInMillis = 1800000;
                resetTimer(1800000);
            }

        });

        mButtonStartPause.setOnClickListener(v -> {
            if (mTimerRunning) {
                pauseTimer();
            } else {
                startTimer();
            }
        });
        mButtonReset.setOnClickListener(v -> {
            if (rb_15min.isChecked() ){
                resetTimer(900000);
            } else if (rb_20min.isChecked()){
                resetTimer(1200000);
            } else if (rb_30min.isChecked()){
                resetTimer(1800000);
            } else {
                resetTimer (900000);
            }
        });
        updateCountDownText();
    }
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                mButtonStartPause.setText(R.string.start);
                mButtonStartPause.setVisibility(View.INVISIBLE);
                mButtonReset.setVisibility(View.VISIBLE);
            }
        }.start();
        mTimerRunning = true;
        mButtonStartPause.setText(R.string.pause);
        mButtonReset.setVisibility(View.INVISIBLE);
    }
    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
        mButtonStartPause.setText(R.string.start);
        mButtonReset.setVisibility(View.VISIBLE);
    }
    private void resetTimer(int i) {
        mTimeLeftInMillis = i;
        updateCountDownText();
        mButtonReset.setVisibility(View.INVISIBLE);
        mButtonStartPause.setVisibility(View.VISIBLE);
    }
    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        mTextViewCountDown.setText(timeLeftFormatted);
        if(minutes == 0 && seconds == 0){
            Toast.makeText(TimerActivity.this,"선택한 시간이 끝났습니다.",Toast.LENGTH_SHORT).show();
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(300);
        }
    }
}