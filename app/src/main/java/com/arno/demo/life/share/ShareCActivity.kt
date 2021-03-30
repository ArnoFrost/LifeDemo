package com.arno.demo.life.share

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.arno.demo.life.R

class ShareCActivity : AppCompatActivity() {

    private val button: View by lazy {
        findViewById(R.id.btn_hot)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_c)
        doAnimation()
    }

    private fun doAnimation() {
        val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
        val movie = AnimationUtils.loadAnimation(this, R.anim.movie)
        button.startAnimation(shake)

        shake.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                Log.d(TAG, "shake onAnimationStart: ")
            }

            override fun onAnimationEnd(animation: Animation?) {
                Log.d(TAG, "shake onAnimationEnd: ")
                button.startAnimation(movie)
            }

            override fun onAnimationRepeat(animation: Animation?) {
                Log.d(TAG, "shake onAnimationRepeat: ")
            }

        })

        movie.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                Log.d(TAG, "movie onAnimationStart: ")
            }

            override fun onAnimationEnd(animation: Animation?) {
                Log.d(TAG, "movie onAnimationEnd: ")
                button.visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animation?) {
                Log.d(TAG, "movie onAnimationRepeat: ")
            }

        })

    }

    //    override fun onBackPressed() {
//        supportFinishAfterTransition()
//    }
    companion object {
        private const val TAG = "ShareCActivity"
    }
}