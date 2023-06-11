package com.bangkit.snapcook.presentation.recommended

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.BookmarkRepository
import com.bangkit.snapcook.data.repository.RecipeRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class RecommendedViewModel(
    private val recipeRepository: RecipeRepository,
    private val bookmarkRepository: BookmarkRepository) : ViewModel() {

    val recipeResult: LiveData<ApiResponse<List<Recipe>>> by lazy { _recipeResult }
    private val _recipeResult = MutableLiveData<ApiResponse<List<Recipe>>>()

    fun getRecipe(ingredients: List<String>) {
        viewModelScope.launch {
            recipeRepository.predictIngredients(ingredients).collect {
                _recipeResult.postValue(it)
            }
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
}