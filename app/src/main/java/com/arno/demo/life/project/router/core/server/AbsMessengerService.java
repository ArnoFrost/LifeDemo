package com.arno.demo.life.project.router.core.server;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.arno.demo.life.project.router.base.Constant;
import com.arno.demo.life.project.router.base.RouterRequest;

/**
 * 服务端 抽象类
 * 在需要的地方回调invokeMsg
 */
public abstract class AbsMessengerService extends Service {
    private static final String TAG = Constant.PRE.PRE_FIX + "AbsMsgService";

    private static final String THREAD_NAME = "MessengerServer";


    private Messenger mServerMessenger;
    private ServerMsgHandler mServerHandler;


    private Messenger mClientMessenger;

    private class ServerMsgHandler extends Handler {
        public ServerMsgHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d(TAG, "5. 服务端收到信息 >>>>>>>>: ");
            //保存需要通信的reply
            mClientMessenger = msg.replyTo;
            //解析拿到传输对象封装
            Bundle bundle = msg.getData();
            if (bundle != null) {
                bundle.setClassLoader(getClass().getClassLoader());
                RouterRequest request = bundle.getParcelable(Constant.PARAM.REQUEST);
                invokeMsg(request);
            }
        }
    }

    /**
     * 分发消息回调包装
     *
     * @param request
     */
    protected abstract void invokeMsg(RouterRequest request);

    /**
     * 回复客户端
     *
     * @param request
     */
    protected void replyToClient(RouterRequest request) {
        Log.d(TAG, "7. 服务端接收消息后 响应客户端 replyToClient() called with: request = [" + request + "]");
        if (mClientMessenger != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.PARAM.RESPONSE, request);

            //复用消息并返回
            Message msg = Message.obtain(null, Constant.CODE.MSG_REPLY, 0, 0);
            msg.setData(bundle);

            try {
                mClientMessenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return getMessenger().getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind() called with: intent = [" + intent + "]");
        return super.onUnbind(intent);
    }

    protected Messenger getMessenger() {
        if (mServerHandler == null || mServerMessenger == null) {
            if (mServerHandler != null && mServerHandler.getLooper() != null) {
                mServerHandler.getLooper().quit();
                mServerHandler = null;
            }

            HandlerThread thread = new HandlerThread(THREAD_NAME);
            thread.start();

            mServerHandler = new ServerMsgHandler(thread.getLooper());
            mServerMessenger = new Messenger(mServerHandler);
        }

        return mServerMessenger;
    }
}
