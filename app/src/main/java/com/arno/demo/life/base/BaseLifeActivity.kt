package com.arno.demo.life.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.arno.demo.life.utils.injectLifeLog

abstract class BaseLifeActivity : AppCompatActivity() {
    abstract val TAG: String
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        this.injectLifeLog()
    }
}