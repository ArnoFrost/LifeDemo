package com.arno.demo.life.fragment

import android.os.Bundle
import android.view.View
import com.arno.demo.life.R
import com.arno.demo.life.fragment.ui.main.FragmentLearnFragment
import com.arno.demo.life.task.BaseTaskActivity

class FragmentLearnActivity : BaseTaskActivity() {

    val TAG: String = "FragmentLearnActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_learn)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FragmentLearnFragment.newInstance())
                .commitNow()
        }
    }

    override fun doStart(view: View?) {

    }
}