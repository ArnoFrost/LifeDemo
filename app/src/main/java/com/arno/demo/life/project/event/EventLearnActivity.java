package com.arno.demo.life.project.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.arno.demo.life.R;
import com.arno.demo.life.project.event.bean.HelloEvent;
import com.arno.demo.life.project.event.custom.Subscription;
import com.arno.demo.life.project.event.custom.WeXinUser;
import com.arno.demo.life.project.event.livebus.LiveDataBus;
import com.arno.demo.life.project.event.rxbus.RxBus;
import com.blankj.utilcode.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Random;

public class EventLearnActivity extends AppCompatActivity {
    private static final String TAG = "EventLearnActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_learn);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        RxBus.getDefault().unregister(this);
    }

    /**
     * eventbus订阅事件
     *
     * @param view
     */
    public void eventBusRegist(View view) {
        EventBus.getDefault().register(this);
        ToastUtils.showShort("Event Bus 开始订阅");
    }

    /**
     * 声明主线程订阅的事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(HelloEvent event) {
        Log.d(TAG, "onMessageEvent() called with: event = [" + event + "]");
        if (event != null) {
            ToastUtils.showShort(event.toString());
        }
    }

    /**
     * eventbus发布事件
     *
     * @param view
     */
    public void eventBusPub(View view) {
        Log.d(TAG, "eventBusPub: ");
        EventBus.getDefault().post(new HelloEvent(new Random().nextInt(), String.valueOf(System.currentTimeMillis())));
    }

    public void eventBusUnregist(View view) {
        EventBus.getDefault().unregister(this);
        ToastUtils.showShort("Event Bus 取消订阅");
    }

    /**
     * 消息订阅者
     */
    private final Subscription observable = new Subscription();
    /**
     * 被观察对象
     */
    private final WeXinUser observer = new WeXinUser("Arno");

    /**
     * 自定义观察者模式
     * ※ 控制反转
     * 被观察者添加观察者
     *
     * @param view
     */
    public void customRegister(View view) {
        ToastUtils.showShort("自定义 订阅事件");
        observable.addWeXinUser(observer);
    }

    public void customUnregister(View view) {
        boolean flag = observable.removeWeXinUser(observer);
        ToastUtils.showShort("自定义 取消订阅事件 %b", flag);
    }

    public void customPub(View view) {
        //被观察者通知观察者
        observable.notify("RandomString = " + System.currentTimeMillis());
    }

    public void rxBusRegist(View view) {
        RxBus.getDefault().subscribe(this, new RxBus.Callback<HelloEvent>() {
            @Override
            public void onEvent(HelloEvent event) {
                ToastUtils.showShort(event.toString());
            }
        });
        ToastUtils.showShort("RxBus 订阅事件");
    }

    public void rxBusUnregist(View view) {
        RxBus.getDefault().unregister(this);
        ToastUtils.showShort("RxBus 取消订阅事件");
    }

    public void rxBusPub(View view) {
        Log.d(TAG, "rxBusPub: ");
        RxBus.getDefault().post(new HelloEvent(new Random().nextInt(), String.valueOf(System.currentTimeMillis())));
    }


    public void liveBusRegist(View view) {
        LiveDataBus.get()
                .with("test", HelloEvent.class)
                .observe(this, new Observer<HelloEvent>() {
                    @Override
                    public void onChanged(HelloEvent event) {
                        ToastUtils.showShort(event.toString());
                    }
                });
    }

    public void liveBusUnregist(View view) {
        //自动释放
    }

    public void liveBusPub(View view) {
        LiveDataBus.get().with("test").setValue(new HelloEvent(new Random().nextInt(), String.valueOf(System.currentTimeMillis())));
    }
}