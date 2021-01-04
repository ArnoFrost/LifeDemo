package com.arno.demo.life.ground;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.arno.demo.life.R;
import com.arno.demo.life.utils.ActivityLifeCycleUtil;

public class B_Activity extends AppCompatActivity {
    private static final String TAG = "B_Activity";
    private ActivityLifeCycleUtil lifeCycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_b);
        lifeCycle = new ActivityLifeCycleUtil(TAG, this);
    }
}