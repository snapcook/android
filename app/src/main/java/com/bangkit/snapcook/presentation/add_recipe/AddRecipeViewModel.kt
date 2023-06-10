package com.bangkit.snapcook.presentation.add_recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.Category
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.model.Utensil
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.CategoryRepository
import com.bangkit.snapcook.data.repository.RecipeRepository
import com.bangkit.snapcook.utils.helper.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File

class AddRecipeViewModel(
    private val repository: RecipeRepository,
    private val categoryRepository: CategoryRepository,

) : ViewModel() {
    val uploadResult: LiveData<Event<ApiResponse<String>>> by lazy { _uploadResult }
    private val _uploadResult = MutableLiveData<Event<ApiResponse<String>>>()

    val categoryResult: LiveData<ApiResponse<List<Category>>> by lazy { _categoryResult }
    private val _categoryResult = MutableLiveData<ApiResponse<List<Category>>>()

    val utensilResult: LiveData<ApiResponse<List<Utensil>>> by lazy { _utensilResult }
    private val _utensilResult = MutableLiveData<ApiResponse<List<Utensil>>>()


    val recipeDetailResult: LiveData<ApiResponse<Recipe>> by lazy { _recipeDetailResult }
    private val _recipeDetailResult = MutableLiveData<ApiResponse<Recipe>>()

    fun deleteRecipe(id: String){
        viewModelScope.launch {
            repository.deleteRecipe(id).collect{

            }
        }
    }

    fun getRecipeDetail(slug: String) {
        viewModelScope.launch {
            repository.getRecipeDetail(slug).collect {
                _recipeDetailResult.postValue(it)
            }
        }
    }

    fun getUtensils() {
        viewModelScope.launch {
            repository.getUtensils().collect {
                _utensilResult.postValue(it)
            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            categoryRepository.getCategories().collect {
                _categoryResult.postValue(it)
            }
        }
    }

    fun uploadRecipe(
        recipeId: String? = null,
        photo: File?,
        title: String,
        description: String,
        secondCategoryId: String,
        mainCategory: String,
        totalServing: String,
        estimatedTime: String,
        mainIngredients: List<String>,
        spices: List<String>,
        steps: List<String>,
        utensils: List<Utensil>,
        isEdit: Boolean
    ) {
        val utensilName = ArrayList<String>()
        utensils.map {
            utensilName.add(it.id)
        }
        viewModelScope.launch {

            if(isEdit){
                repository.editRecipe(
                    recipeId ?: "",
                    photo,
                    title,
                    description,
                    mainCategory,
                    secondCategoryId,
                    totalServing,
                    estimatedTime,
                    mainIngredients,
                    spices,
                    steps,
                    utensilName
                ).collect {
                    _uploadResult.postValue(Event(it))
                }
            } else {
                repository.addRecipe(
                    photo!!,
                    title,
                    description,
                    mainCategory,
                    secondCategoryId,
                    totalServing,
                    estimatedTime,
                    mainIngredients,
                    spices,
                    steps,
                    utensilName
                ).collect {
                    _uploadResult.postValue(Event(it))
                }
            }
        }
    }

}