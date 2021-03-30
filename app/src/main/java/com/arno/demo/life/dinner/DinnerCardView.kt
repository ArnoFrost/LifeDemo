package com.arno.demo.life.dinner

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.cardview.widget.CardView


class DinnerCardView(context: Context, attrs: AttributeSet) : CardView(context, attrs) {

    private val mPaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
        }
    }

    private val circleXFerMode: Xfermode by lazy {
        PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
    }

    private val backGroundRect: Rect by lazy {
        Rect(0, 0, width, height)
    }

    private val circleRadius = 50F
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //1。 存档
        val saved = canvas?.saveLayer(null, null, Canvas.ALL_SAVE_FLAG)
        mPaint.color = Color.GREEN
        canvas?.drawRect(backGroundRect, mPaint)

        //2.叠加 扣除圆形
        mPaint.xfermode = circleXFerMode
        canvas?.drawCircle(circleX(true), circleY(true), circleRadius, mPaint)
        canvas?.drawCircle(circleX(false), circleY(false), circleRadius, mPaint)

        //3. 清除
        mPaint.xfermode = null
        canvas?.restoreToCount(saved!!)

    }

    private fun circleY(isTop: Boolean): Float {
        return if (isTop) {
            0F
        } else {
            height.toFloat()
        }

    }

    private fun circleX(isTop: Boolean): Float {
        return (width * 0.75).toFloat()
    }


}