package com.arno.demo.life.router.core.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.arno.demo.life.router.base.Constant;
import com.arno.demo.life.router.base.HFServiceCallback;
import com.arno.demo.life.router.base.PendingAction;
import com.arno.demo.life.router.base.RouterRequest;
import com.blankj.utilcode.util.ToastUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class AbsMessengerClient {
    private static final String TAG = Constant.PRE.PRE_FIX + "AbsMsgClient";
    private String mPkgName;
    private String mClassName;
    /**
     * 重试Handler
     */
    protected Handler innerHandler = new Handler(Looper.getMainLooper());


    private String getClassName() {
        return mClassName;
    }

    private String getPkgName() {
        return mPkgName;
    }

    private int mRetryCount = 0;
    private final int DELAY_TIME = 3000;

    private Context mContext;

    public AbsMessengerClient() {
    }

    public AbsMessengerClient(String mPkgName, String mClassName) {
        this.mPkgName = mPkgName;
        this.mClassName = mClassName;
    }

    /**
     * 创建
     *
     * @param context
     */
    public void doCreate(Context context) {
        getClientMessenger();
        mContext = context;
        if (mServerMessenger == null) {
            mRetryCount = 0;
            tryBind(context);
        }
    }

    /**
     * 销毁
     *
     * @param context
     */
    public void doDestroy(Context context) {
        clearQueue();
        if (mServerMessenger != null) {
            unbindCustomService(context);
            mServerMessenger = null;
        }

        if (mClientHandler != null) {
            mClientHandler.removeCallbacksAndMessages(null);
            if (mClientHandler.getLooper() != null) {
                mClientHandler.getLooper().quit();
            }

            mClientHandler = null;
        }
        //销毁Handler线程
        if (thread != null) {
            thread.quitSafely();
        }

        mRetryCount = 0;
    }

    /**
     * 请求服务端数据
     *
     * @param request
     */
    public void request(@NonNull final RouterRequest request) {
        Log.d(TAG, "2. client抽象类 调度管理消息 request: ");
        //创建统一消息体
        PendingAction pendingAction = new PendingAction() {
            @Override
            public void run() {
                //放置数据
                super.run();
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.PARAM.REQUEST, request);

                //将客户端Messenger建立关联
                Message message = Message.obtain(null, Constant.CODE.MSG_REQUEST, 0, 0);
                message.setData(bundle);
                message.replyTo = mClientMessenger;

                //向服务端发送数据
                try {
                    mServerMessenger.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
        final String key = getClassName() + "_request_" + request;
        pendingAction.setKey(key);
        pendingAction.setType(PendingAction.PendingActionType.Replace);

        //进入消息分发
        disPatchTask(pendingAction);
    }

    private void disPatchTask(PendingAction pendingAction) {
        if (mServerMessenger != null) {
            pendingAction.run();
        } else {
            enqueueAction(pendingAction);
        }
    }

    private static class PkgInfo {
        private final String mPkgName;
        private final String mClassName;

        private AbsMessengerClient mClientMessenger;

        private PkgInfo(String pkgName, String clsName) {
            this.mPkgName = pkgName;
            this.mClassName = clsName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PkgInfo info = (PkgInfo) o;
            return TextUtils.equals(mPkgName, info.mPkgName) &&
                    TextUtils.equals(mClassName, info.mClassName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(mPkgName, mClassName);
        }
    }


    private static final List<PkgInfo> mLists = new ArrayList<>();

    public static AbsMessengerClient create(Context context, String pkgName, String className) {
        AbsMessengerClient absMessengerClient = getOrCreateClientServer(pkgName, className);
        if (absMessengerClient.getServiceSate() == ServiceState.Stopped || absMessengerClient.getServiceSate() == ServiceState.Init) {
            absMessengerClient.doCreate(context);
        }

        return absMessengerClient;
    }

    /**
     * 创建或者从列表中复用指定的clientServer对象
     *
     * @param pkgName
     * @param className
     * @return
     */
    private static AbsMessengerClient getOrCreateClientServer(String pkgName, String className) {
        PkgInfo info = new PkgInfo(pkgName, className);
        AbsMessengerClient client = null;
        for (PkgInfo pkgInfo : mLists) {
            if (pkgInfo.equals(info)) {
                client = pkgInfo.mClientMessenger;
                break;
            }
        }
        if (client == null) {
            client = new AbsMessengerClient(pkgName, className);
            info.mClientMessenger = client;
            mLists.add(info);
        }
        return client;
    }

    /**
     * Messenger响应回调
     */
    public interface IResponseListener {
        void onResponse(RouterRequest response);

        void onDisconnected();

        void onConnected();
    }

    /**
     * 服务端Messenger
     */
    private Messenger mServerMessenger;
    /**
     * 客户端Messenger
     */
    private Messenger mClientMessenger;


    /**
     * 客户端消息处理handler
     */
    private ClientMsgHandler mClientHandler;

    private static class ClientMsgHandler extends Handler {
        public ClientMsgHandler(@NonNull Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d(TAG, "8. 最终客户端收到服务端应答完成此次IPC过程 handleMessage: reply = " + msg);
            if (msg.what == Constant.CODE.MSG_REPLY) {
                Bundle data = msg.getData();
                if (data != null) {
                    //获得返回对象
                    RouterRequest response = data.getParcelable(Constant.PARAM.RESPONSE);
                    if (response != null) {
                        if (TextUtils.equals(response.getAction(), Constant.ACTION.MAIN.HELLO_REPLY)) {
                            Log.e(TAG, "handleMessage: 收到服务端发来的消息 HELLO_REPLY==========");
                        }
                    }
                    //通知收到响应
                    notifyResponse(response);
                }

            }
        }
    }

    private static final CopyOnWriteArrayList<IResponseListener> mIResponseListeners = new CopyOnWriteArrayList<>();


    public void addListener(IResponseListener listener) {
        if (listener != null) {
            if (!mIResponseListeners.contains(listener)) {
                mIResponseListeners.add(listener);
                Log.d(TAG, "add listener:" + listener.getClass().getName() + ", add:" + listener.toString() + ",size:" + mIResponseListeners.size());
            } else {
                Log.d(TAG, "contain listener:" + listener.getClass().getName());
            }
        }

    }

    public void removeListener(IResponseListener listener) {
        mIResponseListeners.remove(listener);
    }

    private HandlerThread thread;

    private Messenger getClientMessenger() {
        if (mClientHandler == null || mClientMessenger == null) {
            if (thread != null) {
                thread.quitSafely();
            }
            thread = new HandlerThread("ClientMessenger-HandlerThread");
            thread.start();

            mClientHandler = new ClientMsgHandler(thread.getLooper());
            mClientMessenger = new Messenger(mClientHandler);
        }

        return mClientMessenger;
    }

    private void tryBind(final Context context) {
        if (mRetryCount >= 10) {
            Log.e(TAG, "tryBind failed !!!");
            ToastUtils.showShort("绑定服务失败 请检查是否绑定正确 pkgName = " + getPkgName() + ",className = " + getClassName());
            return;
        }
        mRetryCount++;
        bindCustomService(context, getPkgName(), getClassName(), mCallback);
        mClientHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tryBind(context);
            }
        }, DELAY_TIME);
    }

    private final HFServiceCallback mCallback = new HFServiceCallback() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "onServiceConnected. pid:" + android.os.Process.myPid() + ", " + AbsMessengerClient.this);
            mServerMessenger = new Messenger(service);
            mClientHandler.removeCallbacksAndMessages(null);
            mRetryCount = 0;

            //通知连接上
            notifyConnect();
        }

        private final Runnable reBindRunnable = new Runnable() {
            @Override
            public void run() {
                tryBind(mContext);
            }
        };

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "onServiceDisconnected. pid:" + android.os.Process.myPid() + ", " + AbsMessengerClient.this);
            mServerMessenger = null;

            mClientHandler.removeCallbacks(reBindRunnable);
            mClientHandler.postDelayed(reBindRunnable, DELAY_TIME);

            //通知断开
            notifyDisconnect();
        }
    };


    //================
    private void notifyConnect() {
        for (IResponseListener listener : mIResponseListeners) {
            listener.onConnected();
        }
    }

    private static void notifyResponse(RouterRequest response) {
        for (IResponseListener listener : mIResponseListeners) {
            Log.d(TAG, "listener:" + listener.getClass().getName() + ", add:" + listener.toString());
            listener.onResponse(response);
        }
    }

    private void notifyDisconnect() {
        for (IResponseListener listener : mIResponseListeners) {
            listener.onDisconnected();
        }
    }
    //===============

    /**
     * 状态描述
     */
    public enum ServiceState {
        Init,//初始化默认状态
        Binding,//在等待绑定中
        Running,//在运行中
        Stopped,//已停止
    }

    /**
     * 状态描述记录
     */
    private ServiceState mState = ServiceState.Init;

    private ClientServiceConnection mServiceConnection;


    public String getName() {
        return null;
    }

    private boolean hasBind;

    private class ClientServiceConnection implements ServiceConnection {

        private final HFServiceCallback callback;

        public ClientServiceConnection(HFServiceCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            hasBind = true;
            setServiceState(ServiceState.Running);
            stopBindTimer(true);
            if (callback != null) {
                callback.onServiceConnected(name, service);
            }

            innerHandler.removeCallbacks(reBindRunnable);
            doTaskInQueue();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            setServiceState(ServiceState.Stopped);
            stopBindTimer(true);

            if (callback != null) {
                callback.onServiceDisconnected(name);
            }

            //rebind service
            innerHandler.removeCallbacks(reBindRunnable);
            innerHandler.postDelayed(reBindRunnable, 500);

            hasBind = false;

        }
    }

    private final Runnable reBindRunnable = new Runnable() {
        @Override
        public void run() {
            doCreate(mContext);
        }
    };


    private volatile boolean isRecursive = false;
    //维护任务队列 FIXME 看是否能替换其他任务队列 如ArrayDeque
    private final LinkedHashMap<String, PendingAction> mQueue = new LinkedHashMap<>();

    /**
     * 将队列中的滞留消息做遍历请求
     */
    protected void doTaskInQueue() {
        //如果在递归中则只递归一次
        if (isRecursive) {
            return;
        }

        synchronized (mQueue) {
            if (mQueue.size() == 0) {
                return;
            }
            //变更通知
            isRecursive = true;
            //将队列中所有的消息执行一次
            travelsQueue();
            //置回标记
            isRecursive = false;
        }

    }

    protected void travelsQueue() {
        Log.d(TAG, "travelsQueue: ");
        for (Map.Entry<String, PendingAction> entry : mQueue
                .entrySet()) {
            PendingAction pendingAction = entry.getValue();
            pendingAction.run();
        }
    }

    private void removeTask(String taskName) {
        mQueue.remove(taskName);
    }

    protected void clearQueue() {
        synchronized (mQueue) {
            mQueue.clear();
        }
    }

    /**
     * 入队执行
     *
     * @param action
     */
    protected void enqueueAction(@NonNull PendingAction action) {
        Log.d(TAG, "3. 如果服务没有运行则会进入入队操作 enqueueAction() called with: action = [" + action + "]");
        synchronized (mQueue) {
            String key = action.getKey();
            PendingAction.PendingActionType type = action.getType();

            //如果是替换类型则进行替换
            if (type == PendingAction.PendingActionType.Replace) {
                removeTask(key);
            }

            //入队
            mQueue.put(key, action);

            if (getServiceSate() == ServiceState.Stopped || getServiceSate() == ServiceState.Init) {
                //如果没有绑定成功则进行创建操作
                doCreate(mContext);
            }
        }
    }


    private void setServiceState(ServiceState state) {
        this.mState = state;
    }

    public ServiceState getServiceSate() {
        return mState;
    }


    private final BindFailedRunnable mBindFailedRunnable = new BindFailedRunnable();

    private class BindFailedRunnable implements Runnable {
        private int retry;
        private final int MAXCOUNT = 5;
        private static final int TIMEOUT = 30 * 1000;

        @Override
        public void run() {
            if (retry < MAXCOUNT) {
                Log.e(TAG, "bind timeout, retry:" + retry + " ." + AbsMessengerClient.this.getName());
                setServiceState(ServiceState.Init);
                retry++;
            } else {
                Log.e(TAG, "bind Failed timeout!!!! " + AbsMessengerClient.this.getName());
            }
        }

        private void reset() {
            if (retry != 0) {
                retry = 0;
                Log.e(TAG, "reset bind retry count. " + AbsMessengerClient.this.getName());
            }
        }
    }

    private void startBindTimer() {
        innerHandler.removeCallbacks(mBindFailedRunnable);
        innerHandler.postDelayed(mBindFailedRunnable, BindFailedRunnable.TIMEOUT);
        Log.d(TAG, ">>start Bind Timer. " + this.getName());
    }

    private void stopBindTimer(boolean reset) {
        if (reset) {
            mBindFailedRunnable.reset();
        }
        innerHandler.removeCallbacks(mBindFailedRunnable);
        Log.d(TAG, "<<stop Bind Timer. " + this.getName());
    }

    /**
     * 主要调用方法
     *
     * @param context
     * @param pkgName
     * @param clsName
     * @param callback
     * @return
     */
    private boolean bindCustomService(@NonNull Context context, @NonNull String pkgName, @NonNull String clsName, HFServiceCallback callback) {
        this.mContext = context;
        //开始执行绑定
        setServiceState(ServiceState.Binding);
        startBindTimer();
        if (mServiceConnection == null || mServiceConnection.callback != callback) {
            //创建ServiceConnection回调
            mServiceConnection = new ClientServiceConnection(callback);
        }
        //开始执行绑定
        Intent intent = new Intent();
        intent.setClassName(pkgName, clsName);
        return context.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 解绑服务
     *
     * @param context
     */
    private void unbindCustomService(@NonNull Context context) {
        mBindFailedRunnable.reset();
        innerHandler.removeCallbacks(null);
        stopBindTimer(true);
        context.unbindService(mServiceConnection);
    }


}
