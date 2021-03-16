package com.arno.demo.life.conflict.view.inner;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.arno.demo.life.utils.ViewUtil;

/**
 * 处理都是纵向滑动的RecyclerView
 */
public class MyRecyclerViewBothVertical extends RecyclerView {
    private static final String TAG = "BothVertical";

    public MyRecyclerViewBothVertical(@NonNull Context context) {
        super(context);
    }

    public MyRecyclerViewBothVertical(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerViewBothVertical(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        boolean customConsume = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //此处开始拦截
                getParent().requestDisallowInterceptTouchEvent(true);
                mLastX = x;
                mLastY = y;
                customConsume = true;
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                //处理自己也是纵向滚动
                if (ViewUtil.isHorizontal(deltaX, deltaY)) {
                    //纵向滚动查看当前方向是否能滚动
                    if (deltaY > 0) {
                        //向上滑动 同时满足可以继续向上滑动
                        if (!canScrollVertically(-1)) {
                            customConsume = false;
                            //不可以向上滑动则交由父布局
                            Log.e(TAG, "dispatchTouchEvent: 向上滑动终点 继续不再处理");
                            getParent().requestDisallowInterceptTouchEvent(false);
                        } else {
                            customConsume = true;
                        }
                    } else {
                        //向下滑动 同时满足可以继续向下滑动
                        if (!canScrollVertically(1)) {
                            //不可以向下滑动则交由父布局
                            Log.e(TAG, "dispatchTouchEvent: 向下滑动终点 继续不再处理");
                            getParent().requestDisallowInterceptTouchEvent(false);
                            customConsume = false;
                        } else {
                            customConsume = true;
                        }
                    }
                } else {
                    //横向滚动都则都交由外部 处理
                    getParent().requestDisallowInterceptTouchEvent(false);
                    customConsume = false;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
            default:
                break;
        }
        //重新赋值
        mLastX = x;
        mLastY = y;

        boolean consume = super.dispatchTouchEvent(event);
//        Log.d(TAG, "consume: customResume = " + customConsume);
//        Log.d(TAG, "consume: consume = " + consume);


        boolean isSame = (consume == customConsume);

        Log.d(TAG, "dispatchTouchEvent: consume == customResume : " + isSame);

        if (!isSame) {
            Log.e(TAG, "dispatchTouchEvent: consume not same " + event.getAction());
        }
        return customConsume;
    }


}
