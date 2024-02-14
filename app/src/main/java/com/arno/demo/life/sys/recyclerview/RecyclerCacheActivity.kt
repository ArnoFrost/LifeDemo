package com.arno.demo.life.sys.recyclerview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.arno.demo.life.R
import java.util.Queue

class RecyclerCacheActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "RecyclerCacheActivity"
    }

    class ItemData(val name: String, val viewType: Int)

    private val viewPool = CustomRecycledViewPool()
    private val customViewCacheExtension = CustomViewCacheExtension()

    val data = listOf(
        ItemData("Item 1", 1),
        ItemData("Item 2", 2),
        ItemData("Item 3", 3),
        ItemData("Item 4", 1),
        ItemData("Item 5", 2),
        ItemData("Item 6", 3),
        ItemData("Item 7", 1),
        ItemData("Item 8", 2),
        ItemData("Item 9", 3),
        ItemData("Item 10", 3),

        ItemData("Item 11", 1),
        ItemData("Item 12", 2),
        ItemData("Item 13", 3),
        ItemData("Item 14", 1),
        ItemData("Item 15", 2),
        ItemData("Item 16", 3),
        ItemData("Item 17", 1),
        ItemData("Item 18", 2),
        ItemData("Item 19", 3),
        ItemData("Item 20", 3),

        ItemData("Item 31", 1),
        ItemData("Item 32", 2),
        ItemData("Item 33", 3),
        ItemData("Item 34", 1),
        ItemData("Item 35", 2),
        ItemData("Item 36", 3),
        ItemData("Item 37", 1),
        ItemData("Item 38", 2),
        ItemData("Item 39", 3),
        ItemData("Item 40", 3),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_cache)


        val viewPager = findViewById<ViewPager2>(R.id.view_pager)
        viewPager.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val recyclerView = RecyclerView(parent.context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    layoutManager = LinearLayoutManager(parent.context)
                    setRecycledViewPool(viewPool)
                    setItemViewCacheSize(4)
                    setViewCacheExtension(customViewCacheExtension)
                    adapter = MyAdapter(data, customViewCacheExtension)
                }
                return object : RecyclerView.ViewHolder(recyclerView) {}
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


            }

            override fun getItemCount(): Int {
                return 5
            }
        }

    }


    class CustomViewCacheExtension : RecyclerView.ViewCacheExtension() {
        private val cache = mutableMapOf<Int, Queue<RecyclerView.ViewHolder>>()

        override fun getViewForPositionAndType(
            recycler: RecyclerView.Recycler,
            position: Int,
            viewType: Int
        ): View? {
            val cachedViews = cache[viewType]
            return if (!cachedViews.isNullOrEmpty()) {
                val itemView = cachedViews.poll()?.itemView
                if (itemView?.parent != null) {
                    Log.e(TAG, "getViewForPositionAndType: but have parent")
                    return null
                }
                Log.d(
                    TAG,
                    "getViewForPositionAndType() called with: recycler = $recycler, position = $position, viewType = $viewType " +
                            ",same left : " + cachedViews.size
                )
                itemView
            } else {
                null
            }
        }

        fun cacheView(viewHolder: RecyclerView.ViewHolder) {
//            val viewType = viewHolder.itemViewType
//            val itemView = viewHolder.itemView
//            // 检查视图是否已经附加到父布局
//            if (itemView.parent != null) {
//                Log.e(TAG, "cacheView: but have parent, attempting to remove.")
////                // 尝试从其父布局中移除
////                (itemView.parent as? ViewGroup)?.removeView(itemView)
//                return
//            }
//            // 再次检查确保视图没有父布局
//            if (itemView.parent == null) {
//                cache.getOrPut(viewType) { LinkedList() }.add(viewHolder)
//                Log.d(TAG, "cacheView() is hit with: viewType = $viewType")
//            } else {
//                Log.e(TAG, "Failed to cache view as it still has a parent.")
//            }
        }

    }

    class CustomRecycledViewPool : RecyclerView.RecycledViewPool() {
        override fun getRecycledView(viewType: Int): RecyclerView.ViewHolder? {
            val recycledView = super.getRecycledView(viewType)
            if (recycledView != null) {
                Log.d(TAG, "getRecycledView() is hit with: viewType = $viewType")
            }
            return recycledView
        }
    }

    class MyAdapter(
        private val data: List<ItemData>,
        private val customViewCacheExtension: CustomViewCacheExtension?
    ) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        companion object {
            private const val VIEW_TYPE_ONE = 1
            private const val VIEW_TYPE_TWO = 2
            private const val VIEW_TYPE_THREE = 3
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

                VIEW_TYPE_THREE -> ViewHolderThree(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_type_three, parent, false)
                )

                else -> throw IllegalArgumentException("Invalid view type")
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            // 根据 itemViewType 来绑定数据
            Log.d(TAG, "onBindViewHolder() called with: holder = $holder, position = $position")
            when (holder) {
                is ViewHolderOne -> holder.bind(data[position].name)
                is ViewHolderTwo -> holder.bind(data[position].name)
                is ViewHolderThree -> holder.bind(data[position].name)
            }
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
            super.onViewRecycled(holder)
            Log.i(
                TAG,
                "onViewRecycled() called with: holder = ${(holder as BaseTextHolder).getText()}"
            )
            customViewCacheExtension?.cacheView(holder)
        }

        class ViewHolderOne(itemView: View) : BaseTextHolder(itemView) {
            private val textView: TextView = itemView.findViewById(R.id.text_view)

            override fun bind(data: String) {
                textView.text = data
            }
        }

        class ViewHolderTwo(itemView: View) : BaseTextHolder(itemView) {
            private val textView: TextView = itemView.findViewById(R.id.text_view)

            override fun bind(data: String) {
                textView.text = data
            }
        }

        class ViewHolderThree(itemView: View) : BaseTextHolder(itemView) {
            private val textView: TextView = itemView.findViewById(R.id.text_view)

            override fun bind(data: String) {
                textView.text = data
            }
        }

        open class BaseTextHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val textView: TextView = itemView.findViewById(R.id.text_view)

            open fun bind(data: String) {
                textView.text = data
            }

            open fun getText(): String {
                return textView.text?.toString() ?: ""
            }
        }
    }
}