package service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;

import com.example.bcp.tspro.MainActivity;
import com.example.bcp.tspro.R;

import java.util.Random;

public class SimpleService extends Service {
    private ServiceHandler mHander;
    private MyBinder myBinder = new MyBinder();
    private Random random = new Random(47);

    private final class ServiceHandler extends Handler {
        ServiceHandler(Looper looper) {
            super(looper);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void handleMessage(Message msg) {
            Notification.Builder notification = new Notification.Builder(SimpleService.this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("This is title")
                    .setContentText("This is text");
            Intent intent = new Intent(SimpleService.this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(SimpleService.this, 0, intent, 0);
            notification.setContentIntent(pendingIntent);
            startForeground(10, notification.build());
        }
    }

    public SimpleService() {
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        Log.i("simpleservice", "onCreate");
        HandlerThread mThread = new HandlerThread("ServiceThread", Process.THREAD_PRIORITY_BACKGROUND);
        mThread.start();
        Looper mLooper = mThread.getLooper();
        mHander = new ServiceHandler(mLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("simpleservice", "onStartCommand");
        Message msg = mHander.obtainMessage();
        msg.arg1 = startId;
        mHander.sendMessage(msg);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return myBinder;
    }

    @Override
    public void onDestroy() {
        Log.i("simpleservice", "onDestroy");
        super.onDestroy();
    }

    public int getInt() {
        return random.nextInt(100);
    }

    public class MyBinder extends Binder {
        public Service getService() {
            return SimpleService.this;
        }
    }
}
