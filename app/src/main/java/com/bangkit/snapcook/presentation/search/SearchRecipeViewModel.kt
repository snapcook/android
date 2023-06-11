package com.bangkit.snapcook.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.BookmarkRepository
import com.bangkit.snapcook.data.repository.RecipeRepository
import kotlinx.coroutines.launch

class SearchRecipeViewModel(
    private val recipeRepository: RecipeRepository,
    private val bookmarkRepository: BookmarkRepository,
) : ViewModel() {

    val searchResult: LiveData<ApiResponse<List<Recipe>>> by lazy { _searchResult }
    private val _searchResult = MutableLiveData<ApiResponse<List<Recipe>>>()

    fun searchRecipe(
        search: String? = null,
    ) {
        viewModelScope.launch {
            recipeRepository.getSearchRecipes(
                search
            ).collect {
                _searchResult.postValue(it)
            }
        }
    }

    fun addBookmark(id: String) {
        viewModelScope.launch {
            bookmarkRepository.addBookmark(id).collect {
            }
        }
    }

    fun removeBookmark(id: String) {
        viewModelScope.launch {
            bookmarkRepository.removeBookmark(id).collect {
            }
        }
    }

}