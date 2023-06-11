package com.bangkit.snapcook.presentation.add_to_grocery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.GroceryGroup
import com.bangkit.snapcook.data.repository.GroceryRepository
import com.bangkit.snapcook.utils.constant.GroceryConstants
import com.bangkit.snapcook.utils.helper.convertToGrocery
import kotlinx.coroutines.launch

class AddToGroceryViewModel(private val repository: GroceryRepository) : ViewModel() {

    fun addToGroceryList(
        slug: String,
        groupId: String,
        title: String,
        photo: String,
        ingredients: List<String>,
        spices: List<String>) {
        viewModelScope.launch {
            val groceryGroup = GroceryGroup(
                slug = slug,
                groupId = groupId,
                utensils = 0,
                title = title,
                photo = photo,
                spices = spices.size,
                ingredients = ingredients.size,
            )
            repository.insertGroceryGroup(groceryGroup)
            repository.insertGroceries(
                spices.convertToGrocery(
                    groupId,
                    GroceryConstants.SPICES_TYPE
                )
            )
            repository.insertGroceries(
                ingredients.convertToGrocery(
                    groupId,
                    GroceryConstants.INGREDIENTS_TYPE
                )
            )
        }
    }
}