package com.arno.demo.life.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arno.demo.life.R
import com.arno.demo.life.fragment.ui.main.FragmentLearnFragment

class FragmentLearnActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_learn)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FragmentLearnFragment.newInstance())
                .commitNow()
        }
    }
}