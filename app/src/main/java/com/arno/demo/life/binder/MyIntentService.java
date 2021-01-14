package com.arno.demo.life.binder;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    private static final String TAG = "MyIntentService";

    public MyIntentService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return super.onBind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }


    public static final String ACTION_TAG = "action";
    public static final String ACTION_DOWNLOAD = "hello";

    private static int percent = 0;
    private static int totalPercent = 100;

    private int getRandomRemain() {
        int number = new Random().nextInt(totalPercent + 1);
        totalPercent -= number;
        return number;
    }

    private static final int WAIT_TIME = 2 * 1000;

    private int taskNumber = 1;

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d(TAG, "onHandleIntent: taskNumber = " + taskNumber++);
        String action = intent.getStringExtra(ACTION_TAG);
        //模拟下载过程
        if (TextUtils.equals(action, ACTION_DOWNLOAD)) {
            while (percent < 100) {
                percent += getRandomRemain();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                Log.i("Download", "download:" + percent);
            }
            reset();
        } else {
            Log.e(TAG, "onHandleIntent: 空闲任务等待 " + WAIT_TIME);
            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void reset() {
        percent = 0;
        totalPercent = 100;
    }
}