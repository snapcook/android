package com.bangkit.snapcook.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.model.User
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {
    val profileResult: LiveData<ApiResponse<User>> by lazy { _profileResult }
    private val _profileResult = MutableLiveData<ApiResponse<User>>()

    fun getProfile() {
        viewModelScope.launch {
            repository.getProfile()
                .collect {
                    _profileResult.postValue(it)
                }
        }
    }

}