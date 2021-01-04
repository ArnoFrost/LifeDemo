package com.arno.demo.life.ground;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arno.demo.life.R;
import com.arno.demo.life.utils.ActivityLifeCycleUtil;

public class A_Activity extends AppCompatActivity {
    private static final String TAG = "A_Activity";
    private ActivityLifeCycleUtil lifeCycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ground_a);
        lifeCycle = new ActivityLifeCycleUtil(TAG, this);
    }


    public void startB(View view) {
        startActivity(new Intent(this, B_Activity.class));
    }
}