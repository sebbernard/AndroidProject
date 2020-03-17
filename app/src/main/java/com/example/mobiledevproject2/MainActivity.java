package com.example.mobiledevproject2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button confirm_event;
    RadioGroup radio_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        confirm_event = (Button)findViewById(R.id.confirm_event);
        confirm_event.setVisibility(View.INVISIBLE);
        radio_group = (RadioGroup)findViewById(R.id.event_list);

        RetrieveJson get = new RetrieveJson();
        AsyncTask<Integer, Void, JSONArray> resultTask = get.execute(); //We get the list of events
        try {
            JSONArray result = resultTask.get();
            radio_group.clearCheck();
            final RadioButton[] rb = new RadioButton[result.length()];
            for(int i=1; i<result.length(); i++){
                JSONObject objI = result.getJSONObject(i);
                rb[i]  = new RadioButton(getApplicationContext());
                String radioText = (String) objI.get("date") + ": " + (String) objI.get("description"); //We had a formatted deescription of the event to the radio group
                rb[i].setText(radioText);
                rb[i].setId(i+100);
                radio_group.addView(rb[i]);
            }
            confirm_event.setVisibility(View.VISIBLE); //Once the radio group is complete, we show the confirm button

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        confirm_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radio_group.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(selectedId);
                String radioText = (String) radioButton.getText();
                String dateString = radioText.substring(0,10);
                String monthString = dateString.substring(5,7);
                String dayString = dateString.substring(8,10);
                int month = Integer.parseInt(monthString);
                int day = Integer.parseInt(dayString);
                Date nowDate = new Date();
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(Calendar.getInstance().get(Calendar.YEAR), month, day, 0, 0, 0);
                Date sendDate = cal.getTime();
                if (sendDate.before(nowDate)){
                    sendDate.setYear(sendDate.getYear()+1);
                }
                TimerActivity.setTime(getApplicationContext(),sendDate.getTime() - nowDate.getTime());
                finish();
            }
        });

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}