package com.example.maggiemulhern.ohiostatefitleaders;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class WorkOut extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_out);

        Button Finish = findViewById(R.id.Finish);
        Finish.setEnabled(false);
        CheckBox Activity1 = findViewById((R.id.Activity1));
        CheckBox Activity2 = findViewById((R.id.Activity2));
        CheckBox Activity3 = findViewById((R.id.Activity3));
        CheckBox Activity4 = findViewById((R.id.Activity4));
        CheckBox Activity5 = findViewById((R.id.Activity5));

        if (Activity1.isChecked() && Activity2.isChecked() && Activity3.isChecked() && Activity4.isChecked() && Activity5.isChecked()) {
            Finish.setEnabled(true);
            Finish.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(WorkOut.this,
                            ScheduleInfo.class);
                    startActivity(i);
                }

            });
        }

    }
}
