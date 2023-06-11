package com.bangkit.snapcook.presentation.detail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.R
import com.bangkit.snapcook.databinding.DetailStepItemBinding
import com.bangkit.snapcook.databinding.StepItemBinding
import timber.log.Timber
import kotlin.collections.ArrayList

class ListStepsAdapter() : RecyclerView.Adapter<ListStepsAdapter.StringViewHolder>() {

    private var data: ArrayList<String> = arrayListOf("")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StringViewHolder {
        val binding = DetailStepItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StringViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StringViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(steps: List<String>) {
        data.clear()
        data.addAll(steps)
        notifyDataSetChanged()
    }

    inner class StringViewHolder(private val binding: DetailStepItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var currentPosition = 0

        fun bind(position: Int) {
            currentPosition = position

            binding.apply {
                tvStep.text = root.context.getString(R.string.label_step_item_detail, "${currentPosition + 1}")
                tvInstruction.text = data[position]
            }
        }
    }
}