package com.bangkit.snapcook.presentation.modal.prediction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.databinding.PredictionItemBinding
import com.bangkit.snapcook.utils.extension.popClick
import timber.log.Timber

class PredictionAdapter : RecyclerView.Adapter<PredictionAdapter.PredictionViewHolder>() {

    private var data = ArrayList<String>()

    fun setData(results: List<String>) {
        data.clear()
        data.addAll(results)

        notifyDataSetChanged()
    }

    fun addData(data :String) {
        this.data.add(data)
        notifyItemInserted(this.data.size + 1)
        Timber.d("ADDDATA ${data}")

    }

    fun removeData(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun retrieveData(): List<String> = data

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionViewHolder {
        val binding = PredictionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PredictionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PredictionViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class PredictionViewHolder(private val binding: PredictionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String) {
            binding.apply {
                tvPrediction.text = name
                btnDelete.popClick {
                    removeData(layoutPosition)
                }
            }
        }
    }
}