package com.arno.demo.life.sys.life;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.arno.demo.life.R;
import com.arno.demo.life.base.utils.LifeUtil;

public class B_Activity extends AppCompatActivity {
    private static final String TAG = "B_Activity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_b);
        new LifeUtil(TAG, this);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }
}