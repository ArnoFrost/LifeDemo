package com.arno.demo.life.share

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.arno.demo.life.R

class ShareLearnActivity : AppCompatActivity() {
    private val btnAll: Button by lazy {
        findViewById(R.id.btn_all)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_learn)

        ViewCompat.setTransitionName(btnAll, "ALL")
    }

    fun goToAll(view: View) {
        val intent = Intent(this, ShareAllActivity::class.java)
        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(this, btnAll, "test")
        ActivityCompat.startActivity(this, intent, options.toBundle())

    }
}