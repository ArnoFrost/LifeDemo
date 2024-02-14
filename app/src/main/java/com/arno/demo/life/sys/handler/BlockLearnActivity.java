package com.arno.demo.life.sys.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.arno.demo.life.R;

public class BlockLearnActivity extends AppCompatActivity {
    private static final String TAG = "BlockLearnActivity";
    private final Handler normalHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            try {
                Thread.sleep(100);
                Log.d(TAG, "handleMessage: ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_learn);

        normalHandler.getLooper().setMessageLogging(new MyPrinter());
    }

    public void postLarge(View view) {
        for (int i = 0; i < 100; i++) {
            normalHandler.sendEmptyMessage(i);
        }
    }
}