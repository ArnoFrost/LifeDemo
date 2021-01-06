package com.arno.demo.life.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.arno.demo.life.R;

public class ViewLearnActivity extends AppCompatActivity {
    private static final String TAG = "ViewLearnActivity";
//    private CustomView customView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_learn);

//        customView = findViewById(R.id.custom);
        button = findViewById(R.id.btn);

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
                return false;
            }
        });
    }
}