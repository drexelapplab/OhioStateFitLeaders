package com.example.maggiemulhern.ohiostatefitleaders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.lang.String;


public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        final Button btn = findViewById(R.id.LogIn);
        btn.setEnabled(false);
        final EditText emailValidate = findViewById(R.id.EmailEntry);
        final String emailPattern = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

        emailValidate.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String email = emailValidate.getText().toString();
                if (email.matches(emailPattern)) {
                    btn.setEnabled(true);
                    btn.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            Intent i = new Intent(LogIn.this,
                                    SurveyActivity.class);
                            // Set certain emails to be put through and others not
                            startActivity(i);
                        }

                    });
                }
            }



            @Override
            public void afterTextChanged(Editable editable) {
                String email = emailValidate.getText().toString();

                if (email.matches(emailPattern) && editable.length() > 0) {
                    btn.setEnabled(true);
                    btn.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            Intent i = new Intent(LogIn.this,
                                    SurveyActivity.class);
                            startActivity(i);
                        }

                    });
                }
            }
    });
    }
}