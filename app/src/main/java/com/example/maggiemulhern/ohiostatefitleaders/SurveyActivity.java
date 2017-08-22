package com.example.maggiemulhern.ohiostatefitleaders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Button btn = (Button) findViewById(R.id.SurveyContinue);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i  = new Intent(SurveyActivity.this,
                        ScheduleInfo.class);
                startActivity(i);
            }

        });
    }
}
