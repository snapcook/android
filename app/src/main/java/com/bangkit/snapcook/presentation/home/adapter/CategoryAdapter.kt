package com.bangkit.snapcook.presentation.home.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.data.model.Category
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.databinding.CategoryItemBinding
import com.bangkit.snapcook.databinding.RecipeMiniItemBinding
import com.bangkit.snapcook.utils.constant.AnimationConstants
import com.bangkit.snapcook.utils.extension.popTap
import com.bangkit.snapcook.utils.extension.setImageUrl
import timber.log.Timber

class CategoryAdapter(
    private val onClick: (String, String) -> Unit
): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var listCategory = ArrayList<Category>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listCategory: List<Category>) {
        this.listCategory.clear()
        this.listCategory.addAll(listCategory)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = listCategory[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int = listCategory.size

    inner class CategoryViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category){
            binding.root.setOnClickListener{
                it.popTap()
                Handler(Looper.getMainLooper()).postDelayed({
                    onClick(category.id, category.name)
                }, AnimationConstants.POP_ANIMATION)
            }

            Timber.d("${category.name} & ${category.photo}")
            binding.tvCategory.text = category.name
            binding.imgCategory.setImageUrl(category.photo)
        }
    }
}