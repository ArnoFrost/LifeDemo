package com.arno.demo.life.share

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.arno.demo.life.R

class ShareRootActivity : AppCompatActivity() {
    private val btnAll: Button by lazy {
        findViewById(R.id.btn_all)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_root)
    }

    fun goToAll(view: View) {
        val intent = Intent(this, ShareBActivity::class.java)
//        val options =
//            ActivityOptionsCompat.makeSceneTransitionAnimation(this, btnAll, "test")
//        ActivityCompat.startActivity(this, intent, options.toBundle())

        startActivity(intent)

    }
}