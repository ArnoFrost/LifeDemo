package com.arno.demo.life.share

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arno.demo.life.R

class ShareCActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_c)
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}