package com.arno.demo.life.share

import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.arno.demo.life.R
import com.google.android.material.transition.platform.Hold

class ShareAllActivity : AppCompatActivity() {
    private val root: View by lazy {
        findViewById(R.id.layout_all)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setBackgroundDrawable(null)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_all)
//region test
//        ViewCompat.setTransitionName(root, "ALL")
//
////        window.enterTransition = Fade()
//        window.enterTransition = Hold()
//
//        val apply = TransitionSet().apply {
//            addTransition(ChangeBounds())
//            addTransition(ChangeTransform())
//            addTarget(root)
//        }
//
//        window.sharedElementEnterTransition = apply
//        window.sharedElementExitTransition = apply
//endregion

//region 动画
//        postponeEnterTransition()

//        supportStartPostponedEnterTransition()
//endregion
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}