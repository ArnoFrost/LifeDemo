package com.arno.demo.life.conflict;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arno.demo.life.R;
import com.arno.demo.life.utils.DataUtil;

public class Scroller_Recycler_Same_Inner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_recycler_same_inner);
        doInit();
    }

    private void doInit() {
        // 内部拦截法 要求子view自己处理拦截
        // 通过requestDisallowInterceptTouchEvent处理
//        MyRecyclerViewVerticalHorizontal recyclerView = findViewById(R.id.rvView);
        MyRecyclerViewBothVertical recyclerView = findViewById(R.id.rvView);

        SimpleAdapter simpleAdapter = new SimpleAdapter(DataUtil.getStringData());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(simpleAdapter);

    }
}