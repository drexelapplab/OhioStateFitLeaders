package com.example.maggiemulhern.ohiostatefitleaders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Brandon on 12/12/17.
 */

public class SplashActivity extends AppCompatActivity {

    public static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

        if(pref.getBoolean("LoggedIn", false)){
            Intent i = new Intent(this, TabbedActivity.class);
            startActivity(i);
            finish();
        }
        else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}