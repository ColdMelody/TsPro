package hardware;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.os.Build;

/**
 * Created by Bill on 2016/12/7.
 * Email androidBaoCP@163.com
 */

public class CustomCamera2 {
    private CameraManager manager;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomCamera2(Context context) {
        manager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
    }
}
