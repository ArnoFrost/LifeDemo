package com.arno.demo.life.sys.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arno.demo.life.R;
import com.arno.demo.life.base.utils.LifeUtil;

public class TaskLearnActivity extends AppCompatActivity {
    private final String TAG = "TaskLearnActivity-->" + hashCode();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        new LifeUtil(TAG, this);
    }

    public void startStandard(View view) {
        startActivity(new Intent(this, StandardActivity.class));
    }

    public void startSingleTop(View view) {
        startActivity(new Intent(this, SingleTopActivity.class));

    }

    public void startSingleInstance(View view) {
        startActivity(new Intent(this, SingleInstanceActivity.class));

    }

    public void startSingleTask(View view) {
        startActivity(new Intent(this, SingleTaskActivity.class));

    }


}