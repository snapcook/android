package com.bangkit.snapcook.presentation.detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.data.model.Utensil
import com.bangkit.snapcook.databinding.CookingWareItemBinding
import com.bangkit.snapcook.databinding.ListItemBinding
import com.bangkit.snapcook.utils.extension.setImageUrl

class ListUtensilsAdapter : RecyclerView.Adapter<ListUtensilsAdapter.UtensilViewHolder>() {

    private var data = ArrayList<Utensil>()

    fun setData(utensils: List<Utensil>) {
        this.data.clear()
        this.data.addAll(utensils)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UtensilViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UtensilViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UtensilViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class UtensilViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(utensil: Utensil) {
            binding.apply {
                imgItem.setImageUrl(utensil.photo)
                tvItem.text = utensil.name
                itemView.setOnClickListener {
                }
            }
        }
    }
}