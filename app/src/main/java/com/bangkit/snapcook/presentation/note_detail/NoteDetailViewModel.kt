package com.bangkit.snapcook.presentation.note_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.Grocery
import com.bangkit.snapcook.data.repository.GroceryRepository
import com.bangkit.snapcook.utils.constant.GroceryConstants
import kotlinx.coroutines.launch

class NoteDetailViewModel(private val repository: GroceryRepository) : ViewModel() {

    val isAllNoteCompleted: LiveData<Boolean> by lazy { _isAllNoteCompleted }
    private val _isAllNoteCompleted = MutableLiveData<Boolean>()

    fun getIngredientsGrocery(groupId: String): LiveData<List<Grocery>> = repository.getGroceries(
        GroceryConstants.INGREDIENTS_TYPE, groupId
    )

    fun getSpicesGrocery(groupId: String): LiveData<List<Grocery>> = repository.getGroceries(
        GroceryConstants.SPICES_TYPE, groupId
    )

    fun getUtensilsGrocery(groupId: String): LiveData<List<Grocery>> = repository.getGroceries(
        GroceryConstants.UTENSILS_TYPE, groupId
    )

    fun updateCompleted(grocery: Grocery, status: Boolean) {
        viewModelScope.launch {
            repository.completeGroceryGroup(grocery.id, grocery.groupId, status)
            val newStatus = repository.checkGroceryStatus(grocery.groupId)
            _isAllNoteCompleted.postValue(newStatus)
            repository.updateGroceryGroupCompleted(grocery.groupId, newStatus)
        }
    }

    fun deleteGrocery(groupId: String) {
        viewModelScope.launch {
            repository.deleteGroceryGroup(groupId)
        }
    }
}