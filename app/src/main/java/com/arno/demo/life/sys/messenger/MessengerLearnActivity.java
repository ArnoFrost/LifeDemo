package com.arno.demo.life.sys.messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.arno.demo.life.R;
import com.blankj.utilcode.util.ToastUtils;

import static com.arno.demo.life.sys.messenger.MyService.MSG_FROM_SERVICE;
import static com.arno.demo.life.sys.messenger.MyService.MSG_HELLO;
import static com.arno.demo.life.sys.messenger.MyService.MSG_TRANSFER_SERIALIZABLE;

public class MessengerLearnActivity extends AppCompatActivity {
    private static final String TAG = "Messenger_Client";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger_learn);

    }


    @Override
    protected void onStop() {
        super.onStop();
        disConnectService(null);
    }

    private Messenger mService;
    private boolean hasBind = false;
    private final ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //建立连接后通过IBinder 创建Messenger对象
            mService = new Messenger(service);
            hasBind = true;
            ToastUtils.showShort("连接已建立");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            hasBind = false;
            ToastUtils.showShort("连接已断开");
        }
    };

    class InComingHandler extends Handler {
        InComingHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MSG_FROM_SERVICE:
                    Log.d(TAG, "handleMessage: MSG_FROM_SERVICE " + msg.getData().getString("reply"));
                    sendPersonToService(msg, "Arno");
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }

        private void sendPersonToService(Message msg, String personName) {

            Message message = Message.obtain(null, MSG_TRANSFER_SERIALIZABLE, 0, 0);
            Bundle bundle = new Bundle();
            bundle.putString("person", personName);
            message.setData(bundle);

            try {
                mService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建客户端自己的回调处理
     */
    private final Messenger mClientMessenger = new Messenger(new InComingHandler(Looper.getMainLooper()));

    public void sayHello(View view) {
        Log.d(TAG, "sayHello: ");
        if (!hasBind) {
            return;
        }

        Message hello = Message.obtain(null, MSG_HELLO, 0, 0);

        hello.replyTo = mClientMessenger;
        Bundle bundle = new Bundle();
        bundle.putString("person", "Arno");
        hello.setData(bundle);

        try {
            mService.send(hello);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    public void connectService(View view) {
        Log.d(TAG, "connectService: ");
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public void disConnectService(View view) {
        Log.d(TAG, "disConnectService: ");
        if (hasBind) {
            unbindService(mConnection);
        }
    }

}