package com.bangkit.snapcook.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.GroceryGroup
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.BookmarkRepository
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
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    val recipeDetailResult: LiveData<ApiResponse<Recipe>> by lazy { _recipeDetailResult }
    private val _recipeDetailResult = MutableLiveData<ApiResponse<Recipe>>()

    val isBookmarked: LiveData<Boolean> by lazy { _isBookmarked }
    private val _isBookmarked = MutableLiveData<Boolean>()

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
    fun addBookmark(id: String) {
        viewModelScope.launch {
            bookmarkRepository.addBookmark(id).collect {}
        }
    }

    fun removeBookmark(id: String) {
        viewModelScope.launch {
            bookmarkRepository.removeBookmark(id).collect {}
        }
    }

    fun toggleBookmarkButton(isBookmarked: Boolean){
        _isBookmarked.value = isBookmarked
    }
}