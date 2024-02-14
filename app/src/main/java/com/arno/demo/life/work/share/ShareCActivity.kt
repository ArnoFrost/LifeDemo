package com.arno.demo.life.work.share

import android.content.Intent
import android.os.Bundle
import android.transition.ChangeBounds
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.arno.demo.life.R

class ShareCActivity : AppCompatActivity() {

    private val button: View by lazy {
        findViewById(R.id.btn_hot)
    }
    private val root: View by lazy {
        findViewById(R.id.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setbackAnimation()
        setContentView(R.layout.activity_share_c)
        initView()

    }

    private fun initView() {
        button.apply {
            setOnClickListener {
                //放置图片
                PushBitmapCacheManager.getInstance().putBitmapByScale("SharedC", root)
                startActivity(Intent(context, ShareRootActivity::class.java))
//                val intent = Intent(context, ShareRootActivity::class.java)
////        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
////        intent.putExtra("backHotParam", hbBackParam)
//                // 跳转
//                val finalOptionsCompat: ActivityOptionsCompat =
//                    ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        this@ShareCActivity,
//                        root,
//                        "sharedC"
//                    )
//
//                ActivityCompat.startActivity(
//                    this@ShareCActivity,
//                    intent,
//                    finalOptionsCompat.toBundle()
//                )
            }
        }
    }

    private fun setbackAnimation() {
        window.setBackgroundDrawable(null)
        window.sharedElementExitTransition = ChangeBounds().apply {
            duration = duration
            interpolator = AccelerateDecelerateInterpolator()
        }
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }

    companion object {
        private const val TAG = "ShareCActivity"
    }
}