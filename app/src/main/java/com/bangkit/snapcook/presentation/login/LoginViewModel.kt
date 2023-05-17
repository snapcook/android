package com.bangkit.snapcook.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.repository.AuthRepository
import com.bangkit.snapcook.utils.helper.Event
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AuthRepository) : ViewModel() {
    val loginResult: LiveData<Event<ApiResponse<String>>> by lazy { _loginResult }
    private val _loginResult = MutableLiveData<Event<ApiResponse<String>>>()

    fun loginUser(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            repository.login(email, password)
                .collect {
                    _loginResult.postValue(Event(it))
                }
        }
    }
}