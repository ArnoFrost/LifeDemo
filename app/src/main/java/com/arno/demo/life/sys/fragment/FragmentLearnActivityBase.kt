package com.arno.demo.life.sys.fragment

import android.os.Bundle
import com.arno.demo.life.R
import com.arno.demo.life.sys.fragment.ui.main.FragmentLearnFragment
import com.arno.demo.life.base.BaseLifeActivity

class FragmentLearnActivityBase : BaseLifeActivity() {

    override val TAG: String = "FragmentLearnActivity"
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