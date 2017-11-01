package com.example.ohiostatefitleaderswear;

import android.app.Activity;
import android.content.Intent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends Activity implements SensorEventListener {

    private static final String TAG = "MainActivity";   // Tag for logger

    private TextView heartTextView;     // Text view for displaying heart rate
    private TextView stepsTextView;     // Text view for displaying step count since start of app

    private String userID;

    private SensorManager mSensorManager;   // Sensor manager for heart monitor
    private Sensor stepCounter;        // Step detector
    private Sensor heartRateSensor;     // Heart rate sensor
    private Sensor accelSensor;

    private Vector<Integer> heartRateData;  // Vector to store heart rate samples
    private Vector<Integer> stepData;       // Vector to store step counts
    private String accelData;           // Vector to store accel

    private Vector<String> heartRateReadingTimes;   // Vector to store heart rate timestamps
    private Vector<String> stepCountReadingTimes;   // Vector to store step count timestamps
    private Vector<String> accelReadingTimes;   // Vector to store step count timestamps

    private int initialStepCount = 0;       // Helper variable to get accurate step count

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initializations
        super.onCreate(savedInstanceState);

        heartRateData = new Vector<>();
        stepData = new Vector<>();
        heartRateReadingTimes = new Vector<>();
        stepCountReadingTimes = new Vector<>();
        accelReadingTimes = new Vector<>();

        Bundle bundle = getIntent().getExtras();

        try {
            userID = bundle.getString("userID");
        }catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
        }

        // For debugging purposes keep the screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // Set view
        setContentView(R.layout.my_layout);

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

        // Set up accelerometers
        accelSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        // Register listeners for heart rate and pedometer sensors
        mSensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "HR listener registered.");

        mSensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "SC listener registered.");

        mSensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "AS listener registered.");

        final Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sendData();
            }
        });

    }

    public void onSensorChanged(SensorEvent event) {
        String msg;
        Sensor sensor = event.sensor;
        long currTime;
        SimpleDateFormat sdf;

        if (event.values.length > 0) {

            switch (sensor.getType()){
                case Sensor.TYPE_STEP_COUNTER:
                    currTime = System.currentTimeMillis();
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                    stepCountReadingTimes.addElement(sdf.format(new Date(currTime)));

                    if (initialStepCount == 0){
                        initialStepCount = (int) event.values[0];
                    }

                    int stepsTaken = (int) event.values[0] - initialStepCount;
                    stepData.addElement(stepsTaken);
                    msg = String.valueOf(stepsTaken);
                    stepsTextView.setText(msg);
                    Log.d(TAG, "Steps Taken: " + String.valueOf(stepData.lastElement()));
                    break;
                case Sensor.TYPE_HEART_RATE:
                    currTime = System.currentTimeMillis();
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                    heartRateReadingTimes.addElement(sdf.format(new Date(currTime)));

                    heartRateData.addElement((int) event.values[0]);
                    msg = "" + (int) event.values[0];
                    heartTextView.setText(msg);
                    Log.d(TAG, "Heart Rate: " + String.valueOf(heartRateData.lastElement()));
                    break;
                case Sensor.TYPE_LINEAR_ACCELERATION:
                    currTime = System.currentTimeMillis();
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                    accelReadingTimes.addElement(sdf.format(new Date(currTime)));

                    float[] tempVector = {event.values[0], event.values[1], event.values[2]};
                    String newData = Arrays.toString(tempVector) + ",";

                    if (accelData == null) {
                        accelData = newData;
                    }
                    else {
                        accelData = accelData + newData;
                    }

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
        mSensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this, stepCounter);
        mSensorManager.unregisterListener(this, heartRateSensor);
        mSensorManager.unregisterListener(this, accelSensor);
    }

    public void sendData() {

        onStop();

        // URL to send data
        final String URL = "http://jazz.ece.drexel.edu/FitLeaders/submit.php";

        // Create hashmap with parameters
        Map<String,String> params = new HashMap<>();
        params.put("hrData",heartRateData.toString());
        params.put( "hrTimeData", heartRateReadingTimes.toString());
        params.put("stepData",stepData.toString());
        params.put( "stepTimeData", stepCountReadingTimes.toString());
        params.put("accelData", accelData.substring(0, accelData.length() - 2));
        params.put("accelTimeData", accelReadingTimes.toString());
        params.put("userID", userID);

        // Create a JSON Object
        final JSONObject jsonObj = new JSONObject(params);

        Log.d(TAG, "JSON Object: " + jsonObj.toString());

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest sr = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);

                // Switch to thank you Activity
                Intent i = new Intent(MainActivity.this, ThankYou.class);
                startActivity(i);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.getLocalizedMessage());
            }
        }){

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return jsonObj.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    Log.d(TAG, "Unsupported Encoding while trying to get the bytes of " + jsonObj.toString() + "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.add(sr);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged - accuracy: " + accuracy);
    }
}