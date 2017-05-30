package util;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by Bill on 2017/4/25.
 * Email androidBaoCP@163.com
 */

public class BoxUtil {
    /**
     * 获取屏幕密度
     */
    public static int getDisplayDensity(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.densityDpi;
    }

    public static int[] getScreenSize(Activity activity){
        int[] size = new int[2];
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = activity.getWindowManager();
        manager.getDefaultDisplay().getMetrics(metrics);
        size[0] = metrics.widthPixels;
        size[1] = metrics.heightPixels;
        return size;
    }

}
