package com.example.maggiemulhern.ohiostatefitleaders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.webkit.WebView;
import android.webkit.WebSettings;

public class SurveyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        WebView myWebView = findViewById(R.id.Survey);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("https://osu.az1.qualtrics.com/jfe/form/SV_6KKyV9Fkrm0tygl");

        Button btn = findViewById(R.id.SurveyContinue);
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
