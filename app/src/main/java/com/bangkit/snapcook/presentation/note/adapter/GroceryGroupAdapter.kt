package com.bangkit.snapcook.presentation.note.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.snapcook.data.model.GroceryGroup
import com.bangkit.snapcook.databinding.GroceryGroupItemBinding
import com.bangkit.snapcook.utils.extension.gone
import com.bangkit.snapcook.utils.extension.popClick
import com.bangkit.snapcook.utils.extension.setImageUrl
import com.bangkit.snapcook.utils.extension.show

class GroceryGroupAdapter(
    private val onClickDetail: (String, String) -> Unit,
    private val onClickStartCooking: (String) -> Unit
): RecyclerView.Adapter<GroceryGroupAdapter.GroceryGroupViewHolder>() {

    private var listGroceryGroup = ArrayList<GroceryGroup>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listGroceryGroup: List<GroceryGroup>) {
        this.listGroceryGroup.clear()
        this.listGroceryGroup.addAll(listGroceryGroup)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GroceryGroupViewHolder {
        val binding = GroceryGroupItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GroceryGroupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GroceryGroupViewHolder, position: Int) {
        val user = listGroceryGroup[position]
        holder.bind(user)
        if (position == listGroceryGroup.lastIndex){
            val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
            params.bottomMargin = 100
            holder.itemView.layoutParams = params
        }else{
            val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
            params.bottomMargin = 0
            holder.itemView.layoutParams = params
        }
    }

    override fun getItemCount(): Int = listGroceryGroup.size

    inner class GroceryGroupViewHolder(private val binding: GroceryGroupItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun bind(grocery: GroceryGroup){
                binding.apply {
                    if (grocery.isCompleted){
                        tvComplete.show()
                        btnStartCooking.show()
                    }else{
                        tvComplete.gone()
                        btnStartCooking.gone()
                    }
                    btnStartCooking.popClick {
                        onClickStartCooking(grocery.slug)
                    }
                    imgRecipe.setOnClickListener {
                        onClickStartCooking(grocery.slug)
                    }
                    btnDetail.popClick {
                        onClickDetail(grocery.groupId, grocery.slug)
                    }
                    tvTitle.text = grocery.title
                    imgRecipe.setImageUrl(grocery.photo)
                    tvTotalIngredient.text = "Bahan Utama: ${grocery.ingredients} Items"
                    tvTotalSpices.text = "Bumbu: ${grocery.spices} Items"
                    tvTotalUtensils.text = "Alat: ${grocery.utensils} Items"
                }
            }
    }
}