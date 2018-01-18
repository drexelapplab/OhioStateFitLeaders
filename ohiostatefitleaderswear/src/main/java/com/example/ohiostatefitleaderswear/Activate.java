package com.example.ohiostatefitleaderswear;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Activate extends WearableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate);

        // Enables Always-on
        setAmbientEnabled();

        final EditText userID = findViewById(R.id.userIDInput);
        final Button btn = findViewById(R.id.startButton);
        btn.setEnabled(false);

//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        // Add the buttons
//        builder.setTitle("UserID Error");
//        builder.setMessage("User ID must be 6-8 characters");
//
//        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // User clicked OK button
//            }
//        });
//
//        AlertDialog dialog = builder.create();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode

        if(pref.getBoolean("LoggedIn_Watch", true)){
            Intent i = new Intent(this, StartWorkout.class);
            startActivity(i);
            finish();
        }

        userID.addTextChangedListener(new TextWatcher() {
                                          @Override
                                          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                          }

                                          @Override
                                          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                          }

                                          @Override
                                          public void afterTextChanged(Editable editable) {
                                              if ((userID.getText().length() > 5) && (userID.getText().length() < 9)) {
                                                  btn.setEnabled(true);
                                              }
                                              else {
                                                  btn.setEnabled(false);
                                              }
                                          }
                                      });

        btn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("LoggedIn_Watch", true);
                editor.apply();

                Intent i = new Intent(Activate.this, StartWorkout.class);
                i.putExtra("userID", userID.getText().toString());
                startActivity(i);
            }

        });

    }
}
