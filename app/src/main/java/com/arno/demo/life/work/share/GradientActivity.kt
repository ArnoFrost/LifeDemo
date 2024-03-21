package com.arno.demo.life.work.share

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.arno.demo.life.R
import com.arno.demo.life.base.utils.ViewUtil
import kotlin.random.Random

class GradientActivity : AppCompatActivity() {
    private val root: View by lazy {
        findViewById(R.id.view)
    }
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gradient_test)
        root.setOnClickListener {
//            loopGradient()
            loopGradientByColor()
        }
        updateGradientBackground(
            root,
            Color.parseColor("#FF0000"),
            Color.parseColor("#0000FF")
        )
    }

//    private fun loopGradient() {
//        val startColor = Color.parseColor("#FF0000")
//        val endColor = Color.parseColor("#00FF00")
//        handler.postDelayed({
//            val alpha = Random.nextFloat()
//            updateGradientBackground(
//                root,
//                ViewUtil.adjustAlpha(startColor, alpha),
//                ViewUtil.adjustAlpha(endColor, 1 - alpha)
//            )
//            loopGradient()
//        }, 100)
//    }

    private fun loopGradientByColor() {
        handler.postDelayed({
            val startColor = Color.parseColor("#FFE596")
            val endColor = Color.parseColor("#FFF6DC")

            val alpha = Random.nextFloat()
            val newStartColor = ViewUtil.adjustAlpha(startColor, alpha)
            val newEndColor = ViewUtil.adjustAlpha(endColor, 1 - alpha)


            (root.background as? GradientDrawable)?.apply {
                colors = intArrayOf(newStartColor, newEndColor)
            }
            loopGradientByColor()
        }, 100)
    }

    /**
     * 更新视图的渐变背景颜色。
     *
     * @param view 需要更新背景的视图。
     * @param startColor 渐变开始的颜色。
     * @param endColor 渐变结束的颜色。
     */
    fun updateGradientBackground(view: View, startColor: Int, endColor: Int) {
        val gradient = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(startColor, endColor)
        )

        // 设置其他属性，如形状、角度等，根据需要调整
        gradient.shape = GradientDrawable.RECTANGLE
        // gradient.cornerRadius = ... // 如果需要圆角
        // gradient.setStroke(...); // 如果需要边框

        // 将更新后的Drawable设置为背景
        view.background = gradient
    }

    companion object {
        private const val TAG = "ShareRootActivity"
        const val KEY = "ShareRoot"
    }
}