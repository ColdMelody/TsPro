package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class ScrollerTestView extends ViewGroup {
    private Scroller mScroller;
    private int mLastX;
    private VelocityTracker mVelocityTracker;
    private int mTouchSlop;
    private int mMaxVelocity;
    private int mCurrentPage = 0;


    public ScrollerTestView(Context context) {
        super(context);
        init(context);
    }

    public ScrollerTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaxVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.layout(i * getWidth(), t, (i + 1) * getWidth(), b);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        initVelocityTrackerIfNotExists();
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                mLastX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = mLastX - x;
                scrollBy(dx, 0);
                mLastX = x;
                break;
            case MotionEvent.ACTION_UP:
                mVelocityTracker.computeCurrentVelocity(1000);
                int velocity = (int) mVelocityTracker.getXVelocity();
                if (velocity > mMaxVelocity && mCurrentPage > 0) {
                    scrollToPage(mCurrentPage - 1);
                } else if (velocity < -mMaxVelocity && mCurrentPage < (getChildCount() - 1)) {
                    scrollToPage(mCurrentPage + 1);
                } else {
                    slowScrollToPage();
                }
                recycleVelocityTracker();
                break;
        }
        return true;
    }

    /**
     * 滑动到指定屏幕
     *
     * @param indexPage
     */
    private void scrollToPage(int indexPage) {
        mCurrentPage = indexPage;
        if (mCurrentPage > getChildCount() - 1) {
            mCurrentPage = getChildCount() - 1;
        }
        //计算滑动到指定Page还需要滑动的距离
        int dx = mCurrentPage * getWidth() - getScrollX();
        mScroller.startScroll(getScrollX(), 0, dx, 0, Math.abs(dx) * 2);//动画时间设置为Math.abs(dx) * 2 ms
        //记住，使用Scroller类需要手动invalidate
        invalidate();
    }

    /**
     * 缓慢滑动抬起手指的情形，需要判断是停留在本Page还是往前、往后滑动
     */
    private void slowScrollToPage() {
        //当前的偏移位置
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        //判断是停留在本Page还是往前一个page滑动或者是往后一个page滑动
        int whichPage = (getScrollX() + getWidth() / 2) / getWidth();
        scrollToPage(whichPage);
    }

    private void recycleVelocityTracker() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
