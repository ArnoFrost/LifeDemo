package com.arno.demo.life.main

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arno.demo.life.R
import com.arno.demo.life.base.utils.setOnItemClickListener
import com.arno.demo.life.other.annotation.AnnotationLearnActivity
import com.arno.demo.life.other.hook.HookLearnActivity
import com.arno.demo.life.other.proxy.ProxyLearnActivity
import com.arno.demo.life.project.coroutine.CoroutineActivity
import com.arno.demo.life.project.event.EventLearnActivity
import com.arno.demo.life.project.network.NetworkLearnActivity
import com.arno.demo.life.project.router.RouterLearnActivity
import com.arno.demo.life.project.rxjava.RxJavaActivity
import com.arno.demo.life.sys.conflict.ConflictLearnActivity
import com.arno.demo.life.sys.fragment.FragmentLearnActivityBase
import com.arno.demo.life.sys.handler.HandlerLearnActivity
import com.arno.demo.life.sys.life.LifeLearnActivity
import com.arno.demo.life.sys.loader.ClassLoaderActivity
import com.arno.demo.life.sys.messenger.MessengerLearnActivity
import com.arno.demo.life.sys.pref.ViewPrefLearnActivity
import com.arno.demo.life.sys.recyclerview.RecyclerCacheActivity
import com.arno.demo.life.sys.task.TaskLearnActivity
import com.arno.demo.life.work.dinner.DinnerActivity
import com.arno.demo.life.work.motion.MotionActivity
import com.arno.demo.life.work.share.ShareRootActivity

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
        private const val ID_COROUTINE_LEARN = 100
        private const val ID_NETWORK_LEARN = 101
        private const val ID_ROUTER_LEARN = 102
        private const val ID_RXJAVA_LEARN = 103
        private const val ID_EVENT_LEARN = 104

        private const val ID_CONFLICT_LEARN = 200
        private const val ID_FRAGMENT_RECREATE_LEARN = 201
        private const val ID_HANDLER_LEARN = 202
        private const val ID_MESSENGER_LEARN = 203
        private const val ID_CLASS_LOADER_LEARN = 204
        private const val ID_LIFE_LEARN = 205
        private const val ID_START_MODE_LEARN = 206
        private const val ID_RECYCLER_VIEW_LEARN = 207
        private const val ID_VIEW_LOAD_LEARN = 208

        private const val ID_ANNOTATION_LEARN = 300
        private const val ID_HOOK_LEARN = 301
        private const val ID_PROXY_LEARN = 302

        private const val ID_DINNER_MAN = 400
        private const val ID_MOTION_LAYOUT = 401
        private const val ID_SHARE_ANIMATION = 402
    }

    private val categoryData = listOf(
        Category("协程学习", ID_COROUTINE_LEARN, CategoryType.Project),
        Category("网络学习", ID_NETWORK_LEARN, CategoryType.Project),
        Category("路由学习", ID_ROUTER_LEARN, CategoryType.Project),
        Category("RxJava学习", ID_RXJAVA_LEARN, CategoryType.Project),
        Category("事件学习", ID_EVENT_LEARN, CategoryType.Project),

        Category("滑动冲突学习", ID_CONFLICT_LEARN, CategoryType.Sys),
        Category("Fragment重建学习", ID_FRAGMENT_RECREATE_LEARN, CategoryType.Sys),
        Category("Handler学习", ID_HANDLER_LEARN, CategoryType.Sys),
        Category("Messenger学习", ID_MESSENGER_LEARN, CategoryType.Sys),
        Category("类加载学习", ID_CLASS_LOADER_LEARN, CategoryType.Sys),
        Category("生命周期学习", ID_LIFE_LEARN, CategoryType.Sys),
        Category("启动模式学习", ID_START_MODE_LEARN, CategoryType.Sys),
        Category("RecyclerView缓存学习", ID_RECYCLER_VIEW_LEARN, CategoryType.Sys),
        Category("View加载优化学习", ID_VIEW_LOAD_LEARN, CategoryType.Sys),

        Category("注解学习", ID_ANNOTATION_LEARN, CategoryType.Other),
        Category("Hook学习", ID_HOOK_LEARN, CategoryType.Other),
        Category("代理学习", ID_PROXY_LEARN, CategoryType.Other),

        Category("干饭人", ID_DINNER_MAN, CategoryType.Work),
        Category("MotionLayout", ID_MOTION_LAYOUT, CategoryType.Work),
        Category("共享动画", ID_SHARE_ANIMATION, CategoryType.Work),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val c = LayoutInflater.from(this).inflate(R.layout.activity_main_new, null, false)
        setContentView(c)

        findViewById<RecyclerView?>(R.id.rv_project_study).apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = CategoryAdapter(categoryData.filter { it.type == CategoryType.Project })
            setOnItemClickListener { view, position ->
                onItemClickListener.onItemClick(categoryData.filter { it.type == CategoryType.Project }[position].id)
            }
        }

        findViewById<RecyclerView?>(R.id.rv_system_study).apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = CategoryAdapter(categoryData.filter { it.type == CategoryType.Sys })
            setOnItemClickListener { view, position ->
                onItemClickListener.onItemClick(categoryData.filter { it.type == CategoryType.Sys }[position].id)
            }
        }

        findViewById<RecyclerView?>(R.id.rv_other).apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = CategoryAdapter(categoryData.filter { it.type == CategoryType.Other })
            setOnItemClickListener { view, position ->
                onItemClickListener.onItemClick(categoryData.filter { it.type == CategoryType.Other }[position].id)
            }
        }

        findViewById<RecyclerView?>(R.id.rv_work_demo).apply {
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = CategoryAdapter(categoryData.filter { it.type == CategoryType.Work })
            setOnItemClickListener { view, position ->
                onItemClickListener.onItemClick(categoryData.filter { it.type == CategoryType.Work }[position].id)
            }
        }

    }


    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

    private fun navigateToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

    private val onItemClickListener: OnItemClickListener = object : OnItemClickListener {
        override fun onItemClick(id: Int) {
            when (id) {
                ID_COROUTINE_LEARN -> navigateToActivity(CoroutineActivity::class.java)
                ID_NETWORK_LEARN -> navigateToActivity(NetworkLearnActivity::class.java)
                ID_ROUTER_LEARN -> navigateToActivity(RouterLearnActivity::class.java)
                ID_RXJAVA_LEARN -> navigateToActivity(RxJavaActivity::class.java)
                ID_EVENT_LEARN -> navigateToActivity(EventLearnActivity::class.java)

                ID_CONFLICT_LEARN -> navigateToActivity(ConflictLearnActivity::class.java)
                ID_FRAGMENT_RECREATE_LEARN -> navigateToActivity(FragmentLearnActivityBase::class.java)
                ID_HANDLER_LEARN -> navigateToActivity(HandlerLearnActivity::class.java)
                ID_MESSENGER_LEARN -> navigateToActivity(MessengerLearnActivity::class.java)
                ID_CLASS_LOADER_LEARN -> navigateToActivity(ClassLoaderActivity::class.java)
                ID_LIFE_LEARN -> navigateToActivity(LifeLearnActivity::class.java)
                ID_START_MODE_LEARN -> navigateToActivity(TaskLearnActivity::class.java)
                ID_RECYCLER_VIEW_LEARN -> navigateToActivity(RecyclerCacheActivity::class.java)
                ID_VIEW_LOAD_LEARN -> navigateToActivity(ViewPrefLearnActivity::class.java)

                ID_ANNOTATION_LEARN -> navigateToActivity(AnnotationLearnActivity::class.java)
                ID_HOOK_LEARN -> navigateToActivity(HookLearnActivity::class.java)
                ID_PROXY_LEARN -> navigateToActivity(ProxyLearnActivity::class.java)

                ID_DINNER_MAN -> navigateToActivity(DinnerActivity::class.java)
                ID_MOTION_LAYOUT -> navigateToActivity(MotionActivity::class.java)
                ID_SHARE_ANIMATION -> navigateToActivity(ShareRootActivity::class.java)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    private var exitTime: Long = 0
    private fun exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show()
            exitTime = System.currentTimeMillis()
        } else {
            finish()
        }
    }
}
