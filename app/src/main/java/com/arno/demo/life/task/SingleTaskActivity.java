package com.arno.demo.life.task;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.arno.demo.life.R;
import com.arno.demo.life.utils.LifeUtil;

public class SingleTaskActivity extends BaseTaskActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
        new LifeUtil(TAG, this);
    }

    @Override
    public void doStart(View view) {
        startActivity(new Intent(this, SingleTaskActivity.class));
    }

}