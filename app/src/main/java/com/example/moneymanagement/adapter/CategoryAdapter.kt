package com.example.moneymanagement.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moneymanagement.R
import com.example.moneymanagement.beans.CategoryValues
import com.example.moneymanagement.beans.Transaction
import kotlin.math.abs
import kotlin.math.roundToInt

class CategoryAdapter(context: Context, categories: List<CategoryValues>):
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>(){

    var context = context
    var categories = categories
    val sum = categories.sumOf { it.value }

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var categoryName: TextView = itemView.findViewById(R.id.category_name)
        var categoryValue: TextView = itemView.findViewById(R.id.category_value)
        var categoryPercent: TextView = itemView.findViewById(R.id.color_ind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        var categoryItem = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false)
        return CategoryAdapter.CategoryViewHolder(categoryItem)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.categoryName.text = categories[position].category.kategoryName
        holder.categoryValue.text = categories[position].value.toString()
        holder.categoryPercent.text = (categories[position].value / sum * 100).roundToInt().toString() + '%'
        holder.categoryPercent.setBackgroundColor(Color.rgb(
            position * 255 / 4,
            abs(categories[position].value * 255 / 6).toInt(),
            100
        ))
    }

}