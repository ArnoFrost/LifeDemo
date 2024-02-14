package com.arno.demo.life.base.utils

import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.injectLifeLog() {
    val className = this.javaClass.simpleName
    LifeUtil(className, this)
}