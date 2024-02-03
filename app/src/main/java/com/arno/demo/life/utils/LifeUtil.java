package com.arno.demo.life.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * 自定义生命周期显示处理接口
 */
public class LifeUtil implements DefaultLifecycleObserver {

    private final String TAG;
    private final LifecycleOwner lifecycleOwner;

    public LifeUtil(String TAG, LifecycleOwner lifecycleOwner) {
        this.TAG = "LifeUtil-->" + TAG;
        this.lifecycleOwner = lifecycleOwner;
        addObserver();
    }

    private void addObserver() {
        if (lifecycleOwner != null) {
            lifecycleOwner.getLifecycle().addObserver(this);
        }
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "onCreate() called with: owner = [" + owner + "]");
    }

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "onStart() called with: owner = [" + owner + "]");
    }

    @Override
    public void onResume(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "onResume() called with: owner = [" + owner + "]");
    }

    @Override
    public void onPause(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "onPause() called with: owner = [" + owner + "]");
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "onStop() called with: owner = [" + owner + "]");
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {
        Log.d(TAG, "onDestroy() called with: owner = [" + owner + "]");
        if (lifecycleOwner != null) {
            lifecycleOwner.getLifecycle().removeObserver(this);
        }
    }

}
