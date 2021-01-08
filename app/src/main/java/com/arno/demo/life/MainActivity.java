package com.arno.demo.life;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.arno.demo.life.conflict.ConflictLearnActivity;
import com.arno.demo.life.event.EventLearnActivity;
import com.arno.demo.life.life.LifeLearnActivity;
import com.arno.demo.life.loader.ClassLoaderActivity;
import com.arno.demo.life.task.TaskLearnActivity;
import com.arno.demo.life.view.ViewLearnActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToLife(View view) {
        startActivity(new Intent(this, LifeLearnActivity.class));
    }

    public void goToTask(View view) {
        startActivity(new Intent(this, TaskLearnActivity.class));
    }

    public void goToView(View view) {
        startActivity(new Intent(this, ViewLearnActivity.class));
    }

    public void goToConflict(View view) {
        startActivity(new Intent(this, ConflictLearnActivity.class));
    }

    public void goToLoader(View view) {
        startActivity(new Intent(this, ClassLoaderActivity.class));
    }

    public void goToEvent(View view) {
        startActivity(new Intent(this, EventLearnActivity.class));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private long exitTime = 0;

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }



}