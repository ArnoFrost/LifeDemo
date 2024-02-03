package com.arno.demo.life

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerCacheActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "RecyclerCacheActivity"
    }

    class ItemData(val name: String, val viewType: Int)

    val data = listOf(
        ItemData("Item 1", 1),
        ItemData("Item 2", 2),
        ItemData("Item 3", 1),
        ItemData("Item 4", 2),
        ItemData("Item 5", 1),
        ItemData("Item 6", 2),
        ItemData("Item 7", 1),
        ItemData("Item 8", 2),
        ItemData("Item 9", 1),
        ItemData("Item 10", 2),
        ItemData("Item 11", 1),
        ItemData("Item 12", 2),
        ItemData("Item 13", 1),
        ItemData("Item 14", 2),
        ItemData("Item 15", 1),
        ItemData("Item 16", 2),
        ItemData("Item 17", 1),
        ItemData("Item 18", 2),
        ItemData("Item 19", 1),
        ItemData("Item 20", 2)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_cache)
        val recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@RecyclerCacheActivity)
            setRecycledViewPool(CustomRecycledViewPool())
            setViewCacheExtension(CustomViewCacheExtension())
            adapter = MyAdapter(data)
        }

    }


    class CustomViewCacheExtension : RecyclerView.ViewCacheExtension() {
        override fun getViewForPositionAndType(
            recycler: RecyclerView.Recycler,
            position: Int,
            viewType: Int
        ): View? {
            Log.d(
                TAG,
                "getViewForPositionAndType() is hit with: recycler = $recycler, position = $position, viewType = $viewType"
            )
            return null
        }
    }

    class CustomRecycledViewPool : RecyclerView.RecycledViewPool() {
        override fun getRecycledView(viewType: Int): RecyclerView.ViewHolder? {
            Log.d(TAG, "getRecycledView() is hit with: viewType = $viewType")
            return super.getRecycledView(viewType)
        }
    }

    class MyAdapter(private val data: List<ItemData>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        companion object {
            private const val VIEW_TYPE_ONE = 1
            private const val VIEW_TYPE_TWO = 2
        }

        override fun getItemViewType(position: Int): Int {
            return data[position].viewType
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            // 根据 itemViewType 创建并返回相应的 ViewHolder
            return when (viewType) {
                VIEW_TYPE_ONE -> ViewHolderOne(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_type_one, parent, false)
                )

                VIEW_TYPE_TWO -> ViewHolderTwo(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_type_two, parent, false)
                )

                else -> throw IllegalArgumentException("Invalid view type")
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            // 根据 itemViewType 来绑定数据
            when (holder) {
                is ViewHolderOne -> holder.bind(data[position].name)
                is ViewHolderTwo -> holder.bind(data[position].name)
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
            super.onViewRecycled(holder)
            Log.d(TAG, "onViewRecycled")
        }

        class ViewHolderOne(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val textView: TextView = itemView.findViewById(R.id.text_view)

            fun bind(data: String) {
                textView.text = data
            }
        }

        class ViewHolderTwo(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val textView: TextView = itemView.findViewById(R.id.text_view)

            fun bind(data: String) {
                textView.text = data
            }
        }
    }
}