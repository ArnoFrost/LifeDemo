package com.arno.demo.life.conflict;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 处理外部垂直,本身水平的RecyclerView
 */
public class MyRecyclerViewVerticalHorizontal extends RecyclerView {


    public MyRecyclerViewVerticalHorizontal(@NonNull Context context) {
        super(context);
    }

    public MyRecyclerViewVerticalHorizontal(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerViewVerticalHorizontal(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int mLastX;
    private int mLastY;

    //注意父布局代码

    /**
     * public boolean onInterceptTouchEvent(MotionEvent event) {
     * int action = event.getAction();
     * if (action == MotionEvent.ACTION_DOWN) {
     * return false;
     * } else {
     * return true;
     * }
     * }
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //此处开始拦截
                getParent().requestDisallowInterceptTouchEvent(true);
                mLastX = x;
                mLastY = y;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                //1. 处理横纵滚动情况
                if (Math.abs(deltaY) > Math.abs(deltaX)) {
                    //父容器需要此类点击事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    //自己处理
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
}
