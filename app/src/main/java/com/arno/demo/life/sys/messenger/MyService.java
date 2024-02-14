package com.arno.demo.life.sys.messenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;

public class MyService extends Service {
    private static final String TAG = "Messenger_Service";
    public static final int MSG_HELLO = 1;
    public static final int MSG_TRANSFER_SERIALIZABLE = 2;
    public static final int MSG_FROM_SERVICE = 3;

    public MyService() {
    }

    private Messenger mMessenger;

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        //通过创建Handler将Messenger实例化
        mMessenger = new Messenger(new InComingHandler(Looper.getMainLooper()));
        return mMessenger.getBinder();
    }

    static class InComingHandler extends Handler {
        public InComingHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d(TAG, "handleMessage() called with: msg = [" + msg + "]");
            switch (msg.what) {
                case MSG_HELLO:
                    ToastUtils.showShort("hello!");
                    replyToClient(msg, "服务端已经收到 HELLO 方法");
                    break;
                case MSG_TRANSFER_SERIALIZABLE:
                    Log.d(TAG, "传递过来的对象:" + msg.getData().get("person"));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }

        private void replyToClient(Message msg, String replyText) {
            Messenger clientMessenger = msg.replyTo;
            Message replyMessage = Message.obtain(null, MSG_FROM_SERVICE);
            Bundle bundle = new Bundle();
            bundle.putString("reply", replyText);
            replyMessage.setData(bundle);

            try {
                clientMessenger.send(replyMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}