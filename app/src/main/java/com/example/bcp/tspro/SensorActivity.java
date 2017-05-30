package com.example.bcp.tspro;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

import sensor.AccelerometerSensorHelper;
import sensor.SensorActionListener;
import sensor.SensorManagerHelper;

public class SensorActivity extends Activity implements SensorActionListener{
    private SensorManagerHelper helper;
    private Vibrator vibrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        vibrator= (Vibrator) getSystemService(VIBRATOR_SERVICE);
        helper = new AccelerometerSensorHelper(this, Sensor.TYPE_ACCELEROMETER);
        helper.setListener(this);
        Button mBtnShake=(Button)findViewById(R.id.btn_vibrator);
        mBtnShake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(300);
            }
        });
    }

    @Override
    public void onAction() {
        vibrator.vibrate(300);
    }

    @Override
    protected void onResume() {
        super.onResume();
        helper.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        helper.stop();
    }
}
