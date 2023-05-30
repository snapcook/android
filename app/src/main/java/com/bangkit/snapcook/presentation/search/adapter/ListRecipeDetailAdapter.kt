package com.bangkit.snapcook.presentation.search.adapter

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
import com.bangkit.snapcook.databinding.RecipeMiniItemBinding
import com.bangkit.snapcook.utils.constant.AnimationConstants
import com.bangkit.snapcook.utils.extension.popTap
import com.bangkit.snapcook.utils.extension.setImageUrl

class ListRecipeDetailAdapter(
    private val onClick: (String) -> Unit,
    private val onBookmarkClick: (Recipe) -> Unit
): RecyclerView.Adapter<ListRecipeDetailAdapter.RecipeViewHolder>() {

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

        ivBookmark.setOnClickListener {
            it.popTap()
            onBookmarkClick(recipe)
        }
    }

    override fun getItemCount(): Int = listRecipe.size

    inner class RecipeViewHolder(val binding: RecipeDetailedItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe){
            binding.root.setOnClickListener{
                it.popTap()
                Handler(Looper.getMainLooper()).postDelayed({
                    onClick(recipe.slug)
                }, AnimationConstants.POP_ANIMATION)
            }
            binding.tvTitle.text = recipe.title
            binding.tvCategory.text = recipe.mainCategory
            binding.imgRecipe.setImageUrl(recipe.photo)
            binding.tvDescription.text = recipe.description
            binding.tvTime.text = recipe.estimatedTime.toHoursAndMinutes()

        }
    }

    private fun Int.toHoursAndMinutes(): String {
        val hours = this / 60
        val minutes = this % 60

        return if (hours != 0 && minutes != 0) {
            "$hours jam $minutes menit"
        } else if (hours != 0) {
            "$hours jam"
        } else {
            "$minutes menit"
        }
    }
}