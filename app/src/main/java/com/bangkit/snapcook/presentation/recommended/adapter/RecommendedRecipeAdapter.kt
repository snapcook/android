package com.bangkit.snapcook.presentation.recommended.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.databinding.RecipeDetailedItemBinding
import com.bangkit.snapcook.utils.constant.AnimationConstants
import com.bangkit.snapcook.utils.extension.popTap
import com.bangkit.snapcook.utils.extension.setImageUrl
import com.bangkit.snapcook.utils.extension.toHourAndMinutes

class RecommendedRecipeAdapter(
    private val onClick: (String) -> Unit
): RecyclerView.Adapter<RecommendedRecipeAdapter.RecipeViewHolder>() {

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
        val binding = RecipeDetailedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val user = listRecipe[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = listRecipe.size

    inner class RecipeViewHolder(private val binding: RecipeDetailedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(recipe: Recipe){
                binding.apply {
                    root.setOnClickListener{
                        it.popTap()
                        Handler(Looper.getMainLooper()).postDelayed({
                            onClick(recipe.slug)
                        }, AnimationConstants.POP_ANIMATION)
                    }

                    tvCategory.text = recipe.mainCategory
                    imgRecipe.setImageUrl(recipe.photo)
                    tvTitle.text = recipe.title
                    tvTime.text = recipe.estimatedTime.toHourAndMinutes()
                    tvDescription.text = recipe.description
                }
            }
    }
}