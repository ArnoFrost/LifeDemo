package com.arno.demo.life.task;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.arno.demo.life.R;
import com.arno.demo.life.utils.LifeUtil;

public class SingleInstanceActivity extends BaseTaskActivity {
//    private final String TAG = "SingleInstanceActivity-->" + hashCode();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);
        new LifeUtil(TAG, this);
    }

    public void doStart(View view) {
        startActivity(new Intent(this, SingleInstanceActivity.class));

        //通过代码启动singleTask模式
        Intent intent = new Intent();
        intent.setClass(this, SingleInstanceActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}