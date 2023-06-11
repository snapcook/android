package com.bangkit.snapcook.presentation.my_recipe

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.model.User
import com.bangkit.snapcook.databinding.RecipeGridItemBinding
import com.bangkit.snapcook.databinding.RecipeMiniItemBinding
import com.bangkit.snapcook.utils.constant.AnimationConstants
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.popTap
import com.bangkit.snapcook.utils.extension.setImageUrl

class MyRecipeAdapter(
    private val onClick: (Recipe) -> Unit
): RecyclerView.Adapter<MyRecipeAdapter.RecipeViewHolder>() {

    private var listRecipe = ArrayList<Recipe>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listRecipe: List<Recipe>) {
        this.listRecipe.clear()
        this.listRecipe.addAll(listRecipe)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeViewHolder {
        val binding = RecipeGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = listRecipe[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = listRecipe.size

    inner class RecipeViewHolder(private val binding: RecipeGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(recipe: Recipe){
                binding.root.popClick {
                    onClick(recipe)
                }

                binding.tvTitle.text = recipe.title
                binding.tvCategory.text = recipe.mainCategory
                binding.imgRecipe.setImageUrl(recipe.photo)
            }
    }
}