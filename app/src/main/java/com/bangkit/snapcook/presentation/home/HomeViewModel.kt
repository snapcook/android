package com.bangkit.snapcook.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.RecipeRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: RecipeRepository
    ) : ViewModel() {

    val popularRecipeResult: LiveData<ApiResponse<List<Recipe>>> by lazy { _popularRecipeResult }
    private val _popularRecipeResult = MutableLiveData<ApiResponse<List<Recipe>>>()

    val recommendedRecipeResult: LiveData<ApiResponse<List<Recipe>>> by lazy { _recommendedRecipeResult }
    private val _recommendedRecipeResult = MutableLiveData<ApiResponse<List<Recipe>>>()

    fun getRecipes(
        authorId: String? = null,
        mainCategory: String? = null,
        secondCategoryId: String? = null,
        search: String? = null
    ) {
        if (mainCategory != null) {
            viewModelScope.launch {
                repository.getRecipes(
                    authorId,
                    mainCategory,
                    secondCategoryId,
                    search
                ).collect() {
                    _recommendedRecipeResult.postValue(it)
                }
            }
        } else {
            viewModelScope.launch {
                repository.getRecipes(
                    authorId,
                    mainCategory,
                    secondCategoryId,
                    search
                ).collect() {
                    _popularRecipeResult.postValue(it)
                }
            }
        }
    }
}