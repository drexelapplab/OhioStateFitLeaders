package com.example.ohiostatefitleaderswear;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;

public class ThankYou extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_thank_you);

        Button btn = findViewById(R.id.startOverButton);

        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent i = new Intent(ThankYou.this,
                        Activate.class);
                // Set certain emails to be put through and others not
                startActivity(i);
            }

        });

        // Enables Always-on
        setAmbientEnabled();
    }
}
