package com.arno.demo.life.base.utils

import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView


fun RecyclerView.addOnItemClickListener(onClick: (position: Int) -> Unit) {
    val gestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean = true
        })

    this.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            val childView = rv.findChildViewUnder(e.x, e.y)
            if (childView != null && gestureDetector.onTouchEvent(e)) {
                val position = rv.getChildAdapterPosition(childView)
                onClick(position)
                return true
            }
            return false
        }
    })
}

//'为 RecyclerView 扩展表项点击监听器'
fun RecyclerView.setOnItemClickListener(listener: (View, Int) -> Unit) {
    //'为 RecyclerView 子控件设置触摸监听器'
    addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
        //'构造手势探测器，用于解析单击事件'
        val gestureDetector = GestureDetector(context, object :
            GestureDetector.SimpleOnGestureListener() {

            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                //'当单击事件发生时，寻找单击坐标下的子控件，并回调监听器'
                e?.let {
                    findChildViewUnder(it.x, it.y)?.let { child ->
                        listener(child, getChildAdapterPosition(child))
                    }
                }
                return false
            }
        })

        //'在拦截触摸事件时，解析触摸事件'
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
            gestureDetector.onTouchEvent(e)
            return false
        }
    })
}
