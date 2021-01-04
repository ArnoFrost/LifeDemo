package com.arno.demo.life.ground;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.arno.demo.life.R;
import com.arno.demo.life.utils.LifeUtil;

public class GroundLearnActivity extends AppCompatActivity {
    private static final String TAG = "GroundLearnActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_a);
        new LifeUtil(TAG, this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    /**
     * 启动普通Activity
     *
     * @param view
     */
    public void startNormal(View view) {
        startActivity(new Intent(this, B_Activity.class));
        doDelay();
    }

    private void doDelay() {
        boolean TEST_DELAY = true;
        if (TEST_DELAY) {
            finish();
            startTime = System.currentTimeMillis();
        }
    }

    /**
     * 启动透明Activity
     *
     * @param view
     */
    public void startTranslate(View view) {
        startActivity(new Intent(this, B_Translate_Activity.class));
    }

    private long startTime;

    public void startTask(View view) {
        startActivity(new Intent(this, B_WithTask_Activity.class));
        doDelay();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause() 距离 finish()：" + time(startTime) + " ms");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop() 距离 finish() ：" + time(startTime) + " ms");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy() 距离 finish() ：" + time(startTime) + " ms");
    }

    public static long time(long startTime) {
        return System.currentTimeMillis() - startTime;
    }
}