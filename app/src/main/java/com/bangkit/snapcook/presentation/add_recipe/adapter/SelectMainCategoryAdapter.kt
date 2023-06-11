package com.bangkit.snapcook.presentation.add_recipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.R
import com.bangkit.snapcook.data.model.Category
import com.bangkit.snapcook.databinding.SelectCategoryItemBinding
import com.bangkit.snapcook.utils.extension.popClick

class SelectMainCategoryAdapter : RecyclerView.Adapter<SelectMainCategoryAdapter.SelectCategoryAdapter>() {
    private var data = ArrayList<String>()
    private var selectedIndex = -1
    private var selectedCategory = ""

    fun setData(category: List<String>) {
        this.data.clear()
        this.data.addAll(category)
        notifyDataSetChanged()
    }

    fun setSelectedData(category: String) {
        selectedCategory = category
        val index = data.indexOf(selectedCategory)
        notifyItemChanged(selectedIndex)
        selectedIndex = index
        notifyItemChanged(selectedIndex)
    }

    fun retrieveSelectedCategory() = selectedCategory

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectCategoryAdapter {
        val binding = SelectCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectCategoryAdapter(binding)
    }

    override fun onBindViewHolder(holder: SelectCategoryAdapter, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class SelectCategoryAdapter(private val binding: SelectCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: String) {
            binding.apply {
                if (selectedIndex == layoutPosition) {
                    itemView.setBackgroundResource(R.drawable.bg_category_selected)
                    tvCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                } else {
                    itemView.setBackgroundResource(R.drawable.bg_category_un_selected)
                    tvCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.copper))
                }

                itemView.popClick {
                    if (selectedIndex == layoutPosition) {
                        itemView.setBackgroundResource(R.drawable.bg_category_selected)
                        tvCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
                    } else {
                        selectedCategory = category
                        val previousSelectedItem = selectedIndex
                        selectedIndex = layoutPosition
                        notifyItemChanged(previousSelectedItem)
                        notifyItemChanged(selectedIndex)
                        itemView.setBackgroundResource(R.drawable.bg_category_un_selected)
                        tvCategory.setTextColor(ContextCompat.getColor(itemView.context, R.color.copper))
                    }
                    selectedCategory = category
                }

                tvCategory.text = category
            }
        }
    }
}