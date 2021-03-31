package com.arno.demo.life.dinner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
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
        val root = LayoutInflater.from(this).inflate(R.layout.activity_dinner, null, false)
        setContentView(root)
        initView()
    }

    private fun initView() {
        initContent()
        initCard()
    }

    private lateinit var dinnerCardAdapter: DinnerCardAdapter
    private lateinit var dinnerCardItemAdapter: DinnerItemAdapter
    private fun initCard() {
        dinnerCardAdapter = DinnerCardAdapter().apply {
            //反向填充数据确保合理性
            cardList.addAll(DinnerDataHelper.getDefaultCardByItem(dinnerCardItemAdapter.cardItemList))
        }
        topRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            adapter = dinnerCardAdapter
        }
    }

    private fun initContent() {
        dinnerCardItemAdapter = DinnerItemAdapter().apply {
            cardItemList.addAll(DinnerDataHelper.getDefaultCardList(9))
        }

        contentRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = dinnerCardItemAdapter
        }

    }

    fun goBack(view: View) {
        supportFinishAfterTransition()
    }
}