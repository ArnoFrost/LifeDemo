package com.arno.demo.life.task;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseTaskActivity extends AppCompatActivity {
    String TAG = this.getClass().getSimpleName() + "-->" + hashCode();

    private static final boolean isLogDebug = true;

    public abstract void doStart(View view);

    public void doStartOther(View view) {
        startActivity(new Intent(this, TaskLearnActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isLogDebug) {
            getActivityTaskInfo();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(TAG, "LifeUtil onNewIntent: ");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (isLogDebug) {
            Log.d(TAG, "onPostCreate: ");
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (isLogDebug) {
            Log.d(TAG, "onPostResume: ");
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        if (isLogDebug) {
            Log.d(TAG, "onContentChanged: ");
        }
    }

    /**
     * 打印任务栈通过adb命令 adb shell dumpsys activity activities | grep -iE "com.arno.demo.life | Hist"
     */
    protected void getActivityTaskInfo() {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo runningTaskInfo = manager.getRunningTasks(1).get(0);
        //栈内activity数量
        int numActivities = runningTaskInfo.numActivities;
        //taskId
        int id = runningTaskInfo.id;
        ComponentName topActivity = runningTaskInfo.topActivity;

//        Log.e("activityTask", "getActivityTaskInfo: " + runningTaskInfo.toString());
        //栈顶activity信息
        String className = topActivity.getClassName();
        Log.e("activityTask", "id == " + id + "\n" + "numActivity == " + numActivities + "\n" + "className == " + className);
    }
}
