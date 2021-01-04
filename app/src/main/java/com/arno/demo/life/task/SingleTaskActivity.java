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

    /**
     * 使用singleTask如果要启动自己的任务栈
     * 一定要显示指定TaskAffinity与allowTaskReparenting参数
     * <p>
     * 如果 taskAffinity 的值为空或者包名则与默认一样 不会创建新的任务栈
     * allowTaskReparenting则表示这个活动是否可以以切换到新的任务栈,通常与taskAffinity一起使用并设置为true
     *
     * @param view
     */
    @Override
    public void doStart(View view) {
        startActivity(new Intent(this, SingleTaskActivity.class));
        //通过代码启动singleTask模式
//        Intent intent = new Intent();
//        intent.setClass(this, SingleInstanceActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(intent);
    }

}