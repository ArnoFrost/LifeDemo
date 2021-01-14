package com.arno.demo.life.life;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;

import com.arno.demo.life.R;
import com.arno.demo.life.utils.LifeUtil;
import com.blankj.utilcode.util.ToastUtils;

public class B_Translate_Auto_Activity extends Activity {
    private static final String TAG = "LifeUtil:B_TransAuto";

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_life_b_translate);

        if (!hasStart){
            Log.d(TAG, "do post: ");
            ToastUtils.showShort("准备跳转");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run: -------第一个过程完毕----------");
                }
            }, 1000);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    hasStart = true;
                    Log.d(TAG, "run: -------第二个过程开始----------");
                    startActivity(new Intent(B_Translate_Auto_Activity.this, B_WithTask_Activity.class));
                }
            }, 5000);
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    private boolean hasStart = false;

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");

    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }


    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}