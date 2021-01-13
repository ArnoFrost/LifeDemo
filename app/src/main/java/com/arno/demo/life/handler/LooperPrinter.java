package com.arno.demo.life.handler;

import android.util.Log;
import android.util.Printer;

//LooperPrinter
public class LooperPrinter implements Printer {
    private static final String TAG = "LooperPrinter";
    public Printer origin;
    boolean isHasChecked = false;
    boolean isValid = false;

    LooperPrinter(Printer printer) {
        this.origin = printer;
    }

    @Override
    public void println(String x) {
        if (null != origin) {
            origin.println(x);
            if (origin == this) {
//                throw new RuntimeException(MonitorConstants.LOG_TAG + " origin == this");
            }
        }

        if (!isHasChecked) {
            isValid = x.charAt(0) == '>' || x.charAt(0) == '<';
            isHasChecked = true;
            if (!isValid) {
                Log.d(TAG, String.format("[println] Printer is inValid! x:%s", x));
//                InsectLogger.ne("[println] Printer is inValid! x:%s", x);
            }
        }
        Log.d(TAG, x);


//        if (isValid) {
//            dispatch(x.charAt(0) == '>', x);
//        }

    }
}