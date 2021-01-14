package com.arno.demo.life.binder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.arno.demo.life.R;
import com.arno.demo.life.binder.bean.Book;
import com.blankj.utilcode.util.ToastUtils;

import java.util.List;
import java.util.Random;

public class BinderLearnActivity extends AppCompatActivity {
    private static final String TAG = "BinderLearn" + "Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binder_learn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeListener(null);
        doUnbindService(null);
        stopCustomService(null);
    }

    public void doBindService(View view) {
        Log.d(TAG, "doBindService: ");
        Intent intent = new Intent(this, RemoteService.class);
        this.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void doUnbindService(View view) {
        Log.d(TAG, "doUnbindService: ");
        if (hasBind) {
            try {
                this.unbindService(mServiceConnection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    public void getBook(View view) {
        if (hasBind && bookManager != null && bookManager.asBinder().isBinderAlive()) {
            try {
                List<Book> bookList = bookManager.getBookList();
                Log.d(TAG, "getBook() called with: bookList = [" + bookList + "]");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private int i = 3;

    public void addBook(View view) {
        if (hasBind && bookManager != null && bookManager.asBinder().isBinderAlive()) {
            Book book = new Book(i++, String.valueOf(new Random().nextDouble()));
            Log.d(TAG, "addBook: " + book);
            try {
                bookManager.addBook(book);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void addListener(View view) {
        Log.d(TAG, "addListener: ");
        if (hasBind && bookManager != null && bookManager.asBinder().isBinderAlive()) {
            try {
                boolean flag = bookManager.addStateListener(listener);
                Log.d(TAG, "addListener: 是否成功 " + flag);
                ToastUtils.showShort("addListener: 是否成功 " + flag);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeListener(View view) {
        Log.d(TAG, "removeListener: ");
        if (hasBind && bookManager != null && bookManager.asBinder().isBinderAlive()) {
            try {
                boolean flag = bookManager.removeStateListener(listener);
                Log.d(TAG, "removeListener: 是否成功 " + flag);
                ToastUtils.showShort("removeListener: 是否成功 " + flag);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean hasBind = false;
    private IBookManager bookManager;
    private final IBookStateListener listener = new IBookStateListener.Stub() {
        @Override
        public void onNewBookArrived(Book book) throws RemoteException {
            Log.d(TAG, "客户端 onNewBookArrived() called with: book = [" + book + "]");
            ToastUtils.showShort("客户端 onNewBookArrived() called with: book = [" + book + "]");
        }
    };
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected() called with: name = [" + name + "], service = [" + service + "]");
            ToastUtils.showShort("服务已绑定");
            hasBind = true;
            bookManager = IBookManager.Stub.asInterface(service);
            try {
                //建立死亡代理
                service.linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected() called with: name = [" + name + "]");
            ToastUtils.showShort("服务已解绑");
            hasBind = false;
//            bookManager = null;
        }
    };

    private final IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.d(TAG, "binderDied: ");
            if (bookManager == null) {
                return;
            }
            bookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            bookManager = null;
            //TODO 可以重建建立连接...
            ToastUtils.showShort("连接已断开");
        }
    };

    public void startCustomService(View view) {
        Log.d(TAG, "startService: ");
        Intent intent = new Intent(this, RemoteService.class);
        startService(intent);
    }

    public void stopCustomService(View view) {
        Log.d(TAG, "stopService: ");
        Intent intent = new Intent(this, RemoteService.class);
        stopService(intent);
    }
}