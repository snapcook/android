package com.bangkit.snapcook.presentation.note

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.GroceryGroup
import com.bangkit.snapcook.data.model.Recipe
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.GroceryRepository
import com.bangkit.snapcook.data.repository.RecipeRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

class NoteViewModel(private val repository: GroceryRepository) : ViewModel() {

    val groceryResult: LiveData<ApiResponse<List<GroceryGroup>>> by lazy { _groceryResult }
    private val _groceryResult = MutableLiveData<ApiResponse<List<GroceryGroup>>>()

    fun getGrocery() {
        viewModelScope.launch {
            repository.getGroceriesGroup().collect{
                _groceryResult.postValue(it)
            }
        }
    }
}