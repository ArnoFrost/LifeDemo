package com.arno.demo.life.work.share

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import com.arno.demo.life.R
import com.arno.demo.life.base.utils.AnimatorAdapter

class ShareRootActivity : AppCompatActivity() {
    private val root: View by lazy {
        findViewById(R.id.root)
    }

    private val snap: ImageView by lazy {
        findViewById(R.id.snap)
    }
    private val btnC: Button by lazy {
        findViewById(R.id.btn_c)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_root)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        playAnimation()
    }

    private val ZOOM_LEVEL = 1 / 4F
    private val ALPHA_LEVEL = 0.4F
    fun getScaleXAnimator(view: View, zoomLevel: Float): Animator {
        return ObjectAnimator.ofFloat(view, "scaleX", 1F, zoomLevel)
    }

    fun getScaleYAnimator(view: View, zoomLevel: Float): Animator {
        return ObjectAnimator.ofFloat(view, "scaleY", 1F, zoomLevel)
    }

    fun getAlphaAnimator(view: View, alphaLevel: Float): Animator {
        return ObjectAnimator.ofFloat(view, "alpha", 1F, alphaLevel)
    }

    fun getXAnimator(view: View, x: Float): Animator {
        return ObjectAnimator.ofFloat(view, "x", view.x, x)
    }

    fun getYAnimator(view: View, y: Float): Animator {
        return ObjectAnimator.ofFloat(view, "y", view.y, y)
    }

    private fun playAnimation() {
        Log.d(TAG, "playAnimation: ")
        snap.postDelayed({
            val rect = Rect()
            btnC.getGlobalVisibleRect(rect)
            val bitmap = PushBitmapCacheManager.getInstance().getBitmapAndDelete("SharedC", false)
            bitmap?.let {
                //增加背景
                snap.setBackgroundColor(ContextCompat.getColor(this, R.color.black))
                snap.setPadding(10, 20, 20, 10)
                snap.setImageBitmap(
                    it
                )
                snap.visibility = View.VISIBLE
                snap.invalidate()

            }

            val scaleTime = 1500L
            val moveTime = 1000L
            val alphaTime = scaleTime

            val x = rect.centerX().toFloat()
            val y = rect.centerY().toFloat()

            snap.pivotX = x
            snap.pivotY = y


            val scaleX = getScaleXAnimator(snap, ZOOM_LEVEL).apply {
                duration = scaleTime
            }
            val scaleY = getScaleYAnimator(snap, ZOOM_LEVEL).apply {
                duration = scaleTime
            }
            val alpha = getAlphaAnimator(snap, ALPHA_LEVEL).apply {
                duration = alphaTime
            }


            val set = AnimatorSet().apply {
                addListener(object : AnimatorAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        super.onAnimationEnd(animation)
                        Log.d(TAG, "onAnimationEnd: ")
//                        snap.visibility = View.GONE

                        val snapRect = Rect()
                        snap.getGlobalVisibleRect(snapRect)
                        val x = (rect.centerX() - snapRect.centerX()).toFloat()
                        val y = (rect.bottom - snapRect.top).toFloat()
                        Log.d(TAG, "playAnimation: x = $x ,y = $y")

                        val moveX = getXAnimator(snap, x).apply {
                            duration = moveTime
                            addListener(object : AnimatorAdapter() {
                                override fun onAnimationEnd(animation: Animator?) {
                                    snap.visibility = View.GONE
                                }
                            })
                            interpolator = AccelerateDecelerateInterpolator()
                        }
                        val moveY = getYAnimator(snap, y).apply {
                            duration = moveTime
                        }
                        interpolator = AccelerateDecelerateInterpolator()
                        moveX.start()
                        moveY.start()
                    }
                })
            }
            set.interpolator = AccelerateDecelerateInterpolator()
//            set.playTogether(scaleX, scaleY, alpha, moveX, moveY)
            set.playTogether(scaleX, scaleY, alpha)
            set.start()
        }, 0)

    }

    fun go2BActivity(view: View) {
        val intent = Intent(this, ShareBActivity::class.java)
        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, root, KEY)
        //放置截屏
        PushBitmapCacheManager.getInstance().putBitmapByScale(KEY, root)
        //启动转场
        ActivityCompat.startActivity(this, intent, options.toBundle())
    }

    fun go2CActivity(view: View) {
        val intent = Intent(this, ShareCActivity::class.java)
        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, root, "sharedC")
        //启动转场
        ActivityCompat.startActivity(this, intent, options.toBundle())
    }

    companion object {
        private const val TAG = "ShareRootActivity"
        const val KEY = "ShareRoot"
    }
}