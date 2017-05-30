package sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by Bill on 2016/12/14.
 * Email androidBaoCP@163.com
 */

public abstract class SensorManagerHelper implements SensorEventListener {
    private SensorManager sensorManager;
    private Context mContext;
    private SensorActionListener listener;
    private int type;
    SensorManagerHelper(Context context, int type) {
        this.mContext = context;
        this.type=type;
    }

    public void start() {
        sensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            Sensor sensor = obtainSensor(type);
            if (sensor != null) {
                sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
            }
        }
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    private Sensor obtainSensor(int type) {
        return sensorManager.getDefaultSensor(type);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        action(event,listener);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    public void setListener(SensorActionListener listener){
        this.listener=listener;
    }

    public abstract void action(SensorEvent event,SensorActionListener listener);
}
