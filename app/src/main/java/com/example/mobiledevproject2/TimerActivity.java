package com.example.mobiledevproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private static long mStartTimeInMillis = 0;

    private static TextView mTextViewCountDown;

    private static CountDownTimer mCountDownTimer;

    private static boolean mTimerRunning;
    private static boolean mTimerCreated = false;

    private static long mTimeLeftInMillis = mStartTimeInMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        Button button = findViewById(R.id.button_event);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(); //Forwards to the event list page
            }
        });

        Button button2 = findViewById(R.id.button_date);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDateActivity(); //Forwards to the manual date page
            }
        });

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        updateCountDownText(); //Initiates the countdown display
    }

    private void launchActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void launchDateActivity(){
        Intent intent = new Intent(this, DateActivity.class);
        startActivity(intent);
    }

    public static void setTime(Context context, long milliseconds) {
        if (mTimerCreated){
            pauseTimer(); //If the timer has been created, it is paused while it is changed to prevent conflict
        }
        mStartTimeInMillis = milliseconds;
        resetTimer(); //The display is reset
        startTimer(context); //The timer starts again or is created
    }

    private static void startTimer(final Context context) {
        mTimerCreated = true;
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText(); //The number is updated as well as the display
            }

            @Override
            public void onFinish() { //once it is done
                mTimerRunning = false;
                Toast.makeText(context, "The counter has reached 0 !!", Toast.LENGTH_LONG).show();
            }
        }.start();
        mTimerRunning = true;
    }

    private static void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
    }

    private static void resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis;
        updateCountDownText();
    }

    private static void updateCountDownText() { //We check how big the timer is and format accordingly
        Long days = (Long) (mTimeLeftInMillis / 1000) / 86400;
        Long hours = (Long) ((mTimeLeftInMillis / 1000) / 3600) % 24;
        Long minutes = (Long) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        Long seconds = (Long) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (days > 0){
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d:%02d", days, hours, minutes, seconds);
        }
        else if(hours > 0){
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, seconds);
        }
        else{
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d:%02d", minutes, seconds);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }
}
