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
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONObject;

public class MainActivity extends Activity implements SensorEventListener {

    private static final String TAG = "MainActivity";   // Tag for logger
    private TextView heartTextView;     // Text view for displaying heart rate

    private String userID;

    private SensorManager mSensorManager;   // Sensor manager for heart monitor
    private Sensor heartRateSensor;     // Heart rate sensor

    private Vector<Integer> heartRateData;  // Vector to store heart rate samples

    private Vector<String> heartRateReadingTimes;   // Vector to store heart rate timestamps

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Initializations
        super.onCreate(savedInstanceState);

        heartRateData = new Vector<>();
        heartRateReadingTimes = new Vector<>();

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

        // Register listeners for heart rate and pedometer sensors
        mSensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "HR listener registered.");

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
            if (sensor.getType() == Sensor.TYPE_HEART_RATE) {
                currTime = System.currentTimeMillis();
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                heartRateReadingTimes.addElement(sdf.format(new Date(currTime)));

                heartRateData.addElement((int) event.values[0]);
                msg = "" + (int) event.values[0];
                heartTextView.setText(msg);
                Log.d(TAG, "Heart Rate: " + String.valueOf(heartRateData.lastElement()));
            }
        }
    }

    protected void onResume() {
        super.onResume();

        mSensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this, heartRateSensor);
    }

    public void sendData() {

        onStop();

        // URL to send data
        final String URL = "http://jazz.ece.drexel.edu/FitLeaders/submit.php";

        // Create hashmap with parameters
        Map<String,String> params = new HashMap<>();
        params.put("hrData",heartRateData.toString());
        params.put( "hrTimeData", heartRateReadingTimes.toString());
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