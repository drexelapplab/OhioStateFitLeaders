package com.example.maggiemulhern.ohiostatefitleaders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class pedometer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Put application specific code here.

        setContentView(R.layout.activity_pedometer);

        //initializeLogging();
        Button btn = findViewById(R.id.backButton);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(pedometer.this,
                        YourData.class);
                startActivity(i);
            }
        });
    }
}