package com.arno.demo.life.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.arno.demo.life.BuildConfig
import com.arno.demo.life.R
import com.arno.demo.life.conflict.SimpleAdapter
import com.arno.demo.life.utils.NoLastItemDividerItemDecoration
import com.arno.demo.life.utils.NoLastItemDividerItemDecoration.VERTICAL

class DesktopPopWindow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var ivArrow: ImageView
    private var rv: OverScrollRecyclerView
    private var clTop: ConstraintLayout
    private var bottomBar: LinearLayoutCompat
    private var rootMotionView: MotionLayout

    private val dataList = mutableListOf("Hello", "Hello1", "Hello2", "Hello3", "Hello4", "Hello5")
    private val adapter = SimpleAdapter(dataList)

    init {
        if (BuildConfig.DEBUG) {
            setBackgroundColor(Color.BLUE)
        }
        inflate(context, R.layout.desktop_pop_layout, this).apply {
            ivArrow = findViewById<ImageView?>(R.id.iv_arrow).apply {
                setOnClickListener { hideDialog() }
            }
            rv = findViewById(R.id.rv)
            clTop = findViewById(R.id.top_bar)
            bottomBar = findViewById(R.id.bottom_bar)
            rootMotionView = findViewById(R.id.desktop_dialog)
        }

        rv.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            rv.adapter = this@DesktopPopWindow.adapter
            addItemDecoration(NoLastItemDividerItemDecoration(context, VERTICAL))
            setOnOverScrollListener { direct, offset ->
                if (offset < -100) {
                    hideDialog()
                }
            }
        }
    }

    fun showDialog() {
        rv.scrollToPosition(0)
        rootMotionView.visibility = View.VISIBLE
        rootMotionView.transitionToEnd()
    }

    fun hideDialog() {
        rootMotionView.transitionToStart()
        rootMotionView.addTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                when (motionLayout?.currentState) {
                    // 回归到播放场景后隐藏布局
                    R.id.start -> {
                        motionLayout.visibility = View.GONE
                    }
                    else -> {
                    }
                }
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                super.onTransitionChange(motionLayout, startId, endId, progress)
                Log.d(
                    "Arno",
                    "onTransitionChange() called with: motionLayout = $motionLayout, startId = $startId, endId = $endId, progress = $progress"
                )
                if (progress == 0F) {
                    when (motionLayout?.currentState) {
                        // 回归到播放场景后隐藏布局
                        R.id.start -> {
                            motionLayout.visibility = View.GONE
                        }
                        else -> {
                        }
                    }
                }
            }
        })
    }
}
