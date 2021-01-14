package com.arno.demo.life.hook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arno.demo.life.R;
import com.arno.demo.life.utils.LifeUtil;

import java.util.Random;

public class HookLearnActivity extends HookActivity {
    private static final String TAG = "HookLearnActivity" + new Random().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook_learn);
        new LifeUtil(TAG, this);
        setHook();
    }

    public void startPage(View view) {
        Intent intent = new Intent(this, HookLearnActivity.class);
        startActivity(intent);
    }
}