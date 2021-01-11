package com.arno.demo.life.router.module.main;

import android.content.Context;
import android.util.Log;

import com.arno.demo.life.router.base.Constant;
import com.arno.demo.life.router.base.RouterRequest;
import com.arno.demo.life.router.util.RequestUtil;
import com.arno.demo.life.router.core.client.AbsMessengerClient;
import com.arno.demo.life.router.module.IModel;
import com.blankj.utilcode.util.ToastUtils;

public class MainClient extends AbsMessengerClient implements IModel, AbsMessengerClient.IResponseListener {

    private static final String TAG = Constant.PRE.PRE_FIX + "MainClientServer";

    private Context mContext;


    private AbsMessengerClient mMessenger;

    public AbsMessengerClient getMessenger() {
        if (mMessenger == null) {
            mMessenger = AbsMessengerClient.create(mContext, Constant.PKG.MAIN_PKG, Constant.PKG.MAIN_CLASSNAME);
            mMessenger.addListener(this);
        }
        return mMessenger;
    }


    @Override
    public void onResponse(RouterRequest response) {
        Log.d(TAG, "onResponse() called with: response = [" + response + "]");
    }

    @Override
    public void onDisconnected() {
        Log.e(TAG, "onDisconnected: ");
    }

    @Override
    public void onConnected() {
        Log.d(TAG, "onConnected: ");
        ToastUtils.showShort("建立连接");
    }

    @Override
    public void init(Context context) {
        mContext = context;
    }

    @Override
    public void destroy(Context context) {
        if (mMessenger != null) {
            mMessenger.request(RequestUtil.mainRouterBuilder(Constant.ACTION.MAIN.EXIT, null, null));
            mMessenger.removeListener(this);
            mMessenger.doDestroy(context);
        }

    }

    //=========
    public void doSendHello() {
        //向服务端发送hello
        Log.d(TAG, "1. 从客户端发送消息 ==> doSendHello: ");
        if (getMessenger() != null) {
            getMessenger().request(RequestUtil.mainRouterBuilder(Constant.ACTION.MAIN.HELLO, null, null));
        }

    }
}
