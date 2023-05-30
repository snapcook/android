package com.bangkit.snapcook.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.GroceryGroup
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.GroceryRepository
import com.bangkit.snapcook.data.repository.RecipeRepository
import com.bangkit.snapcook.utils.constant.GroceryConstants.INGREDIENTS_TYPE
import com.bangkit.snapcook.utils.constant.GroceryConstants.SPICES_TYPE
import com.bangkit.snapcook.utils.constant.GroceryConstants.UTENSILS_TYPE
import com.bangkit.snapcook.utils.helper.convertToGrocery
import com.bangkit.snapcook.utils.helper.convertUtensilToGrocery
import kotlinx.coroutines.launch

class DetailRecipeViewModel(
    private val recipeRepository: RecipeRepository,
    private val groceryRepository: GroceryRepository,
) : ViewModel() {

    val recipeDetailResult: LiveData<ApiResponse<Recipe>> by lazy { _recipeDetailResult }
    private val _recipeDetailResult = MutableLiveData<ApiResponse<Recipe>>()

    fun getRecipeDetail(slug: String) {
        viewModelScope.launch {
            recipeRepository.getRecipeDetail(slug).collect {
                _recipeDetailResult.postValue(it)
            }
        }
    }

    fun addToGroceryList(recipe: Recipe) {
        viewModelScope.launch {
            val groceryGroup = GroceryGroup(
                slug = recipe.slug,
                groupId = recipe.id,
                utensils = recipe.utensils.size,
                title = recipe.title,
                photo = recipe.photo,
                spices = recipe.spices.size,
                ingredients = recipe.mainIngredients.size,
            )
            groceryRepository.insertGroceryGroup(groceryGroup)
            groceryRepository.insertGroceries(
                recipe.utensils.convertUtensilToGrocery(
                    recipe.id,
                    UTENSILS_TYPE
                )
            )
            groceryRepository.insertGroceries(
                recipe.spices.convertToGrocery(
                    recipe.id,
                    SPICES_TYPE
                )
            )
            groceryRepository.insertGroceries(
                recipe.fullIngredients.convertToGrocery(
                    recipe.id,
                    INGREDIENTS_TYPE
                )
            )
        }
    }
}