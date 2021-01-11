package com.arno.demo.life.annotation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.arno.demo.life.R;
import com.arno.demo.life.annotation.ann.ClassPolicy;

@ClassPolicy
public class AnnotationLearnActivity extends AppCompatActivity {
    private static final String TAG = "AnnotationLearnActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
    }
}