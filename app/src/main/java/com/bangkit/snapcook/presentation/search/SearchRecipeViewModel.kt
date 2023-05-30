package com.bangkit.snapcook.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.network.response.BookmarkResponse
import com.bangkit.snapcook.data.repository.BookmarkRepository
import com.bangkit.snapcook.data.repository.RecipeRepository
import com.bangkit.snapcook.utils.helper.Event
import kotlinx.coroutines.launch

class SearchRecipeViewModel(
    private val recipeRepository: RecipeRepository,
    private val bookmarkRepository: BookmarkRepository
    ) : ViewModel() {

    val searchResult: LiveData<Event<ApiResponse<List<Recipe>>>> by lazy { _searchResult }
    private val _searchResult = MutableLiveData< Event<ApiResponse<List<Recipe>>>>()

    val getBookmarkResult: LiveData<ApiResponse<List<BookmarkResponse>>> by lazy { _getBookmarkResult }
    private val _getBookmarkResult = MutableLiveData<ApiResponse<List<BookmarkResponse>>>()

    fun searchRecipe(
        authorId: String? = null,
        mainCategory: String? = null,
        secondCategoryId: String? = null,
        search: String? = null) {
        viewModelScope.launch {
            recipeRepository.getRecipes(
                authorId,
                mainCategory,
                secondCategoryId,
                search
            ).collect {
                _searchResult.postValue(Event(it))
            }
        }
    }

    fun getBookmarkedRecipe(){
        viewModelScope.launch {
            bookmarkRepository.getBookmarkedRecipe().collect() {
                _getBookmarkResult.postValue(it)
            }
        }
    }

    fun addBookmark(id: String){
        viewModelScope.launch {
            bookmarkRepository.addBookmark(id)
        }
    }

    fun removeBookmark(id: String){
        viewModelScope.launch {
            bookmarkRepository.removeBookmark(id)
        }
    }

}