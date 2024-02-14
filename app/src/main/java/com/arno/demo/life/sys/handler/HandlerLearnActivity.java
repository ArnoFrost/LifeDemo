package com.arno.demo.life.sys.handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.MessageQueue;
import android.util.Log;
import android.view.View;

import com.arno.demo.life.R;
import com.arno.demo.life.base.utils.LifeUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HandlerLearnActivity extends AppCompatActivity {
    private static final String TAG = "HandlerLearnActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new LifeUtil(TAG, this);
        View view = getLayoutInflater().inflate(R.layout.activity_handler_learn, null);
        setContentView(view);
        view.post(() -> Log.d(TAG, "run: view post执行 -> " + Thread.currentThread()));
        sendNormalMessage(null);

        getNormalHandler().getLooper().getQueue().addIdleHandler(idleHandler);
    }

    private final MessageQueue.IdleHandler idleHandler = new MessageQueue.IdleHandler() {
        int count = 0;

        @Override
        public boolean queueIdle() {
            Log.d(TAG, "queueIdle: 有空闲");
            if (count > 5) {
                Log.e(TAG, "queueIdle: 条件触发不再执行");
                return false;
            }
            count++;
            return true;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (normalHandler != null) {
            normalHandler.removeCallbacks(null);
        }
        if (asyncHandler != null) {
            asyncHandler.removeCallbacks(null);
            asyncHandler.getLooper().getQueue().removeIdleHandler(idleHandler);
        }
    }

    private HandlerThread normalThread;
    private Handler normalHandler;



    private HandlerThread asyncThread;
    private Handler asyncHandler;

    private Handler getAsyncHandler() {
        if (asyncThread == null) {
            asyncThread = new HandlerThread("AsyncThread");
            asyncThread.start();
        }
        if (asyncHandler == null) {
            asyncHandler = new Handler(asyncThread.getLooper(), msg -> {
                Log.d(TAG, "getAsyncHandler: 异步消息执行");
                return false;
            });
        }

        return asyncHandler;
    }

    private int token;
    private volatile boolean hasOpen = false;

    private void openBarrier(@NonNull Handler handler) {
        if (hasOpen) {
            return;
        }
        //获取当前线程Looper对象的消息队列
        MessageQueue queue = handler.getLooper().getQueue();
        try {
            //通过反射调用
            Method method = MessageQueue.class.getDeclaredMethod("postSyncBarrier");
            token = (int) method.invoke(queue);
            Log.d(TAG, "openBarrier() called with: token = [" + token + "]");
            hasOpen = true;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void closeBarrier(@NonNull Handler handler) {
        if (!hasOpen) {
            return;
        }
        //移除同步屏障
        //获取当前线程Looper对象的消息队列
        MessageQueue queue = handler.getLooper().getQueue();
        Method method = null;
        try {
            method = MessageQueue.class.getDeclaredMethod("removeSyncBarrier", int.class);
            method.invoke(queue, token);
            Log.d(TAG, "closeBarrier: ");
            hasOpen = false;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void sendNormalMessage(View view) {
        Log.d(TAG, "sendNormalMessage: ");
//        getNormalHandler().sendMessage(Message.obtain());
        getNormalHandler().sendMessageDelayed(Message.obtain(), 1000);
    }
    private Handler getNormalHandler() {
        if (normalThread == null) {
            normalThread = new HandlerThread("NormalThread");
            normalThread.start();
        }
        if (normalHandler == null) {
            normalHandler = new Handler(normalThread.getLooper(), msg -> {
                Log.d(TAG, "2. 构造方法callback 执行 handleMessage() 是否拦截 : " + (msg.what > 0) + ",thread = " + Thread.currentThread());
                return msg.what > 0;
            }) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    Log.d(TAG, "3. 最后覆写方法的回调执行 handleMessage() called with: msg = [" + msg + "]");
                }
            };
        }

        return normalHandler;
    }

    public void sendCallbackMessage(View view) {
        Log.d(TAG, "sendCallbackMessage: ");
        Message obtain = Message.obtain(normalHandler, new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: 1. 如果有runnable 对象 则首先执行 且不执行 2.3. ");
            }
        });
        getNormalHandler().sendMessage(obtain);
    }

    public void sendCallbackInterceptMessage(View view) {
        Log.d(TAG, "sendCallbackInterceptMessage: ");
        Message obtain = Message.obtain();
        obtain.what = 999;
        getNormalHandler().sendMessage(obtain);
    }

    public void openBarrier(View view) {
        openBarrier(getAsyncHandler());
    }

    public void closeBarrier(View view) {
        closeBarrier(getAsyncHandler());
    }

    private volatile int count = 0;

    public void sendAsync(View view) {
        Log.d(TAG, "sendAsync: ");
        Message obtain = Message.obtain(asyncHandler, 1, count++, 0);
        obtain.setAsynchronous(true);
        getAsyncHandler().sendMessageDelayed(obtain, 2000);
    }

    public void sendSync(View view) {
        Log.d(TAG, "sendSync: ");
        getAsyncHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "同步消息执行 run sync : count " + ++count);
            }
        }, 2000);
    }
}