package com.arno.demo.life.project.router.module.main;

import android.util.Log;

import com.arno.demo.life.project.router.base.Constant;
import com.arno.demo.life.project.router.base.RouterRequest;
import com.arno.demo.life.project.router.core.server.AbsMessengerService;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

public class MainRemoteService extends AbsMessengerService {
    private static final String TAG = Constant.PRE.PRE_FIX + "MainRemoteService";


    @Override
    protected void invokeMsg(RouterRequest request) {
        if (request != null) {
            String action = request.getAction();
            if (StringUtils.isEmpty(action)) {
                return;
            }

            if (StringUtils.equals(action, Constant.ACTION.MAIN.HELLO)) {
                doHello();
            } else if (StringUtils.equals(action, Constant.ACTION.MAIN.EXIT)) {
                doExit();
            }
        }
    }

    private void doExit() {
        Log.d(TAG, "doExit: ");
        ToastUtils.showShort("do Exit");
        //回应客户端
        RouterRequest request = new RouterRequest<>();
        request.withDomain(Constant.DOMAIN.MAIN);
        request.withAction(Constant.ACTION.MAIN.EXIT_REPLY);
        replyToClient(request);
    }

    private void doHello() {
        Log.d(TAG, "6. 解析具体消息并执行 doHello: ");
        ToastUtils.showShort("do Hello");
        //回应客户端
        RouterRequest request = new RouterRequest<>();
        request.withDomain(Constant.DOMAIN.MAIN);
        request.withAction(Constant.ACTION.MAIN.HELLO_REPLY);
        replyToClient(request);
    }


}
