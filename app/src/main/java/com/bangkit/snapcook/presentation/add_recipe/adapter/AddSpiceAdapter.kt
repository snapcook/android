package com.bangkit.snapcook.presentation.add_recipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.databinding.AddIngredientItemBinding
import com.bangkit.snapcook.utils.extension.popClick
import java.util.Collections

class AddSpiceAdapter : RecyclerView.Adapter<AddSpiceAdapter.StringViewHolder>() {

    private var data: ArrayList<String> = arrayListOf("", "")

    fun addData() {
        this.data.add("")
        notifyItemInserted(this.data.size + 1)
    }

    fun removeData(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }


    fun retrieveResult(): String {
        var finalResult = ""
        for (result in data){
            finalResult += "$result,"
        }
        return finalResult
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val binding = AddIngredientItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StringViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = data.size

    inner class StringViewHolder(private val binding: AddIngredientItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.apply {
                edtIngredient.doAfterTextChanged {
                    data[layoutPosition] = it.toString()
                }

                iconClose.popClick {
                    edtIngredient.text.clear()
                    removeData(layoutPosition)
                }
            }
        }
    }

    val itemTouchHelper = ItemTouchHelper(object :
        ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN or ItemTouchHelper.UP, 0) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val initial = viewHolder.absoluteAdapterPosition
            val final = target.absoluteAdapterPosition
            Collections.swap(data, initial, final)
            notifyItemMoved(initial, final)
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
    })
}