package com.arno.demo.life.view;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.arno.demo.life.R;
import com.arno.demo.life.utils.ViewUtil;
import com.blankj.utilcode.util.ToastUtils;

import java.util.Random;

public class ViewLearnActivity extends AppCompatActivity {
    private static final String TAG = "ViewLearnActivity";
    //    private CustomView customView;
    private Button button;
//    private SwitchButton switchButton;

    private LinearLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_learn);

//        customView = findViewById(R.id.custom);
        root = findViewById(R.id.root);
        button = findViewById(R.id.btn);
//        switchButton = findViewById(R.id.custom_button);

//        switchButton.setOnCheckStateListener(new SwitchButton.OnCheckStateListener() {
//            @Override
//            public void onChange(boolean state) {
//                ToastUtils.showShort(String.valueOf(state));
//            }
//        });

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Log.d(TAG, "onTouch() called with: v = [" + v + "], event = [" + event + "]");
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "ACTION_DOWN: ");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "ACTION_MOVE: ");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "ACTION_UP: ");
                        break;
                    default:
                        break;
                }
//                boolean flag = false;
                boolean flag = true;
                Log.d(TAG, "onTouch: flag = " + flag);
                return flag;
            }
        });
        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: ");
            }
        });

//        ViewUtil.create().applyViewRecursively(root, 0);
//        ViewUtil.create().breadthTravelView(root);
//        ViewUtil.create().depthTravelView(root);
        float density = ViewUtil.getDensity();
        ToastUtils.showShort("density = " + density);
        float dpi = ViewUtil.getDensityDpi();
        Log.d(TAG, "onCreate: density = " + density + ",dpi = " + dpi);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
    }
}