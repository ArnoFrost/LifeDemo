package com.arno.demo.life.sys.pref;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arno.demo.life.R;

public class ViewPrefLearnActivity extends AppCompatActivity {
    private static final String TAG = "ViewPrefLearnActivity";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AsyncLayoutInflater(this).inflate(R.layout.activity_view_pref_learn, null, new AsyncLayoutInflater.OnInflateFinishedListener() {
            @Override
            public void onInflateFinished(@NonNull View view, int resid, @Nullable ViewGroup parent) {
                Log.d(TAG, "onInflateFinished: ");
                setContentView(view);
                textView = findViewById(R.id.tvTitle);
                textView.post(new Runnable() {
                    @Override
                    public void run() {
                        Looper looper = Looper.myLooper();
                        Log.d(TAG, "run() called looper = " + looper);
                        textView.setText("变更");
                    }
                });


            }
        });
    }
}