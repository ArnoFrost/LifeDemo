package com.arno.demo.life

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.arno.demo.life.annotation.AnnotationLearnActivity
import com.arno.demo.life.binder.BinderLearnActivity
import com.arno.demo.life.binder.IntentServiceLearnActivity
import com.arno.demo.life.conflict.ConflictLearnActivity
import com.arno.demo.life.dinner.DinnerActivity
import com.arno.demo.life.event.EventLearnActivity
import com.arno.demo.life.handler.BlockLearnActivity
import com.arno.demo.life.handler.HandlerLearnActivity
import com.arno.demo.life.hook.HookLearnActivity
import com.arno.demo.life.life.LifeLearnActivity
import com.arno.demo.life.loader.ClassLoaderActivity
import com.arno.demo.life.messenger.MessengerLearnActivity
import com.arno.demo.life.pref.ViewPrefLearnActivity
import com.arno.demo.life.router.RouterLearnActivity
import com.arno.demo.life.share.ShareRootActivity
import com.arno.demo.life.task.TaskLearnActivity
import com.arno.demo.life.view.ViewLearnActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goToDinner(view: View?) {
        startActivity(Intent(this, DinnerActivity::class.java))
    }

    fun goToShare(view: View?) {
        startActivity(Intent(this, ShareRootActivity::class.java))
    }

    fun goToHook(view: View?) {
        startActivity(Intent(this, HookLearnActivity::class.java))
    }

    fun goToIntent(view: View?) {
        startActivity(Intent(this, IntentServiceLearnActivity::class.java))
    }

    fun goToBinder(view: View?) {
        startActivity(Intent(this, BinderLearnActivity::class.java))
    }

    fun goToLife(view: View?) {
        startActivity(Intent(this, LifeLearnActivity::class.java))
    }

    fun goToTask(view: View?) {
        startActivity(Intent(this, TaskLearnActivity::class.java))
    }

    fun goToView(view: View?) {
        startActivity(Intent(this, ViewLearnActivity::class.java))
    }

    fun goToConflict(view: View?) {
        startActivity(Intent(this, ConflictLearnActivity::class.java))
    }

    fun goToLoader(view: View?) {
        startActivity(Intent(this, ClassLoaderActivity::class.java))
    }

    fun goToEvent(view: View?) {
        startActivity(Intent(this, EventLearnActivity::class.java))
    }

    fun goToAnnotation(view: View?) {
        startActivity(Intent(this, AnnotationLearnActivity::class.java))
    }

    fun goToHandler(view: View?) {
        startActivity(Intent(this, HandlerLearnActivity::class.java))
    }

    fun goToMessenger(view: View?) {
        startActivity(Intent(this, MessengerLearnActivity::class.java))
    }

    fun goToRouter(view: View?) {
        startActivity(Intent(this, RouterLearnActivity::class.java))
    }

    fun goToViewPref(view: View?) {
        startActivity(Intent(this, ViewPrefLearnActivity::class.java))
    }

    fun goToBlock(view: View?) {
        startActivity(Intent(this, BlockLearnActivity::class.java))
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