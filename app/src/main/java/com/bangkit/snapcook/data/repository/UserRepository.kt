package com.bangkit.snapcook.data.repository

import com.bangkit.snapcook.data.model.User
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.source.UserDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(
    private val dataSource: UserDataSource
) {

    suspend fun getProfile(): Flow<ApiResponse<User>> =
        dataSource.fetchProfile().flowOn(Dispatchers.IO)
}