package com.arno.demo.life.binder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import com.arno.demo.life.R;
import com.blankj.utilcode.util.ToastUtils;

public class IntentServiceLearnActivity extends AppCompatActivity {
    private static final String TAG = "IntentLearn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service_learn);
    }

    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected() called with: name = [" + name + "], service = [" + service + "]");
            ToastUtils.showShort("连接成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected() called with: name = [" + name + "]");
            ToastUtils.showShort("连接断开");
        }
    };

    public void doBindService(View view) {
        Log.d(TAG, "doBindService: ");

        bindService(getMyIntent(), mConnection, Context.BIND_AUTO_CREATE);
    }

    public void doUnbindService(View view) {
        Log.d(TAG, "doUnbindService: ");
        try {
            unbindService(mConnection);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startCustomService(View view) {
        Log.d(TAG, "startCustomService: ");
        //开启多个Service
        for (int i = 0; i < 3; i++) {
            startService(getMyIntent());
        }
    }

    public void stopCustomService(View view) {
        Log.d(TAG, "stopCustomService: ");
        try {
            stopService(getMyIntent());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Intent getMyIntent() {
        Intent intent = new Intent(this, MyIntentService.class);
        //放入两个异步任务
        intent.putExtra(MyIntentService.ACTION_TAG, MyIntentService.ACTION_DOWNLOAD);
        return intent;
    }
}