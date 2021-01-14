package com.arno.demo.life.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.Guideline;
import androidx.core.content.ContextCompat;
import androidx.transition.AutoTransition;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.arno.demo.life.R;

/**
 * TODO: document your custom view class.
 */
public class SwitchButton extends ConstraintLayout {
    private boolean curState = false;
    private final ConstraintSet animSet = new ConstraintSet();

    private final TextView title1;
    private final TextView title2;
    private final View button;
    private final ConstraintLayout root;
    private final Guideline guideline;

    public SwitchButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.wiget_switch_button, this);

        //初始化控件
        title1 = findViewById(R.id.title1);
        title2 = findViewById(R.id.title2);
        button = findViewById(R.id.ivButton);
        //按钮根布局
        root = findViewById(R.id.root);
        guideline = findViewById(R.id.guideline);

        switchButton();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switchButton();
        }
        return super.onTouchEvent(event);
    }

    private void switchButton() {
        //更改状态
        curState = !curState;
        performAnimate(curState);
    }

//    private final ViewOutlineProvider provider = new ViewOutlineProvider() {
//        @Override
//        public void getOutline(View view, Outline outline) {
////            outline.setOval(0, 0, view.getWidth(), view.getHeight());
////            outline.setAlpha();
//        }
//    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        button.setOutlineProvider(provider);
    }

    private void performAnimate(boolean state) {
        this.curState = state;
        //复制现在的布局形式
        animSet.clone(root);

        animSet.clear(R.id.ivButton);
        //如果是1的样式
        if (curState) {
            //约束到title1
            animSet.connect(R.id.ivButton, ConstraintSet.START, R.id.root, ConstraintSet.START, 10);
            animSet.connect(R.id.ivButton, ConstraintSet.END, R.id.guideline, ConstraintSet.START);

            //改变颜色
            title1.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            title2.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_400));

        } else {//如果是样式2
            //约束到title2
            animSet.connect(R.id.ivButton, ConstraintSet.START, R.id.guideline, ConstraintSet.END);
            animSet.connect(R.id.ivButton, ConstraintSet.END, R.id.root, ConstraintSet.END, 10);

            //改变颜色
            title1.setTextColor(ContextCompat.getColor(getContext(), R.color.gray_400));
            title2.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        }

        //居中对齐
        animSet.connect(R.id.ivButton, ConstraintSet.TOP, R.id.root, ConstraintSet.TOP);
        animSet.connect(R.id.ivButton, ConstraintSet.BOTTOM, R.id.root, ConstraintSet.BOTTOM);

        //配置动画效果
        AutoTransition transition = new AutoTransition();
        transition.setDuration(200);
        transition.addListener(listener);
        TransitionManager.beginDelayedTransition(root, transition);
        //执行动画效果
        animSet.applyTo(root);
    }

    private final Transition.TransitionListener listener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(@NonNull Transition
                                              transition) {
            //通知变更
            if (onCheckStateListener != null) {
                onCheckStateListener.onChange(curState);
            }
        }

        @Override
        public void onTransitionEnd(@NonNull Transition
                                            transition) {

        }

        @Override
        public void onTransitionCancel(@NonNull Transition
                                               transition) {

        }

        @Override
        public void onTransitionPause(@NonNull Transition
                                              transition) {

        }

        @Override
        public void onTransitionResume(@NonNull Transition
                                               transition) {

        }
    };
    private OnCheckStateListener onCheckStateListener;

    public void setOnCheckStateListener(OnCheckStateListener onCheckStateListener) {
        this.onCheckStateListener = onCheckStateListener;
    }

    public interface OnCheckStateListener {
        void onChange(boolean state);
    }
}