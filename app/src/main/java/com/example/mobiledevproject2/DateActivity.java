package com.example.mobiledevproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class DateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        final DatePicker datepicker = (DatePicker)findViewById(R.id.date_picker);
        final TimePicker timepicker = (TimePicker)findViewById(R.id.time_picker);

        Button button2 = (Button)findViewById(R.id.button_cancel); //Cancel button that sends back to the main page
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });

        Button button3 = (Button)findViewById(R.id.button_confirm);
        button3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(datepicker.getYear(), datepicker.getMonth(), datepicker.getDayOfMonth(), timepicker.getHour(), timepicker.getMinute(), 0);
                Date sendDate = cal.getTime(); //This is the user's date
                Date nowDate = new Date(); //Today's date
                if (sendDate.before(nowDate)){
                    sendDate.setYear(Calendar.getInstance().get(Calendar.YEAR)+1); //If the user's date is earlier, we add a year
                }
                TimerActivity.setTime(getApplicationContext(), sendDate.getTime() - nowDate.getTime());
                finish(); //We go back to the main page
            }
        });
    }
}
