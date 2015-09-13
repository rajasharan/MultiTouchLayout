package com.rajasharan.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/**
 * Created by rajasharan on 8/14/15.
 */
public class MultiTouchLayout extends ViewGroup {
    private static final String TAG = "TouchLayout";

    private Paint mBubblePaint;
    private SparseArray<Point> mTouches;
    private int mRadius;
    private ViewConfiguration mViewConfig;

    public MultiTouchLayout(Context context) {
        this(context, null);
    }

    public MultiTouchLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mBubblePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBubblePaint.setStyle(Paint.Style.STROKE);
        mBubblePaint.setStrokeWidth(20f);
        mBubblePaint.setColor(Color.argb(128, 128, 128, 128));
        mRadius = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 55,
                context.getResources().getDisplayMetrics());
        mTouches = new SparseArray<>(10);

        mViewConfig = ViewConfiguration.get(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        for (int i=0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i=0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(l, t, r, b);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        for (int i=0; i<mTouches.size(); i++) {
            Point p = mTouches.valueAt(i);
            canvas.drawCircle(p.x, p.y, mRadius, mBubblePaint);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        int index = event.getActionIndex();
        int id = event.getPointerId(index);

        int xp = (int) event.getX(index);
        int yp = (int) event.getY(index);

        Point p = new Point(x, y);
        Point pp = new Point(xp, yp);

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                //Log.d(TAG, String.format("DOWN (index, id) -> (%s, %s) at %s", index, id, p));
                mTouches.append(id, p);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //Log.d(TAG, String.format("P_DOWN(index, id) -> (%s, %s) at %s", index, id, pp));
                mTouches.append(id, pp);
                break;
            case MotionEvent.ACTION_MOVE:
                for (int i=0; i<event.getPointerCount(); i++) {
                    int ind = event.findPointerIndex(i);
                    if (ind != -1) {
                        int xInd = (int) event.getX(ind);
                        int yInd = (int) event.getY(ind);
                        Point pNew = new Point(xInd, yInd);
                        mTouches.append(i, pNew);
                        //invalidate(pOrig);
                        //invalidate(pNew);
                    }
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                //Log.d(TAG, String.format("P_UP (index, id) -> (%s, %s) at %s", index, id, pp));
                mTouches.remove(id);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                //Log.d(TAG, String.format("UP (index, id) -> (%s, %s) at %s", index, id, p));
                mTouches.remove(id);
                break;
        }
        invalidate();
        return true;
    }

    private void invalidate(Point p) {
        int offset = mRadius+20;
        invalidate(p.x-offset, p.y-offset, p.x+offset, p.y+offset);
    }
}
