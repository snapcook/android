package com.bangkit.snapcook.data.repository

import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.network.request.LoginRequest
import com.bangkit.snapcook.data.network.request.RegisterRequest
import com.bangkit.snapcook.data.source.AuthDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class AuthRepository(
    private val dataSource: AuthDataSource,
) {

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): Flow<ApiResponse<String>> {
        val request = RegisterRequest(
            name = name,
            email = email,
            password = password
        )
        return dataSource.register(request).flowOn(Dispatchers.IO)
    }

    suspend fun login(
        email: String,
        password: String
    ): Flow<ApiResponse<String>> {
        val request = LoginRequest(
            email = email,
            password = password
        )
        return dataSource.login(request).flowOn(Dispatchers.IO)
    }
}