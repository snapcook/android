package com.bangkit.snapcook.presentation.add_recipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.R
import com.bangkit.snapcook.databinding.AddIngredientItemBinding
import com.bangkit.snapcook.databinding.AddStepItemBinding
import com.bangkit.snapcook.utils.extension.popClick
import java.lang.Math.max
import java.lang.Math.min
import java.util.Collections

class AddStepAdapter : RecyclerView.Adapter<AddStepAdapter.StringViewHolder>() {

    private var data: ArrayList<String> = arrayListOf("")

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
        val binding = AddStepItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StringViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {

        holder.bind(position)
    }

    override fun getItemCount(): Int = data.size

    inner class StringViewHolder(private val binding: AddStepItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var currentPosition = 0

        fun bind(position: Int) {
            currentPosition = position

            binding.apply {
                edtStep.doAfterTextChanged {
                    data[layoutPosition] = it.toString()
                }
                tvStep.text = root.context.getString(R.string.label_step_item, "${currentPosition + 1}")
                iconClose.popClick {
                    edtStep.text.clear()
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

            for (i in 0 until data.size){
                val holder = recyclerView.findViewHolderForAdapterPosition(i) as StringViewHolder
                holder.bind(i)
            }

            return false
        }
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
    })
}