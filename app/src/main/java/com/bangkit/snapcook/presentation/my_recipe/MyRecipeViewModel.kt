package com.bangkit.snapcook.presentation.my_recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.RecipeRepository
import kotlinx.coroutines.launch

class MyRecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    val recipeResult: LiveData<ApiResponse<List<Recipe>>> by lazy { _recipeResult }
    private val _recipeResult = MutableLiveData<ApiResponse<List<Recipe>>>()

    fun getMyRecipe() {
        viewModelScope.launch {
            repository.getMyRecipe().collect {
                _recipeResult.postValue(it)
            }
        }
    }


}