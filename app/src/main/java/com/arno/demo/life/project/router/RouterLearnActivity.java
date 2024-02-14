package com.arno.demo.life.project.router;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.arno.demo.life.R;
import com.arno.demo.life.project.router.base.Constant;
import com.arno.demo.life.project.router.module.main.MainClient;

public class RouterLearnActivity extends AppCompatActivity {
    private static final String TAG = Constant.PRE.PRE_FIX + "Activity";
    private MainClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_router_learn);

        client = new MainClient();
        client.init(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.destroy(this);
    }


    //测试路由消息
    public void doBindService(View view) {
        Log.d(TAG, "doBindService: ");
        client.getMessenger();

    }

    public void doUnbindService(View view) {
        Log.d(TAG, "doUnbindService: ");
        client.destroy(this);
    }

    public void sendHelloToServer(View view) {
        Log.d(TAG, "sendHelloToServer: ");
        client.doSendHello();
    }
}