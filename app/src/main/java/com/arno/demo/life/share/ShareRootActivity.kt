package com.arno.demo.life.share

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import com.arno.demo.life.R

class ShareRootActivity : AppCompatActivity() {
    private val root: View by lazy {
        findViewById(R.id.root)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_root)
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

    companion object {
        private const val TAG = "ShareRootActivity"
        const val KEY = "ShareRoot"
    }
}