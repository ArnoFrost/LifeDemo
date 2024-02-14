package com.arno.demo.life.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.arno.demo.life.R

class CategoryAdapter(
    private val categories: List<Category>
) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: AppCompatButton = view.findViewById(R.id.btn_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.button.text = categories[position].name
    }

    override fun getItemCount() = categories.size
}

data class Category(val name: String, val id: Int, val type: CategoryType = CategoryType.Other) {

}

sealed class CategoryType {
    object Project : CategoryType()
    object Sys : CategoryType()
    object Other : CategoryType()
    object Work : CategoryType()
}