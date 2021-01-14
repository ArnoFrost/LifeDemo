package com.arno.demo.life.binder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;

import com.arno.demo.life.binder.bean.Book;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RemoteService extends Service {
    private static final String TAG = "BinderLearn" + "Service";
    private final CopyOnWriteArrayList<Book> mList = new CopyOnWriteArrayList<>();
    private final RemoteCallbackList<IBookStateListener> stateList = new RemoteCallbackList<>();

    public RemoteService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mList.add(new Book(1, "Android"));
        mList.add(new Book(2, "Arno"));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private final IBookManager.Stub mBinder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.d(TAG, "服务端 getBookList: ");
            return mList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d(TAG, "服务端 addBook: ");
            if (!mList.contains(book)) {
                mList.add(book);

                final int N = stateList.beginBroadcast();
                for (int i = 0; i < N; i++) {
                    IBookStateListener broadcastItem = stateList.getBroadcastItem(i);
                    broadcastItem.onNewBookArrived(book);
                }
                stateList.finishBroadcast();
            }
        }

        @Override
        public boolean addStateListener(IBookStateListener listener) throws RemoteException {
            Log.d(TAG, "服务端 addStateListener: ");
            return stateList.register(listener);
        }

        @Override
        public boolean removeStateListener(IBookStateListener listener) throws RemoteException {
            Log.d(TAG, "服务端 removeStateListener: ");
            return stateList.unregister(listener);
        }
    };
}