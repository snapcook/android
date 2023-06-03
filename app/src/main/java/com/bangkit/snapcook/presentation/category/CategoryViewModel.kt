package com.bangkit.snapcook.presentation.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.BookmarkRepository
import com.bangkit.snapcook.data.repository.RecipeRepository
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val recipeRepository: RecipeRepository,
    private val bookmarkRepository: BookmarkRepository,
) : ViewModel() {

    val categoryResult: LiveData<ApiResponse<List<Recipe>>> by lazy { _categoryResult }
    private val _categoryResult = MutableLiveData<ApiResponse<List<Recipe>>>()

    fun searchCategory(
        categoryId: String
    ) {
        viewModelScope.launch {
            recipeRepository.getCategoryRecipes(
                categoryId
            ).collect {
                _categoryResult.postValue(it)
            }
        }
    }

    fun addBookmark(id: String) {
        viewModelScope.launch {
            bookmarkRepository.addBookmark(id).collect {
                // jangan lupa dicollect buat manggil flow nya
            }
        }
    }

    fun removeBookmark(id: String) {
        viewModelScope.launch {
            bookmarkRepository.removeBookmark(id).collect {
                // jangan lupa dicollect buat manggil flow nya
            }
        }
    }

}