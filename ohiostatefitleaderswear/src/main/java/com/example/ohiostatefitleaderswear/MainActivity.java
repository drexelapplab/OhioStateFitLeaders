package com.example.ohiostatefitleaderswear;

import android.app.Activity;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.view.View;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.content.res.Configuration;
import android.content.Context;
import android.hardware.SensorEvent;
import java.util.Date;
import java.text.DateFormat;
import android.util.Log;
import android.hardware.SensorEventListener;


public class MainActivity extends Activity implements SensorEventListener {

    TextView textView;
    private SensorManager SensorManager;
    private Sensor SensorStepCounter;
    private Sensor StepDetector;
    TextView mTextViewHeart;
    private SensorManager mSensorManager;
    private Sensor mHeartRateSensor;
    private SensorEventListener sensorEventListener;
    String TAG;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.rect_activity_main);
        TextView Time = findViewById(R.id.Time);
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        String format = currentDateTimeString.replaceAll("[\r\n]+", " ");
        Time.setText(format);

        textView = findViewById(R.id.Steps);
        SensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);
        SensorStepCounter = SensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        StepDetector = SensorManager
                .getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
    }

    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }

        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            textView.setText("Step Counter Detected : " + value);
        } else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            // For test only. Only allowed value is 1.0 i.e. for step taken
            textView.setText("Step Detector Detected : " + value);
        }

        mTextViewHeart = (TextView) findViewById(R.id.HeartRate);
        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "LISTENER REGISTERED.");
        mTextViewHeart.setText("Something here");


        mSensorManager.registerListener(sensorEventListener, mHeartRateSensor, mSensorManager.SENSOR_DELAY_FASTEST);

        //Heart Rate
        if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            String msg = "" + (int)event.values[0];
            mTextViewHeart.setText(msg);
            Log.d(TAG, msg);
        }
        else
            Log.d(TAG, "Unknown sensor type");

    }

    protected void onResume() {

        super.onResume();

        SensorManager.registerListener(this, SensorStepCounter,

                SensorManager.SENSOR_DELAY_FASTEST);
        SensorManager.registerListener(this, StepDetector,

                SensorManager.SENSOR_DELAY_FASTEST);

    }


        protected void onStop() {
            super.onStop();
            SensorManager.unregisterListener(this, SensorStepCounter);
            SensorManager.unregisterListener(this, StepDetector);
        }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //do nothing???
        Log.d(TAG, "onAccuracyChanged - accuracy: " + accuracy);
    }

    /* new Handler().postDelayed(new Runnable() {
        public void run() {
            // call JSON methods here
            new MyAsyncTask().execute();
        }
    }, 60000);
*/
}
