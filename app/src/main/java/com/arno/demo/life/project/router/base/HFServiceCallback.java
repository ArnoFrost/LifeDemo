package com.arno.demo.life.project.router.base;

import android.content.ComponentName;
import android.os.IBinder;

/**
 * Created by chentao on 16/6/20.
 */
public interface HFServiceCallback {

     void onServiceConnected(ComponentName name, IBinder service);

     void onServiceDisconnected(ComponentName name);
}
