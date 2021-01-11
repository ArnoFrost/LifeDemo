package com.arno.demo.life.router.base;

import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.CallSuper;

/**
 * Created by chentao on 16/7/1.
 * actions store for recovery
 * when service disconnect
 */
public abstract class PendingAction implements Runnable {
    private static final String TAG = Constant.PRE.PRE_FIX + "PendingAction";
    private String key;

    private PendingActionType pendingActionType;

    public enum PendingActionType {
        Normal, //普通插入类型
        Replace, //替换之前key相同的action
    }

    public PendingAction() {
        pendingActionType = PendingActionType.Replace;
        key = String.valueOf(SystemClock.elapsedRealtime());
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public void setType(PendingActionType pendingActionType) {
        this.pendingActionType = pendingActionType;
    }

    public PendingActionType getType() {
        return this.pendingActionType;
    }

    @Override
    @CallSuper
    public void run() {
        Log.d(TAG, "4. 调度完成开始正式向server请求数据,通过messenger");
    }
}
