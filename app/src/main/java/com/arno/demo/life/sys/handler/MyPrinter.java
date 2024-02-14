package com.arno.demo.life.sys.handler;

import android.os.SystemClock;
import android.util.Log;
import android.util.Printer;

public class MyPrinter implements Printer {
    private static final String TAG = "MyPrinter";

    private boolean mStartedPrinting;
    private long mStartTimeMillis;
    private long mStartThreadTimeMillis;
    private long mBlockThresholdMillis = 30;


    @Override
    public void println(String x) {
        if (!mStartedPrinting) {
            mStartTimeMillis = System.currentTimeMillis();
            mStartThreadTimeMillis = SystemClock.currentThreadTimeMillis();
            mStartedPrinting = true;
        } else {
            final long endTime = System.currentTimeMillis();
            mStartedPrinting = false;
            if (isBlock(endTime)) {
                notifyBlockEvent(endTime);
            }
        }

    }

    private void notifyBlockEvent(long endTime) {
        Log.e(TAG, "notifyBlockEvent: " + endTime);
    }

    private boolean isBlock(long endTime) {
        boolean isBlock = endTime - mStartTimeMillis > mBlockThresholdMillis;
        Log.d(TAG, "isBlock: " + isBlock);
        return isBlock;
    }
}
