package com.arno.demo.life.project.router.module.media;

import android.util.Log;

import com.arno.demo.life.project.router.core.server.AbsMessengerService;
import com.arno.demo.life.project.router.base.Constant;
import com.arno.demo.life.project.router.base.RouterRequest;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;

public class MediaAIDLService extends AbsMessengerService {
    private static final String TAG = "MediaAIDLService";


    @Override
    protected void invokeMsg(RouterRequest request) {
        if (request != null) {
            String action = request.getAction();
            if (StringUtils.isEmpty(action)) {
                return;
            }
            if (StringUtils.equals(action, Constant.ACTION.MEDIA.PLAY)) {
                doPlay();
            } else if (StringUtils.equals(action, Constant.ACTION.MEDIA.PAUSE)) {
                doPause();
            }
        }
    }

    private void doPlay() {
        Log.d(TAG, "doPlay: ");
        ToastUtils.showShort("do Play");
    }

    private void doPause() {
        Log.d(TAG, "doPause: ");
        ToastUtils.showShort("do Pause");
    }
}
