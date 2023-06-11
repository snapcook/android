package com.bangkit.snapcook.presentation.recommended.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.R
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.databinding.RecipeDetailedItemBinding
import com.bangkit.snapcook.utils.constant.AnimationConstants
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.popTap
import com.bangkit.snapcook.utils.extension.setImageUrl
import com.bangkit.snapcook.utils.extension.toHourAndMinutes

class RecommendedRecipeAdapter(
    private val onClick: (String) -> Unit,
    private val onBookmarkClick: (Recipe) -> Unit
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
        val recipe = listRecipe[position]
        holder.bind(recipe)

        val ivBookmark = holder.binding.btnBookmark
        if (recipe.isBookmarked) {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark))
        } else {
            ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark_border))
        }

        ivBookmark.popClick {
            onBookmarkClick(recipe)
            recipe.isBookmarked = !recipe.isBookmarked
            notifyItemChanged(position)
            if (recipe.isBookmarked) {
                ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark))
            } else {
                ivBookmark.setImageDrawable(ContextCompat.getDrawable(ivBookmark.context, R.drawable.ic_bookmark_border))
            }
        }
    }

    override fun getItemCount(): Int = listRecipe.size

    inner class RecipeViewHolder(val binding: RecipeDetailedItemBinding) :
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