package com.example.androidtest2.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtest2.databinding.ItemCategoryBinding

class CategoryAdapter(
    private var categoryList: List<String>,
    private val onCategoryClick: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedCategoryPosition: Int = -1

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String, position: Int) {
            binding.categoryName.text = category

            if (position == selectedCategoryPosition) {
                binding.root.setBackgroundColor(binding.root.context.getColor(android.R.color.darker_gray))
            } else {
                binding.root.setBackgroundColor(binding.root.context.getColor(android.R.color.transparent))
            }

            binding.root.setOnClickListener {
                selectedCategoryPosition = position
                onCategoryClick(category)
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categoryList[position], position)
    }

    override fun getItemCount(): Int = categoryList.size
    fun updateCategoryList(newCategoryList: List<String>) {
        categoryList = newCategoryList
        notifyDataSetChanged()
    }
}
