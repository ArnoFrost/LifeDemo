package com.arno.demo.life.base.utils;

import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;

import java.util.ArrayDeque;

public class ViewUtil {
    private static final String TAG = "ViewUtil";

    public static String getModeString(int measureSpec) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        String string = "unkown";
        switch (mode) {
            case View.MeasureSpec
                    .UNSPECIFIED:
                string = "UNSPECIFIED";
                break;
            case View.MeasureSpec.EXACTLY:
                string = "EXACTLY";
                break;
            case View.MeasureSpec.AT_MOST:
                string = "AT_MOST";
                break;
            default:
                break;
        }
        return string;
    }

    /**
     * 判断是否是垂直滚动
     *
     * @param deltaX
     * @param deltaY
     * @return
     */
    public static boolean isVertical(int deltaX, int deltaY) {
        Log.d(TAG, "isVertical() called with: deltaX = [" + deltaX + "], deltaY = [" + deltaY + "]");
        return Math.abs(deltaX) > Math.abs(deltaY);
    }

    public static boolean isHorizontal(int deltaX, int deltaY) {
        Log.d(TAG, "isHorizontal() called with: deltaX = [" + deltaX + "], deltaY = [" + deltaY + "]");
        return Math.abs(deltaY) > Math.abs(deltaX);
    }

    public static ViewUtil create() {
        return new ViewUtil();
    }

    public void applyViewRecursively(ViewGroup parent, int depth) {
        if (parent == null) {
            Log.e(TAG, "applyViewRecursively: parent is null");
            return;
        }
        int childCount = parent.getChildCount();
        Log.d(TAG, "applyViewRecursively: parent = " + parent.getClass() + ":" + parent.getId() + " , childCount = " + childCount);
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            if (child instanceof ViewGroup) {
                //递归遍历
                applyViewRecursively((ViewGroup) child, depth++);
            } else if (child != null) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < depth; j++) {
                    builder.append("--");
                }
                Log.d(TAG, "applyViewRecursively: depth = " + depth + "," + builder.toString() + "parent = " + parent.getClass().getSimpleName() + " , child = " + child.getClass().getSimpleName());
            }
        }
        Log.d(TAG, "applyViewRecursively: parent = " + parent.getClass().getSimpleName() + ":" + parent.getId() + " finished");
    }

    /**
     * 层序遍历
     *
     * @param rootView
     */
    public void breadthTravelView(View rootView) {
        ArrayDeque<View> queue = new ArrayDeque<>();
        queue.addLast(rootView);
        int depth = 0;
        while (!queue.isEmpty()) {
            View temp = queue.getFirst();
            //将当前view层的所有子添加进来
            if (temp instanceof ViewGroup) {
                int childCount = ((ViewGroup) temp).getChildCount();
                for (int i = 0; i < childCount; i++) {
                    queue.addLast(((ViewGroup) temp).getChildAt(i));
                }
                //当前层结束
                Log.d(TAG, "breadthTravelView: depth " + depth++ + " 层结束");
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                builder.append("--");
            }
            //打印参数
            Log.d(TAG, "breadthTravelView:" + builder.toString() + " depth = " + depth + ",rootView : " + temp.getClass().getSimpleName() + ":" + temp.getId() + " ,child = " + temp.getClass().getSimpleName());
            if (!queue.isEmpty()) {
                Log.d(TAG, "breadthTravelView: |");
            }
            //队头出队
            queue.pollFirst();
        }
    }

    /**
     * 深度遍历
     *
     * @param rootView
     */
    public void depthTravelView(View rootView) {
        ArrayDeque<View> queue = new ArrayDeque<>();
        queue.addLast(rootView);
        int depth = 0;
        while (!queue.isEmpty()) {
            View temp = queue.getLast();
            //队尾出队
            queue.pollLast();
            if (temp instanceof ViewGroup) {
                int childCount = ((ViewGroup) temp).getChildCount();
                for (int i = childCount - 1; i >= 0; i--) {
                    queue.addLast(((ViewGroup) temp).getChildAt(i));
                }
            }

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                builder.append("--");
            }
            //打印参数
            Log.d(TAG, "depthTravelView:" + builder.toString() + " depth = " + depth + ",rootView : " + temp.getClass().getSimpleName() + ":" + temp.getId() + " ,child = " + temp.getClass().getSimpleName());
            depth++;
            if (!queue.isEmpty()) {
                Log.d(TAG, "depthTravelView: |");
            }
        }
    }

    public static float getDensity() {
        return Resources.getSystem().getDisplayMetrics().density;
    }

    public static float getDensityDpi() {
        return Resources.getSystem().getDisplayMetrics().densityDpi;
    }

    @ColorInt
    public static int adjustAlpha(@ColorInt int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
