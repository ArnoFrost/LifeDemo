package com.arno.demo.life.share

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.arno.demo.life.R

class ShareBActivity : AppCompatActivity() {
    private val root: View by lazy {
        findViewById(R.id.layout_root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setBackgroundDrawable(null)
        super.onCreate(savedInstanceState)
        //<editor-fold desc="Description">
        setContentView(R.layout.activity_share_b)
        //</editor-fold>
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

    fun goToC(view: View) {
        val intent = Intent(this, ShareCActivity::class.java)
        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, root, "test")
        ActivityCompat.startActivity(this, intent, options.toBundle())
        finish()
    }

//    override fun onBackPressed() {
//        supportFinishAfterTransition()
//    }
}