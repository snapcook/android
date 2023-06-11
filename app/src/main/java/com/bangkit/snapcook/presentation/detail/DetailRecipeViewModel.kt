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

    val bookmarkResult: LiveData<ApiResponse<String>> by lazy { _bookmarkResult }
    private val _bookmarkResult = MutableLiveData<ApiResponse<String>>()

    val isBookmarked: LiveData<Boolean> by lazy { _isBookmarked }
    private val _isBookmarked = MutableLiveData<Boolean>()

    val isGroceryGroupExist: LiveData<Boolean> by lazy { _isGroceryGroupExist }
    private val _isGroceryGroupExist = MutableLiveData<Boolean>()

    fun getRecipeDetail(slug: String) {
        viewModelScope.launch {
            recipeRepository.getRecipeDetail(slug).collect {
                _recipeDetailResult.postValue(it)
            }
        }
    }

    fun checkIsGroceryGroupExist(groupId: String) {
        viewModelScope.launch {
            _isGroceryGroupExist.postValue(groceryRepository.isGroceryGroupExist(groupId))
        }
    }
    fun addBookmark(id: String) {
        viewModelScope.launch {
            bookmarkRepository.addBookmark(id).collect {
                _bookmarkResult.postValue(it)
            }
        }
    }

    fun removeBookmark(id: String) {
        viewModelScope.launch {
            bookmarkRepository.removeBookmark(id).collect {
                _bookmarkResult.postValue(it)
            }
        }
    }

    fun toggleBookmarkButton(isBookmarked: Boolean){
        _isBookmarked.value = isBookmarked
    }
}