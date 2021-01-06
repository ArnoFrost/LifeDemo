package com.arno.demo.life.conflict;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arno.demo.life.R;
import com.arno.demo.life.utils.DataUtil;

public class Scroller_Recycler_Same_Outer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_recycler_same_outer);
        doInit();
    }

    private void doInit() {
        /**
         * 外部拦截方法 主要是处理父布局的拦截,让其"放过"不关注的事件到子view
         * ※注意不要拦截up 和down事件 一旦拦截 子view将无法消费此次事件
         * 而是直接在判断move后去消费move事件
         * 自定义ScrollView
         */
        RecyclerView recyclerView = findViewById(R.id.rvView);
        SimpleAdapter simpleAdapter = new SimpleAdapter(DataUtil.getStringData());
        //改用横向滑动处理
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(simpleAdapter);

    }
}