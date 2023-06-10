package com.bangkit.snapcook.presentation.add_to_grocery.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.databinding.ShoppingNoteItemBinding

class AddToGroceryAdapter: RecyclerView.Adapter<AddToGroceryAdapter.ShoppingNoteViewHolder>() {

    private val groceries = ArrayList<String>()
    private val selectedGroceries = ArrayList<String>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listGroceries: List<String>) {
        this.groceries.clear()
        this.groceries.addAll(listGroceries)
        notifyDataSetChanged()
    }

    fun getSelectedData(): List<String> = selectedGroceries

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingNoteViewHolder {
        val binding = ShoppingNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingNoteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AddToGroceryAdapter.ShoppingNoteViewHolder,
        position: Int,
    ) {
        val grocery = groceries[position]
        holder.bind(grocery)
    }

    override fun getItemCount(): Int = groceries.size

    inner class ShoppingNoteViewHolder(private val binding: ShoppingNoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(grocery: String){
                binding.apply {
                    cbNote.setOnClickListener {
                        if (cbNote.isChecked) {
                            selectedGroceries.add(grocery)
                        } else {
                            selectedGroceries.remove(grocery)
                        }
                    }
                    tvNote.text = grocery
                }
            }
    }
}