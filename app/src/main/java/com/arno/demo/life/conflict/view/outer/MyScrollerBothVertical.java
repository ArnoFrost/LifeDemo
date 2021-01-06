package com.arno.demo.life.conflict.view.outer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.recyclerview.widget.RecyclerView;

public class MyScrollerBothVertical extends ScrollView {
    private static final String TAG = "MyScrollerBothVertical";

    public MyScrollerBothVertical(Context context) {
        this(context, null);
    }

    public MyScrollerBothVertical(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollerBothVertical(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //DOWN事件不能拦截，否则事件将无法分发到子View
                isIntercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //根据条件判断是否拦截事件
                //落在子view的第二个时则不处理
                View rootLayout = getChildAt(0);
                boolean isVerticalInnerFlag = false;
                if (rootLayout != null) {
                    View rvView = ((LinearLayout) rootLayout).getChildAt(1);
                    if (rvView instanceof RecyclerView) {
                        Log.e(TAG, "onInterceptTouchEvent: 是RV则判断");
                        isIntercept = !isTouchPointInView(rootLayout, (int) ev.getX(), (int) ev.getY());
                    }
                } else {
                    Log.e(TAG, "onInterceptTouchEvent: 不是rv则自己处理");
                    isIntercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //一旦父容器拦截了UP事件，子View将无法触发点击事件
                isIntercept = false;
                break;
            default:
                break;
        }
        return isIntercept;
    }

    //(x,y)是否在view的区域内
    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        //view.isClickable() &&
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            Log.e(TAG, "isTouchPointInView: 落在区域内 则rv自行处理");
            return true;
        }
        Log.e(TAG, "isTouchPointInView: 落在区域外");
        return false;
    }
}