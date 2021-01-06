package com.arno.demo.life.conflict.view.outer;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.arno.demo.life.utils.ViewUtil;

public class MyScrollerVerticalHorizontal extends ScrollView {

    public MyScrollerVerticalHorizontal(Context context) {
        this(context, null);
    }

    public MyScrollerVerticalHorizontal(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyScrollerVerticalHorizontal(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyScrollerVerticalHorizontal(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private int mInitialTouchX;
    private int mInitialTouchY;

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //DOWN事件不能拦截，否则事件将无法分发到子View
                isIntercept = false;

                mInitialTouchX = (int) (ev.getX() + 0.5f);
                mInitialTouchY = (int) (ev.getY() + 0.5f);
                break;
            case MotionEvent.ACTION_MOVE:
                //根据条件判断是否拦截事件
                isIntercept = needThisEvent(ev);
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

    /**
     * 判断是否是scrollerView 同时不是RV view
     *
     * @param event
     * @return
     */
    private boolean needThisEvent(MotionEvent event) {
        // 获取手指当前所在的位置
        final int x = (int) (event.getX() + 0.5f);
        final int y = (int) (event.getY() + 0.5f);
        // 计算距离
        int dx = mInitialTouchX - x;
        int dy = mInitialTouchY - y;

        //如果 dx 为正数代表向左滑动，dx 为负数代表向右滑动。
        //如果 dy 为正数代表向上滑动，dy 为负数代表向下滑动。

        //判断是否是竖直方向 数值大于水平 则自己处理
        // 否则交由子布局处理
        return ViewUtil.isVertical(dx, dy);

    }
}