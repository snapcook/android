package com.bangkit.snapcook.presentation.note_detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.base.custom_view.GroceryTextView.Companion.DONE
import com.bangkit.snapcook.base.custom_view.GroceryTextView.Companion.NORMAL
import com.bangkit.snapcook.data.model.Grocery
import com.bangkit.snapcook.databinding.ShoppingNoteItemBinding

class ShoppingNoteAdapter(
    private val onCheckedChange: (Grocery, Boolean) -> Unit,
): RecyclerView.Adapter<ShoppingNoteAdapter.ShoppingNoteViewHolder>() {

    private val groceries = ArrayList<Grocery>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listGroceries: List<Grocery>) {
        this.groceries.clear()
        this.groceries.addAll(listGroceries)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingNoteViewHolder {
        val binding = ShoppingNoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingNoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingNoteViewHolder, position: Int) {
        val grocery = groceries[position]
        holder.bind(grocery)
    }

    override fun getItemCount(): Int = groceries.size

    inner class ShoppingNoteViewHolder(private val binding: ShoppingNoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(grocery: Grocery){
                binding.apply {
                    if(grocery.isCompleted){
                        tvNote.state = DONE
                        cbNote.isChecked = true
                    } else {
                        tvNote.state = NORMAL
                        cbNote.isChecked = false
                    }
                    
                    cbNote.setOnClickListener {
                        onCheckedChange(grocery, !grocery.isCompleted)
                    }

                    tvNote.text = grocery.title
                }
            }
    }
}