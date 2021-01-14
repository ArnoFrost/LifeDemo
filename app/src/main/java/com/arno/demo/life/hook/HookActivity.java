package com.arno.demo.life.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;

public abstract class HookActivity extends AppCompatActivity {
    protected void setHook() {
        replaceActivityInstrumentation(this);
    }

    protected void replaceActivityInstrumentation(Activity activity) {
        try {
            //反射方法
            Field field = Activity.class.getDeclaredField("mInstrumentation");
            //开启权限
            field.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) field.get(activity);
            Instrumentation instrumentationProxy = new MyInstrumentationProxy(instrumentation);
            //放入
            field.set(activity, instrumentationProxy);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
