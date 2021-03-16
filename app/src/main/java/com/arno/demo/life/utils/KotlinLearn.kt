package com.arno.demo.life.utils

class KotlinLearn {

    fun hello() {
        var arry = intArrayOf(1, 2, 3)
        var arry2 = IntArray(3) { it + 1 }

        println(arry2.contentToString())
    }
}