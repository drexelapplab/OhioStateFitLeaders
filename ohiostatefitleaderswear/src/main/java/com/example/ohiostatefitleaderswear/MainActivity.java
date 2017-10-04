package com.example.ohiostatefitleaderswear;

import android.app.Activity;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import java.util.Date;
import java.text.DateFormat;
import java.util.Vector;

import android.util.Log;


public class MainActivity extends Activity implements SensorEventListener {

    TextView heartTextView;     // Text view for displaying heart rate
    TextView stepsTextView;     // Text view for displaying step count since start of app

    private SensorManager mSensorManager;   // Sensor manager for heart monitor

    private Sensor stepCounter;        // Step detector
    private Sensor heartRateSensor;     // Heart rate sensor

    private Vector<Integer> heartRateData;
    private Vector<Integer> stepData;
    private int initialStepCount = 0;

    String TAG;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initalizations
        super.onCreate(savedInstanceState);
        heartRateData = new Vector<>();
        stepData = new Vector<>();

        // For debugging purposes keep the screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Set view
        setContentView(R.layout.rect_activity_main);

        // Display time and date for the header
        TextView Time = findViewById(R.id.Time);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        String format = currentDateTimeString.replaceAll("[\r\n]+", " ");
        Time.setText(format);

        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));

        // Set up heart rate monitor
        heartTextView = findViewById(R.id.HeartRate);
        heartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        heartTextView.setText("Waiting...");

        // Set up pedometer
        stepsTextView = findViewById(R.id.Steps);
        stepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        stepsTextView.setText("Waiting...");

        // Register listeners for heart rate and pedometer sensors
        mSensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "LISTENER REGISTERED.");
    }

    public void onSensorChanged(SensorEvent event) {
        String msg;
        Sensor sensor = event.sensor;

        if (event.values.length > 0) {

            switch (sensor.getType()){
                case Sensor.TYPE_STEP_COUNTER:
                    if (initialStepCount == 0){
                        initialStepCount = (int) event.values[0];
                    }

                    int stepsTaken = (int) event.values[0] - initialStepCount;
                    stepData.addElement((int) event.values[0]);
                    msg = String.valueOf(stepsTaken);
                    stepsTextView.setText(msg);
                    Log.d(TAG, "Steps Taken: " + String.valueOf(stepData.lastElement()));
                    break;
                case Sensor.TYPE_HEART_RATE:
                    heartRateData.addElement((int) event.values[0]);
                    msg = "" + (int) event.values[0];
                    heartTextView.setText(msg);
                    Log.d(TAG, "Heart Rate: " + String.valueOf(heartRateData.lastElement()));
                    break;
                default:
                    Log.d(TAG, "Unknown sensor type");
                    break;
            }
        }
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this, stepCounter);
        mSensorManager.unregisterListener(this, heartRateSensor);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged - accuracy: " + accuracy);
    }
}