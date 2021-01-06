package com.arno.demo.life.conflict.view.outer;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;

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

    private final HashSet<View> canVerticalSet = new HashSet<>();
    private View rootLayout;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //DOWN事件不能拦截，否则事件将无法分发到子View
                isIntercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //根据条件判断是否拦截事件
                //落在满足条件的子view时不处理
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


    private boolean needThisEvent(MotionEvent event) {
        boolean isIntercept = false;
        //FIXME 做一个优化并不严谨
        if (rootLayout == null) {
            rootLayout = getChildAt(0);
        }
        //如果根布局是Recyclerview 的垂直方向
        if (canVertical(rootLayout) && ((RecyclerView) rootLayout).getLayoutManager() != null && ((RecyclerView) rootLayout).getLayoutManager().canScrollVertically()) {
            Log.e(TAG, "onInterceptTouchEvent: 1. 根布局是满足条件的直接交由处理");
            isIntercept = !isTouchPointInView(rootLayout, (int) event.getX(), (int) event.getY());
        } else if (rootLayout instanceof ViewGroup) {
            int count = ((ViewGroup) rootLayout).getChildCount();
            //FIXME  做一个优化并不严格  demo但是实际过程仍旧需要判断处理
            if (canVerticalSet.size() == 0) {
                for (int i = 0; i < count; i++) {
                    View view = ((ViewGroup) rootLayout).getChildAt(i);
                    if (canVertical(view)) {
                        //存入内部
                        ((ViewGroup) rootLayout).getChildAt(i).setTag("canScroll" + i);
                        canVerticalSet.add(((ViewGroup) rootLayout).getChildAt(i));
                    }
                }
            }

            if (canVerticalSet.size() > 0) {
                isIntercept = !isTouchPointInView(canVerticalSet, (int) event.getX(), (int) event.getY());
                if (isIntercept) {
                    Log.e(TAG, "onInterceptTouchEvent: 2. 没有可滚动");
                } else {
                    Log.e(TAG, "onInterceptTouchEvent: 2. 子布局命中到可滚动 则交由子布局处理");
                }
            } else {
                Log.e(TAG, "onInterceptTouchEvent: 3. 其他默认则不处理");
                isIntercept = true;
            }
        }
        return isIntercept;
    }

    private boolean isTouchPointInView(HashSet<View> viewSet, int x, int y) {
        if (viewSet == null || viewSet.size() == 0) {
            return false;
        }
        int[] location = new int[2];
        //遍历看是否命中区域内
        for (View view : viewSet) {
            if (view == null) {
                continue;
            }
            view.getLocationOnScreen(location);
            int left = location[0];
            int top = location[1];
            int right = left + view.getMeasuredWidth();
            int bottom = top + view.getMeasuredHeight();
            //view.isClickable() &&
            if (y >= top && y <= bottom && x >= left
                    && x <= right) {
                Log.e(TAG, "isTouchPointInView: 落在区域内 自行处理 " + view.getTag());
                return true;
            }
            Log.e(TAG, "isTouchPointInView: 落在区域外");
        }
        return false;
    }

    private boolean canVertical(View rootLayout) {
        //FIXME 过滤更多条件 rootLayout instanceof ViewPager等
        return rootLayout instanceof RecyclerView;
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

    private boolean isTouchPointInView(List<View> viewList, int x, int y) {
        if (viewList == null || viewList.isEmpty()) {
            return false;
        }
        int[] location = new int[2];

        //遍历看是否命中区域内
        for (View view : viewList) {
            view.getLocationOnScreen(location);
            int left = location[0];
            int top = location[1];
            int right = left + view.getMeasuredWidth();
            int bottom = top + view.getMeasuredHeight();
            //view.isClickable() &&
            if (y >= top && y <= bottom && x >= left
                    && x <= right) {
                Log.e(TAG, "isTouchPointInView: 落在区域内 自行处理 " + view.getTag());
                return true;
            }
            Log.e(TAG, "isTouchPointInView: 落在区域外");
        }

        return false;
    }
}