package com.bangkit.snapcook.presentation.modal.utensils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.R
import com.bangkit.snapcook.data.model.Utensil
import com.bangkit.snapcook.databinding.CookingWareItemBinding
import com.bangkit.snapcook.databinding.UtensilSelectionItemBinding

class UtensilsAdapter : RecyclerView.Adapter<UtensilsAdapter.UtensilViewHolder>() {

    private var data = ArrayList<Utensil>()
    private val selectedItems = ArrayList<Utensil>()

    fun setData(utensils: List<Utensil>) {
        this.data.clear()
        this.data.addAll(utensils)
        notifyDataSetChanged()
    }

    fun setSelectedData(utensils: List<Utensil>) {
        selectedItems.clear()
        selectedItems.addAll(utensils)
    }

    fun addData(utensil: Utensil) {
        this.data.add(utensil)
        notifyItemInserted(this.data.size + 1)
    }

    fun removeData(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun retrieveSelectedUtensils(): List<Utensil> = selectedItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UtensilViewHolder {
        val binding = UtensilSelectionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UtensilViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UtensilViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class UtensilViewHolder(private val binding: UtensilSelectionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(utensil: Utensil) {
            binding.apply {
                if (selectedItems.contains(utensil)) {
                    iconCheck.setImageResource(R.drawable.ic_checked_box)
                } else {
                    iconCheck.setImageResource(R.drawable.ic_unchecked_box)
                }

                imgCookingWare.setImageResource(utensil.image)
                tvCookingWare.text = utensil.name
                itemView.setOnClickListener {
                    if (selectedItems.contains(utensil)) {
                        selectedItems.remove(utensil)
                        iconCheck.setImageResource(R.drawable.ic_unchecked_box)
                    } else {
                        selectedItems.add(utensil)
                        iconCheck.setImageResource(R.drawable.ic_checked_box)
                    }
                }
            }
        }
    }
}