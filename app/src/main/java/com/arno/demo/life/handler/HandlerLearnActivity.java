package com.arno.demo.life.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.arno.demo.life.R;

public class HandlerLearnActivity extends AppCompatActivity {
    private static final String TAG = "HandlerLearnActivity";
    private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Log.d(TAG, "2. 构造方法callback 执行 handleMessage() 是否拦截 : " + (msg.what > 0));
            return msg.what > 0;
        }
    }) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d(TAG, "3. 最后覆写方法的回调执行 handleMessage() called with: msg = [" + msg + "]");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_learn);
    }

    public void sendNormalMessage(View view) {
        Log.d(TAG, "sendNormalMessage: ");
        mHandler.sendMessage(Message.obtain());
    }

    public void sendCallbackMessage(View view) {
        Log.d(TAG, "sendCallbackMessage: ");
        Message obtain = Message.obtain(mHandler, new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 1. 如果有runnable 对象 则首先执行 且不执行 2.3. ");
            }
        });
        mHandler.sendMessage(obtain);
    }

    public void sendCallbackInterceptMessage(View view) {
        Log.d(TAG, "sendCallbackInterceptMessage: ");
        Message obtain = Message.obtain();
        obtain.what = 999;
        mHandler.sendMessage(obtain);
    }
}