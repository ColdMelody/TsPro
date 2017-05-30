package com.example.bcp.tspro;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.lang.reflect.Method;

public class AnimActivity extends Activity {

    ImageView mIvDownloading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        Button btnMove = (Button) findViewById(R.id.btn_move);
        Button mBtnDeviceName = (Button) findViewById(R.id.show_device_name);
        mBtnDeviceName.setText(getEMUI());
        final RotateAnimation animation = new RotateAnimation(0, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Integer.MAX_VALUE);
        animation.setRepeatMode(Animation.RESTART);
        mIvDownloading = (ImageView) findViewById(R.id.downloading);
//        mIvDownloading.startAnimation(animation);
        mIvDownloading.post(new Runnable() {
            @Override
            public void run() {
                mIvDownloading.startAnimation(animation);
            }
        });
        btnMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvDownloading.startAnimation(animation);
//                initAnim(mIvDownloading);
//                obtainAnimator();
            }
        });
        mIvDownloading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getAnimation() != null) {
                    v.getAnimation().cancel();
                }
            }
        });
//        Log.e("Cpeng",mIvDownloading.getStateListAnimator().toString());
    }

    private void initAnim(View view) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationX", 400);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(view, "alpha", 1f, 0.5f);
        AnimatorSet set1 = new AnimatorSet();
        set1.play(animator2).after(animator1);
        set1.setDuration(1000);
        AnimatorSet set2 = new AnimatorSet();
        set2.play(animator3).after(set1);
        set2.setDuration(500);
        set2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Toast.makeText(AnimActivity.this, "End Anim!", Toast.LENGTH_SHORT).show();
            }
        });
        set1.start();
        set2.start();
    }

    private void obtainAnimator() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(mIvDownloading, "rotation", 0f, 360f);
        animator.setDuration(2000);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.setRepeatCount(Integer.MAX_VALUE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    public String getEMUI() {
        Class<?> classType;
        String buildVersion = null;
        try {
            classType = Class.forName("android.os.SystemProperties");
            Method getMethod = classType.getDeclaredMethod("get", String.class);
            buildVersion = (String) getMethod.invoke(classType, "ro.build.version.emui");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buildVersion;
    }
}
