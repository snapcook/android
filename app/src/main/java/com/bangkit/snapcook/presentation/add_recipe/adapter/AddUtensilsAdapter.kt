package com.bangkit.snapcook.presentation.add_recipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.R
import com.bangkit.snapcook.data.model.Utensil
import com.bangkit.snapcook.databinding.CookingWareItemBinding
import com.bangkit.snapcook.utils.extension.popClick

class AddUtensilsAdapter : RecyclerView.Adapter<AddUtensilsAdapter.StringViewHolder>() {
    private var data = ArrayList<Utensil>()

    fun setData(utensils: List<Utensil>) {
        this.data.clear()
        this.data.addAll(utensils)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val binding = CookingWareItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StringViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class StringViewHolder(private val binding: CookingWareItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(utensil: Utensil) {
            binding.apply {
                imgCookingWare.setImageResource(utensil.image)
                tvCookingWare.text = utensil.name
            }
        }
    }
}