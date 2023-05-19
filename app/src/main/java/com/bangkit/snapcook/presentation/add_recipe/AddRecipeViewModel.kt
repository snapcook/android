package com.bangkit.snapcook.presentation.add_recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.RecipeRepository
import com.bangkit.snapcook.utils.helper.Event
import kotlinx.coroutines.launch
import java.io.File

class AddRecipeViewModel(private val repository: RecipeRepository) : ViewModel() {
    val uploadResult: LiveData<Event<ApiResponse<String>>> by lazy { _uploadResult }
    private val _uploadResult = MutableLiveData<Event<ApiResponse<String>>>()

    fun uploadRecipe(
        photo: File,
        title: String,
        description: String,
        mainCategory: String,
        totalServing: String,
        estimatedTime: String,
        mainIngredients: List<String>,
        spices: List<String>,
        steps: List<String>,
        utensils: List<String>,
    ) {
        viewModelScope.launch {
            repository.addRecipe(
                photo,
                title,
                description,
                mainCategory,
                totalServing,
                estimatedTime,
                mainIngredients,
                spices,
                steps,
                utensils
            ).collect {
                _uploadResult.postValue(Event(it))
            }
        }
    }

}