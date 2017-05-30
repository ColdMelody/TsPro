package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Bill on 2016/11/29.
 * Email androidBaoCP@163.com
 * 简单的SurfaceView应用实例
 */
public class SurfaceViewLine extends SurfaceView implements SurfaceHolder.Callback,Runnable {

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private boolean isDwawing;
    private int x;
    private int y;
    private Path mPath;
    private Paint mPaint;
    public SurfaceViewLine(Context context) {
        super(context);
        initView();
    }

    public SurfaceViewLine(Context context,AttributeSet attrs){
        super(context,attrs);
        initView();
    }

    public SurfaceViewLine(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        initView();
    }

    private void initView(){
        mHolder=getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
        mPath=new Path();
        mPaint=new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isDwawing=true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isDwawing=false;
    }

    @Override
    public void run() {
        while (isDwawing){
            draw();
            x+=1;
            y=(int)(100*Math.sin(x*2*Math.PI/180)+400);
            mPath.lineTo(x,y);
        }
    }
    private void draw(){
        try {
            mCanvas=mHolder.lockCanvas();
            mCanvas.drawColor(Color.WHITE);
            mCanvas.drawPath(mPath,mPaint);
        }catch (Exception e){

        }finally {
            if (mCanvas!=null){
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }
}
