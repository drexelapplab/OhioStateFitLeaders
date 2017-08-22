package com.example.maggiemulhern.ohiostatefitleaders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ScheduleInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_info);

        Button Calendar = (Button) findViewById(R.id.Calendar);
        Button WorkOut = (Button) findViewById(R.id.Workouts);
        Button DataButton = (Button) findViewById(R.id.Data);
        Calendar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ScheduleInfo.this,
                        Calendar.class);
                startActivity(i);
            }

        });


        WorkOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(ScheduleInfo.this,
                        WorkOut.class);
                startActivity(i);
            }

        });


        DataButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i  = new Intent(ScheduleInfo.this,
                            YourData.class);
                startActivity(i);
            }

        });
    }
}
