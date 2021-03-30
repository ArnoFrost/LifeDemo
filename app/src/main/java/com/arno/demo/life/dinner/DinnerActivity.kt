package com.arno.demo.life.dinner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arno.demo.life.R
import com.arno.demo.life.utils.DinnerDataHelper

class DinnerActivity : AppCompatActivity() {
    val topRv: RecyclerView by lazy {
        findViewById(R.id.rv_top)
    }
    val contentRv: RecyclerView by lazy {
        findViewById(R.id.rv_content)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dinner)
        initView()
    }

    private fun initView() {
        initCard()
        initContent()
    }

    private lateinit var dinnerCardAdapter: DinnerCardAdapter
    private fun initCard() {
        dinnerCardAdapter = DinnerCardAdapter().apply {
            cardList.addAll(DinnerDataHelper.getDefaultCardList())
        }
        topRv.apply {
            layoutManager = LinearLayoutManager(this@DinnerActivity, RecyclerView.HORIZONTAL, false)
            adapter = dinnerCardAdapter
        }

    }

    private fun initContent() {

    }
}