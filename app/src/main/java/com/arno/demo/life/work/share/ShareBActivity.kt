package com.arno.demo.life.work.share

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Transition
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.arno.demo.life.R

class ShareBActivity : AppCompatActivity() {
    private val image: ImageView by lazy {
        findViewById(R.id.image)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //暂停动画
        initSharedWindow()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_b)
        initSharedView()
    }

    private fun initSharedWindow() {
        window.setBackgroundDrawable(null)
        supportPostponeEnterTransition()
        window.sharedElementEnterTransition = getSnapShotTrans(1000)
    }

    private fun initSharedView() {
        //设置共享
        ViewCompat.setTransitionName(image, ShareRootActivity.KEY)
        //填充图片
        image.setImageBitmap(PushBitmapCacheManager.getInstance().getBitmap(ShareRootActivity.KEY))
        //开始动效
        supportStartPostponedEnterTransition()
    }

    private fun getSnapShotTrans(duration: Long): Transition? {
        // 尝试覆写透明转场效果
        /**
         * ChangeBounds 改变目标布局中view的边界
         * ChangeClipBounds 裁剪目标布局中view的边界
         * ChangeTransform 实现旋转或者缩放动画
         */
        val trans: Transition = ChangeBounds()
        trans.duration = duration
        val interpolator = AccelerateInterpolator(1.5F)
//        val interpolator = PathInterpolatorCompat.create(0F, 0.5F, 0.5F, 0.2F)
//        val interpolator = AnticipateOvershootInterpolator(3F)

        trans.interpolator = interpolator

        return trans
    }
}