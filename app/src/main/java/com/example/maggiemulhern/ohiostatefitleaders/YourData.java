package com.example.maggiemulhern.ohiostatefitleaders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class YourData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_data);

        Button Back = (Button) findViewById(R.id.BackButton);
        Back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i  = new Intent(YourData.this,
                        ScheduleInfo.class);
                startActivity(i);
            }

        });
        Button btn = findViewById(R.id.Steps);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i  = new Intent(YourData.this,
                        pedometer.class);
                startActivity(i);
            }

        });
    }
}
