package com.arno.demo.life.utils;

import android.util.Log;
import android.view.View;

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
}
