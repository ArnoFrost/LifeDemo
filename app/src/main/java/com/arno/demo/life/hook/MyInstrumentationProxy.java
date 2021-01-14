package com.arno.demo.life.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyInstrumentationProxy extends Instrumentation {

    private static final String TAG = "MyInstrumentationProxy";

    Instrumentation mInstrumentation;

    public MyInstrumentationProxy(Instrumentation mInstrumentation) {
        this.mInstrumentation = mInstrumentation;
    }

    public ActivityResult execStartActivity(Context who, IBinder contextThread, IBinder token,
                                            Activity target, Intent intent,
                                            int requestCode, Bundle options) {
        Log.d(TAG, "Hook成功: " + "----who: " + who);
        ToastUtils.showShort("Hook成功: " + "----who: " + who);
        try {
            //获取方法
            Method execStartActivity = Instrumentation.class.getDeclaredMethod(
                    "execStartActivity", Context.class, IBinder.class, IBinder.class, Activity.class,
                    Intent.class, int.class, Bundle.class);
            return (ActivityResult) execStartActivity.invoke(mInstrumentation, who,
                    contextThread, token, target, intent, requestCode, options);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
