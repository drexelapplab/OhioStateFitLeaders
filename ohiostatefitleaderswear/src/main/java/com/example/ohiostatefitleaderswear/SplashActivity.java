package com.example.ohiostatefitleaderswear;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.widget.TextView;

public class SplashActivity extends WearableActivity {

    public static final String TAG = "SplashActivity_Watch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref_Watch", 0); // 0 - for private mode

        String loginStatusString = "Watch Logged In: " + String.valueOf(pref.getBoolean("LoggedIn_Watch", false));

        Log.d(TAG, loginStatusString);

        if(pref.getBoolean("LoggedIn_Watch", false) == false){
            Log.d(TAG, "Going to ActivateScreen");
            Intent i = new Intent(this, Activate.class);
            startActivity(i);
            finish();
        }
        else {
            Log.d(TAG, "Going to ActivateScreen");
            Intent intent = new Intent(this, StartWorkout.class);
            startActivity(intent);
            finish();
        }
    }
}
