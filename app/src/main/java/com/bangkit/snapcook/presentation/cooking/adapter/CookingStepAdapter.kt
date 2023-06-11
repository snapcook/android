package com.bangkit.snapcook.presentation.cooking.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.R
import com.bangkit.snapcook.databinding.ItemStepCookingBinding
import com.bangkit.snapcook.utils.extension.gone
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.show

class CookingStepAdapter(private val onChangeCurrentPage: (Int) -> Unit): RecyclerView.Adapter<CookingStepAdapter.ShoppingNoteViewHolder>() {

    private val steps = ArrayList<String>()
    private var currentIndex = 0

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listStep: List<String>) {
        this.steps.clear()
        this.steps.addAll(listStep)
        notifyDataSetChanged()
    }

    fun onPageChanged(position: Int) {
        currentIndex = position
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoppingNoteViewHolder {
        val binding = ItemStepCookingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingNoteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CookingStepAdapter.ShoppingNoteViewHolder,
        position: Int,
    ) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = steps.size

    inner class ShoppingNoteViewHolder(private val binding: ItemStepCookingBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(position: Int){
                binding.apply {

                    if (currentIndex >= layoutPosition) {
                        itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.primary_color))
                    } else {
                        itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.grey_200))
                    }

                    itemView.popClick {
                        onChangeCurrentPage(layoutPosition)
                        if (currentIndex >= layoutPosition) {
                            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.primary_color))
                        } else {
                            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.grey_200))
                        }
                        currentIndex = layoutPosition
                        notifyDataSetChanged()
                    }

                    when (layoutPosition) {
                        0 -> {
                            tvStep.gone()
                            imgStep.show()
                            imgStep.setImageDrawable(AppCompatResources.getDrawable(itemView.context, R.drawable.ic_flag))
                        }
                        steps.size - 1 -> {
                            tvStep.gone()
                            imgStep.show()
                            imgStep.setImageDrawable(AppCompatResources.getDrawable(itemView.context, R.drawable.ic_dine))
                        }
                        else -> {
                            tvStep.show()
                            imgStep.gone()
                            tvStep.text = "$position"
                        }
                    }
                }
            }
    }
}