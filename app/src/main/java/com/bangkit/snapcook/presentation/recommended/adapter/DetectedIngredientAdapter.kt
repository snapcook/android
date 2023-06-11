package com.bangkit.snapcook.presentation.recommended.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.databinding.ChipItemBinding

class DetectedIngredientAdapter :
    RecyclerView.Adapter<DetectedIngredientAdapter.DetectedIngredientViewHolder>() {

    private var listString = ArrayList<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listString: List<String>) {
        this.listString.clear()
        this.listString.addAll(listString)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DetectedIngredientViewHolder {
        val binding = ChipItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetectedIngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetectedIngredientViewHolder, position: Int) {
        val user = listString[position]
        holder.bind(user)
    }

    override fun getItemCount(): Int = listString.size

    inner class DetectedIngredientViewHolder(private val binding: ChipItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: String) {
            binding.tvIngredient.text = recipe

        }
    }
}