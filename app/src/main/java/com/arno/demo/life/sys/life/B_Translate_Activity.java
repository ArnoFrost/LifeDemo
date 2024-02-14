package com.arno.demo.life.sys.life;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.arno.demo.life.R;

public class B_Translate_Activity extends Activity {
    private static final String TAG = "LifeUtil:B_Translate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_life_b_translate);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }


    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}