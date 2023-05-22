package com.bangkit.snapcook.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.model.User
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.AuthRepository
import com.bangkit.snapcook.data.repository.RecipeRepository
import com.bangkit.snapcook.data.repository.UserRepository
import kotlinx.coroutines.launch

class DetailRecipeViewModel(
    private val recipeRepository: RecipeRepository,
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

}