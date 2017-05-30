package hardware;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.bcp.tspro.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import view.CameraPreview;

import static android.content.ContentValues.TAG;


/**
 * Created by Bill on 2016/12/7.
 * Email androidBaoCP@163.com
 */

public class CustomCamera extends Activity {

    private Camera camera;
    private CameraPreview cameraPreview;
    private Button mBtnCapture;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_custom_camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        mBtnCapture = (Button) findViewById(R.id.button_capture);
        if (checkCameraHardware(this)) {
            camera = getCameraInstance();
            cameraPreview = new CameraPreview(this, camera);
            preview.addView(cameraPreview);
        }
        mBtnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, mPicture);
            }
        });

    }

    public boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public static Camera getCameraInstance() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (Exception e) {

        }
        return camera;
    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };
}
