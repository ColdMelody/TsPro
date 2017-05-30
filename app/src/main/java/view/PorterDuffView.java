package view;

import android.content.Context;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Bill on 2017/5/30.
 * Email androidBaoCP@163.com
 */

public class PorterDuffView extends View {

    private Paint mPaint;
    private PorterDuffXfermode porterDuffXfermode;
    public PorterDuffView(Context context) {
        super(context);
        initPaint();
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    public PorterDuffView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();
        int layerId = canvas.saveLayer(0, 0, canvasWidth, canvasHeight, null, Canvas.ALL_SAVE_FLAG);
        int radius = canvasWidth/3;
        mPaint.setColor(0xFFFFCC44);
        canvas.drawCircle(radius,radius,radius,mPaint);
        mPaint.setXfermode(porterDuffXfermode);
        mPaint.setColor(0xFF66AAFF);
        canvas.drawRect(radius,radius,radius*2.7f,radius*2.7f,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }
}
