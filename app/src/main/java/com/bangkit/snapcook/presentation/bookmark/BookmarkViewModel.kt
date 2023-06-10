package com.bangkit.snapcook.presentation.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.network.response.BookmarkResponse
import com.bangkit.snapcook.data.network.services.RecipeService
import com.bangkit.snapcook.data.repository.BookmarkRepository
import com.bangkit.snapcook.utils.PreferenceManager
import com.bangkit.snapcook.utils.helper.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber


class BookmarkViewModel(
    private val repository: BookmarkRepository
) : ViewModel() {

    val getBookmarkResult: LiveData<ApiResponse<List<Recipe>>> by lazy { _getBookmarkResult }
    private val _getBookmarkResult = MutableLiveData<ApiResponse<List<Recipe>>>()

    fun getBookmarkedRecipe(){
        viewModelScope.launch {
            repository.getBookmarkedRecipe().collect() {
                _getBookmarkResult.postValue(it)
            }
        }
    }

    fun removeBookmark(id: String){
        viewModelScope.launch {
            Timber.d("Remove Bookmark, id: $id")
            repository.removeBookmark(id).collect{

            }
        }
    }
}