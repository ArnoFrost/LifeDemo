package com.arno.demo.life.sys.conflict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.arno.demo.life.R;

public class ConflictLearnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conflict_learn);
    }

    public void goSRL_RV_SameOuter(View view) {
        startActivity(new Intent(this, Scroller_Recycler_Same_Outer.class));
    }

    public void goSRL_RV_SameInner(View view) {
        startActivity(new Intent(this, Scroller_Recycler_Same_Inner.class));
    }
}