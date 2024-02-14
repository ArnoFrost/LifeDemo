package com.arno.demo.life.project.coroutine

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.arno.demo.life.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CoroutineActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "CoroutineActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPostResume() {
        super.onPostResume()
        // 启动一个新的协程
        test1()

        // 使用async/await并发执行任务
        test2()

        // 在指定的上下文中执行协程
        test3()
    }

    // 使用launch启动一个新的协程
    fun test1() = Thread {
        runBlocking {
            launch {
                delay(1000L)
                Log.d(TAG, "test1: World")
                println("World!")
            }
            Log.d(TAG, "test1: Hello,")
        }
    }

    // 使用async/await并发执行任务
    fun test2() = Thread {
        runBlocking {
            val deferred = async {
                doSomething()
            }
            Log.d(TAG, "test2:Waiting ....")

            println(deferred.await())
        }
    }

    // 在指定的上下文中执行协程
    fun test3() = Thread {
        runBlocking {
            withContext(Dispatchers.IO) {
                doSomething()
            }
        }
    }

    suspend fun doSomething() {
        delay(1000L)
        Log.d(TAG, "doSomething ....")
    }

//    // 使用launch启动一个新的协程
//    fun fetchUser() = launch {
//        val user = async(Dispatchers.IO) { apiService.getUser() }
//        showUser(user.await())
//    }
//
//    // 使用async/await并发执行任务
//    fun fetchPostsAndComments() = launch {
//        val posts = async(Dispatchers.IO) { apiService.getPosts() }
//        val comments = async(Dispatchers.IO) { apiService.getComments() }
//        showPostsAndComments(posts.await(), comments.await())
//    }
//
//    // 在指定的上下文中执行协程
//    fun saveUser(user: User) = launch {
//        withContext(Dispatchers.IO) { userDao.insert(user) }
//    }
//
//    // 使用delay暂停协程的执行
//    fun showLoadingIndicator() = launch {
//        loadingIndicator.show()
//        delay(1000L)
//        loadingIndicator.hide()
//    }
}