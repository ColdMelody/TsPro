package sensor;

import android.content.Context;
import android.hardware.SensorEvent;


/**
 * Created by Bill on 2016/12/14.
 * Email androidBaoCP@163.com
 */

public class AccelerometerSensorHelper extends SensorManagerHelper {
    private float lastX;
    private float lastY;
    private float lastZ;
    private long lastUpdateTime;

    public AccelerometerSensorHelper(Context context, int type) {
        super(context, type);
    }

    @Override
    public void action(SensorEvent event, SensorActionListener listener) {
        final int speedShreshold = 1000;
        // 两次检测的时间间隔
        final int updateTime = 50;
        long currentTime = System.currentTimeMillis();
        long timeInterval = currentTime - lastUpdateTime;
        if (timeInterval < updateTime) return;
        lastUpdateTime = currentTime;
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];
        // 获得x,y,z的变化值
        float deltaX = x - lastX;
        float deltaY = y - lastY;
        float deltaZ = z - lastZ;
        // 将现在的坐标变成last坐标
        lastX = x;
        lastY = y;
        lastZ = z;
        double speed = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ
                * deltaZ)
                / timeInterval * 10000;
        if (speed > speedShreshold) {
            listener.onAction();
        }
    }
}
