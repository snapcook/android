package com.bangkit.snapcook.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.Category
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.CategoryRepository
import com.bangkit.snapcook.data.repository.RecipeRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val recipeRepository: RecipeRepository,
    private val categoryRepository: CategoryRepository
    ) : ViewModel() {

    val popularRecipeResult: LiveData<ApiResponse<List<Recipe>>> by lazy { _popularRecipeResult }
    private val _popularRecipeResult = MutableLiveData<ApiResponse<List<Recipe>>>()

    val recommendedRecipeResult: LiveData<ApiResponse<List<Recipe>>> by lazy { _recommendedRecipeResult }
    private val _recommendedRecipeResult = MutableLiveData<ApiResponse<List<Recipe>>>()

    val categoryResult: LiveData<ApiResponse<List<Category>>> by lazy { _categoryResult }
    private val _categoryResult = MutableLiveData<ApiResponse<List<Category>>>()

    fun getRecipes(
        authorId: String? = null,
        mainCategory: String? = null,
        secondCategoryId: String? = null,
        search: String? = null
    ) {
        if(mainCategory != null) {
            viewModelScope.launch {
                recipeRepository.getRecipes( mainCategory = mainCategory ).collect() {
                    _recommendedRecipeResult.postValue(it)
                }
            }
        } else {
            viewModelScope.launch {
                recipeRepository.getRecipes( mainCategory = mainCategory ).collect() {
                    _popularRecipeResult.postValue(it)
                }
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            categoryRepository.getCategories().collect() {
                _categoryResult.postValue(it)
            }
        }
    }
}