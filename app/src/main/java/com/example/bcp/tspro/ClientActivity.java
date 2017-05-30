package com.example.bcp.tspro;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import service.RemoteService;

public class ClientActivity extends Activity {
    private TextView mTvShow;
    private Messenger mService;
    private boolean mBound;

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RemoteService.MSG_SET_VALUE:
                    mTvShow.setText("Received from service: " + msg.arg1);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
//            mTvShow.setText("Attached.");
            try {
                Message msg = Message.obtain(null, RemoteService.MSG_REGISTER_CLIENT);
                msg.replyTo = mMessenger;
                mService.send(msg);
                msg = Message.obtain(null,
                        RemoteService.MSG_SET_VALUE, this.hashCode(), 0);
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Button btnBind = (Button) findViewById(R.id.bind_service);
        Button btnUnbind = (Button) findViewById(R.id.unbind_service);
        mTvShow = (TextView) findViewById(R.id.tv_show);
        mTvShow.setText("NoAttached");
        btnBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBindService();
                mBound = true;

            }
        });
        btnUnbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(connection);
            }
        });
    }

    private void doBindService() {
        if (!mBound) {
            Intent intent = new Intent(this, RemoteService.class);
//            String packageName =getPackageName();
//            String serviceName = SERVICE_ACTION;
//            ComponentName componentName = new ComponentName(packageName, serviceName);
//            intent.setComponent(componentName);
            bindService(intent, connection, BIND_AUTO_CREATE);
        }
    }
}
