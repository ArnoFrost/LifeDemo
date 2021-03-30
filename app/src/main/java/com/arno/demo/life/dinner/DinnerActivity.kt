package com.arno.demo.life.dinner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arno.demo.life.R
import com.arno.demo.life.utils.DinnerDataHelper
import com.arno.demo.life.utils.NoLastItemDividerItemDecoration
import com.arno.demo.life.utils.RecycleViewDivider

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
    private lateinit var dinnerCardItemAdapter: DinnerItemAdapter
    private fun initCard() {
        dinnerCardAdapter = DinnerCardAdapter().apply {
            cardList.add(DinnerDataHelper.getDefaultCard(true))
            cardList.add(DinnerDataHelper.getDefaultCard(false))
        }
        topRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = dinnerCardAdapter
        }
    }

    private fun initContent() {
        dinnerCardItemAdapter = DinnerItemAdapter().apply {
            cardItemList.addAll(DinnerDataHelper.getDefaultCardList())
        }

        contentRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
//            addItemDecoration(NoLastItemDividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = dinnerCardItemAdapter
        }

    }
}