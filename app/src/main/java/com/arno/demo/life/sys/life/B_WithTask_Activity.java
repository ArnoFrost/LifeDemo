package com.arno.demo.life.sys.life;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.arno.demo.life.R;
import com.arno.demo.life.base.utils.LifeUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class B_WithTask_Activity extends AppCompatActivity {
    private static final String TAG = "B_WithTask_Activity";

    private View root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = LayoutInflater.from(this).inflate(R.layout.activity_life_b, null);
        setContentView(root);
        new LifeUtil(TAG, this);
        //放置复杂的任务 来使得主线程任务繁重
        postMessage();
    }

    private final AtomicInteger count = new AtomicInteger(0);

    private void postMessage() {
        root.post(() -> {
            Log.d(TAG, "postMessage: " + count.getAndIncrement());
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (count.get() < 200) {
                postMessage();
            }

        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}