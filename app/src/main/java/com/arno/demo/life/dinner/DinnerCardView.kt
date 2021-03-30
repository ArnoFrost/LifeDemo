package com.arno.demo.life.dinner

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider


class DinnerCardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    init {
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, width, height, mCornerSize)
            }
        }
        clipToOutline = true
    }

    private val mPaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
        }
    }

    private val circleXFerMode: Xfermode by lazy {
        PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
    }

    private val backGroundRect: Rect by lazy {
        Rect(0, 0, measuredWidth, measuredHeight)
    }
    private var mCornerSize: Float = 18F

    private val backGroundRectF: RectF by lazy {
        RectF(0F, 0F, measuredWidth.toFloat(), measuredHeight.toFloat())
    }

    private var isActive = true

    private fun getStateColor(): Int {
        return return if (isActive) {
            Color.parseColor("#3BCF76")
        } else {
            Color.GRAY
        }
    }

    private val circleRadius = 50F
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawCard(canvas)
    }

    private val linePathEffect: DashPathEffect by lazy {
        DashPathEffect(floatArrayOf(8F, 4F), 0F)
    }

    /**
     * 绘制背景
     */
    private fun drawCard(canvas: Canvas?) {
        //region 绘制扣图
        //1。 存档
        val saved = canvas?.saveLayer(null, null, Canvas.ALL_SAVE_FLAG)
        mPaint.color = getStateColor()
//        canvas?.drawRect(backGroundRect, mPaint)
        canvas?.drawRoundRect(backGroundRectF, mCornerSize, mCornerSize / 2, mPaint)
        //2.叠加 扣除圆形
        mPaint.xfermode = circleXFerMode
        canvas?.drawCircle(circleX(true), circleY(true), circleRadius, mPaint)
        canvas?.drawCircle(circleX(false), circleY(false), circleRadius, mPaint)
        //3. 清除
        mPaint.xfermode = null
        canvas?.restoreToCount(saved!!)
        //endregion


        canvas?.apply {
            mPaint.color = getLineColor()
            mPaint.strokeWidth = 4F
            mPaint.pathEffect = linePathEffect
            drawLine(
                circleX(true),
                circleY(true) + circleRadius,
                circleX(false),
                circleY(false) - circleRadius,
                mPaint
            )
        }

    }

    private fun getLineColor(): Int {
        return Color.parseColor("#848887")
    }

    private fun circleY(isTop: Boolean): Float {
        return if (isTop) {
            0F
        } else {
            measuredHeight.toFloat()
        }

    }

    private fun circleX(isTop: Boolean): Float {
        return (measuredWidth * 0.75).toFloat()
    }


}