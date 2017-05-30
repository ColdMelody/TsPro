package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

/**
 * Created by Bill on 2017/4/11.
 * Email androidBaoCP@163.com
 * 一个可以自动调节比例的TextureView
 */

public class AutoTextureView extends TextureView {

    private int mRadioWidth = 0;
    private int mRadioHeight = 0;

    public AutoTextureView(Context context) {
        this(context, null);
    }

    public AutoTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoTextureView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 按照比例设置尺寸
     */
    public void setAspectRadio(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("Size cannot be negative");
        }
        mRadioWidth = width;
        mRadioHeight = height;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (mRadioWidth == 0 || mRadioHeight == 0) {
            setMeasuredDimension(width, height);
        } else {
            if (width < height * mRadioWidth / mRadioHeight) {
                setMeasuredDimension(width, width * mRadioHeight / mRadioWidth);
            } else {
                setMeasuredDimension(height * mRadioWidth / mRadioHeight, height);
            }
        }
    }
}
