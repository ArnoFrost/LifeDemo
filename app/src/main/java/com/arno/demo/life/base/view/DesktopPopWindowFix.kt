package com.arno.demo.life.base.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.arno.demo.life.BuildConfig
import com.arno.demo.life.R
import com.arno.demo.life.sys.conflict.SimpleAdapter
import com.arno.demo.life.base.utils.NoLastItemDividerItemDecoration
import com.arno.demo.life.base.utils.NoLastItemDividerItemDecoration.VERTICAL

class DesktopPopWindowFix @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var ivArrow: ImageView
    private var rv: OverScrollRecyclerView
    private var clTop: ConstraintLayout
    private var bottomBar: LinearLayoutCompat
    private var rootMotionView: ConstraintLayout

    private val dataList = mutableListOf("Hello", "Hello1", "Hello2", "Hello3", "Hello4", "Hello5")
    private val adapter = SimpleAdapter(dataList)

    init {
        if (BuildConfig.DEBUG) {
            setBackgroundColor(Color.BLUE)
        }
        inflate(context, R.layout.desktop_pop_layout_fix, this).apply {
            ivArrow = findViewById<ImageView?>(R.id.iv_arrow).apply {
                setOnClickListener { hideDialog() }
            }
            rootMotionView = this as ConstraintLayout
            rv = findViewById(R.id.rv)
            clTop = findViewById(R.id.top_bar)
            bottomBar = findViewById(R.id.bottom_bar)
        }

        rv.apply {
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            rv.adapter = this@DesktopPopWindowFix.adapter
            addItemDecoration(NoLastItemDividerItemDecoration(context, VERTICAL))
            setOnOverScrollListener { direct, offset ->
                if (offset < -100) {
                    hideDialog()
                }
            }
        }
    }

    fun showDialog() {
    }

    fun hideDialog() {
    }
}
