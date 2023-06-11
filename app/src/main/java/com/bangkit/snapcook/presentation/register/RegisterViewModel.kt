package com.bangkit.snapcook.presentation.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.AuthRepository
import com.bangkit.snapcook.utils.helper.Event
import kotlinx.coroutines.launch

class RegisterViewModel(private val repository: AuthRepository) : ViewModel() {
    val registerResult: LiveData<Event<ApiResponse<String>>> by lazy { _registerResult }
    private val _registerResult = MutableLiveData<Event<ApiResponse<String>>>()

    fun registerUser(
        name: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            repository.register(name, email, password)
                .collect {
                    _registerResult.postValue(Event(it))
                }
        }
    }
}