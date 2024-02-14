package com.arno.demo.life.sys.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arno.demo.life.R;
import com.arno.demo.life.base.utils.LifeUtil;

public class SingleInstanceActivity extends BaseTaskActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_instance);
        new LifeUtil(TAG, this);
    }

    /**
     * 注意 当设置成为singleInstance模式后
     * 再次启动其他的页面任务栈不同,返回后 除非启动页面的任务栈没有了
     * 才会再次返回singleInstance的任务栈中 这点需要注意
     * @param view
     */
    public void doStart(View view) {
        startActivity(new Intent(this, SingleInstanceActivity.class));
    }

}