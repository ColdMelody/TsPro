package com.example.bcp.tspro;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import service.SimpleService;
import service.SimpleService.MyBinder;

public class ServiceActivity extends Activity implements View.OnClickListener {

    private boolean mBound = false;
    private SimpleService mService;
    private Button mBtnService;
    private Button mBtnStop;
    private Button mBtnGetInfo;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBinder myBinder = (MyBinder) service;
            mService = (SimpleService) myBinder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        mBtnService = (Button) findViewById(R.id.btn_start_service);
        mBtnStop = (Button) findViewById(R.id.btn_stop_service);
        mBtnGetInfo = (Button) findViewById(R.id.btn_get_info);
        initListener();
    }

    private void initListener() {
        mBtnService.setOnClickListener(this);
        mBtnGetInfo.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                Intent intent = new Intent(ServiceActivity.this, SimpleService.class);
                startService(intent);
                bindService(intent, connection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_stop_service:
                stopService(new Intent(ServiceActivity.this, SimpleService.class));
                if (mBound) {
                    unbindService(connection);
                    mBound = false;
                }
                break;
            case R.id.btn_get_info:
                if (mBound) {
                    int i = mService.getInt();
                    Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
