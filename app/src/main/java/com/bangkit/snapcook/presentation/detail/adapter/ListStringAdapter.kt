package com.bangkit.snapcook.presentation.detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.databinding.IngredientItemBinding
import timber.log.Timber

class ListStringAdapter() :
    RecyclerView.Adapter<ListStringAdapter.StringViewHolder>() {

    private var data: ArrayList<String> = arrayListOf("")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val binding = IngredientItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return StringViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(items: List<String>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    inner class StringViewHolder(private val binding: IngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.apply {
                tvIngredient.text = data
            }
        }
    }
}