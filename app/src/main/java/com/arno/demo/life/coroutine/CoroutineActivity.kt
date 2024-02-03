package com.arno.demo.life.coroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arno.demo.life.R
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CoroutineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
    }

    fun test1() = runBlocking { // this: CoroutineScope
        launch { // 在后台启动一个新的协程并继续
            delay(1000L) // 非阻塞的等待1秒钟（默认时间单位是毫秒）
            println("World!") // 在延迟后打印输出
        }
        println("Hello,") // 协程已在等待时主线程还在继续
    }

    suspend fun doSomething() {
        delay(1000L) // 模拟一个长时间运行的任务
        println("Done Something")
    }

    fun test2() = runBlocking {
        launch {
            doSomething()
        }
    }

    fun test3() = runBlocking {
        val deferred = async { // 开始异步执行
            delay(1000L)
            return@async "Hello"
        }
        println("Waiting...")
        println(deferred.await()) // 等待异步执行的结果
    }
}