package com.arno.demo.life.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        inflate(context, R.layout.desktop_pop_layout, this).apply {
            ivArrow = findViewById(R.id.iv_arrow)
            rv = findViewById(R.id.rv)
            clTop = findViewById(R.id.cl_top)
            bottomBar = findViewById(R.id.bottom_bar)
            rootMotionView = findViewById(R.id.desktop_dialog)
        }

        rv.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            rv.adapter = this@DesktopPopWindow.adapter
            addItemDecoration(NoLastItemDividerItemDecoration(context, VERTICAL))
        }
    }

    fun showDialog() {
        rv.scrollToPosition(0)
        rootMotionView.transitionToEnd()
    }

    fun hideDialog() {
        rootMotionView.transitionToStart()
    }
}
