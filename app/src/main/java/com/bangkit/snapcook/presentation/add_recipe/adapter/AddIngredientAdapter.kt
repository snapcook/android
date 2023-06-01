package com.bangkit.snapcook.presentation.add_recipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.databinding.AddIngredientItemBinding
import com.bangkit.snapcook.utils.extension.popClick
import timber.log.Timber
import java.util.Collections

class AddIngredientAdapter : RecyclerView.Adapter<AddIngredientAdapter.StringViewHolder>() {

    private var data: ArrayList<String> = arrayListOf("", "")
    private var isEdit = false

    fun setData(data: List<String>){
        this.data.clear()
        this.data.addAll(data)
        isEdit = true
        notifyDataSetChanged()
    }

    fun addData() {
        this.data.add("")
        notifyItemInserted(this.data.size + 1)
    }

    fun removeData(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }


    fun retrieveResult(): List<String> {
        return data
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
                if (isEdit){
                    edtIngredient.setText(data[layoutPosition])
                }

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