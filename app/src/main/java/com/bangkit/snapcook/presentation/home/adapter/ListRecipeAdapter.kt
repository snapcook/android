package com.bangkit.snapcook.presentation.home.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.model.User
import com.bangkit.snapcook.databinding.RecipeMiniItemBinding
import com.bangkit.snapcook.utils.constant.AnimationConstants
import com.bangkit.snapcook.utils.extension.popTap
import com.bangkit.snapcook.utils.extension.setImageUrl

class ListRecipeAdapter(
    private val onClick: (String) -> Unit
): RecyclerView.Adapter<ListRecipeAdapter.RecipeViewHolder>() {

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
        val binding = RecipeMiniItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val user = listRecipe[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = listRecipe.size

    inner class RecipeViewHolder(private val binding: RecipeMiniItemBinding) :
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
            }
    }
}